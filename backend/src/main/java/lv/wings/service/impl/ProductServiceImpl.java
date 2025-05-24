package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import lv.wings.dto.request.admin.products.CreateProductTranslationDto;
import lv.wings.dto.request.admin.products.NewProductDto;
import lv.wings.dto.request.admin.products.NewProductMaterialDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.admin.products.ExistingProductDto;
import lv.wings.dto.response.admin.products.ProductAdminDto;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.dto.response.product.ProductTitleDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.exception.validation.NestedValidationException;
import lv.wings.mapper.ProductMapper;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.entity.ProductMaterial;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.repo.ProductTranslationRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ColorService;
import lv.wings.service.LocaleService;
import lv.wings.service.MaterialService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductMaterialService;
import lv.wings.service.ProductService;
import lv.wings.service.S3Service;
import lv.wings.service.TranslationService;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
@Slf4j
public class ProductServiceImpl extends AbstractTranslatableCRUDService<Product, ProductTranslation, Integer> implements ProductService {

	private final ProductRepository productRepository;
	private final ProductTranslationRepository productTranslationRepository;
	private final ProductMapper productMapper;
	private final ProductImageService productImageService;
	private final ProductCategoryService productCategoryService;
	private final ColorService colorService;
	private final ProductMaterialService productMaterialService;
	private final MaterialService materialService;
	private final Validator validator;
	private final TranslationService translationService;

	@PersistenceContext
	private EntityManager entityManager;
	// From the previous request
	private List<Product> randomProducts;
	private LocaleCode lastRequestedLocaleCode;

	@Lazy
	public ProductServiceImpl(
			ProductRepository productRepository,
			ProductTranslationRepository productTranslationRepository,
			ProductMapper productMapper,
			ProductImageService productImageService,
			LocaleService localeService,
			ProductCategoryService productCategoryService,
			ColorService colorService,
			ProductMaterialService productMaterialService,
			Validator validator,
			TranslationService translationService,
			S3Service imageUploader,
			MaterialService materialService) {
		super(productRepository, "Product", "entity.product", localeService);
		this.productTranslationRepository = productTranslationRepository;
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.productImageService = productImageService;
		this.productCategoryService = productCategoryService;
		this.colorService = colorService;
		this.productMaterialService = productMaterialService;
		this.validator = validator;
		this.translationService = translationService;
		this.materialService = materialService;
	}

	@Override
	public Page<ShortProductDto> getAllByCategory(Integer categoryId, Pageable pageable) {
		if (categoryId < 0)
			throw new InvalidParameterException("category.id", categoryId + "", false);
		Page<Product> products = categoryId == 0
				? productRepository.findAllByDeletedFalse(pageable)
				: productRepository.findAllByCategoryIdAndDeletedFalse(categoryId, pageable);
		return products.map(this::mapToShortProductDto);
	}

	@Override
	public Page<ProductAdminDto> getAllByCategoryForAdmin(String q, Integer categoryId, Pageable pageable) {
		if (categoryId < 0)
			throw new InvalidParameterException("category.id", categoryId + "", false);

		Optional<Sort.Order> soldOrder = pageable.getSort().stream()
				.filter(order -> order.getProperty().equalsIgnoreCase("sold"))
				.findFirst();

		Page<Product> results;

		if (soldOrder.isPresent()) {
			Pageable newPageable = PageRequest.of(pageable.getPageNumber(), pageable.getPageSize());
			results = productRepository.findWithSoldSortedNative(q, categoryId, soldOrder.get().getDirection().name().toLowerCase(), newPageable);
		} else {
			results = productRepository.searchByCategoryAndTitle(q, categoryId, pageable);
		}

		return results.map(productMapper::toBaseAdmin);
	}

	@Override
	public ProductDto getProductById(Integer id) {
		return mapToProductDto(productRepository.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(entityNameKey, entityName, id)));
	}

	@Override
	public List<RandomProductDto> getRandomProducts(Integer categoryId, Integer amount) {
		if (categoryId < 0)
			throw new InvalidParameterException("category.id", categoryId + "", false);
		if (amount < 1)
			throw new InvalidParameterException("general.amount", amount + "", false);

		boolean isAllCategories = categoryId == 0;

		/**
		 * Cache randomised list.
		 * 
		 * If a new locale => user wants to see the translation of the previously randomly assembled list.
		 * Else, randomly select a new list.
		 */

		if (randomProducts == null || localeService.getCurrentLocaleCode() == lastRequestedLocaleCode) {
			randomProducts = isAllCategories
					? productRepository.findAvaialableRandomProductsFromAll(amount)
					: productRepository.findAvaialableRandomProductsByCategory(categoryId, amount);

			/**
			 * Ideally, we do not want to show products that are currently unavailable (aka amount = 0), however if there are no enough avaialable products, use
			 * unavailable * as it might still interest the customer and he / she will add the product page to Bookmarks and check it later.
			 * 
			 * Or if we are doing the search by category, get available products from other categories to advertise.
			 */

			int missing = amount - randomProducts.size();
			if (missing > 0) {
				if (!isAllCategories) {
					randomProducts.addAll(productRepository.findAvaialableRandomProductsFromAll(missing));
					missing = amount - randomProducts.size();
				}
				if (missing > 0) {
					if (!isAllCategories) {
						randomProducts.addAll(productRepository.findUnavaialableRandomProductsByCategory(categoryId, missing));
						missing = amount - randomProducts.size();
					}
					if (missing > 0) {
						randomProducts.addAll(productRepository.findUnavaialableRandomProductsFromAll(missing));
					}
				}
			}
		}
		lastRequestedLocaleCode = localeService.getCurrentLocaleCode();
		return randomProducts.stream().map(this::mapToRandomProductDto).toList();
	}

	@Override
	public List<SearchedProductDto> getSearchedProducts(String q) {
		if (q.isBlank())
			return new ArrayList<>();

		return productTranslationRepository.findTop1000ByTitleContainingIgnoreCaseAndLocaleEquals(q, localeService.getCurrentLocaleCode())
				.stream()
				.filter((pr) -> !pr.getEntity().isDeleted())
				.map(this::mapToSearchedProductDto)
				.toList();
	}

	@Override
	public List<ProductTitleDto> getProductDtosByIds(List<Integer> ids) {
		return getProductsByIds(ids).stream().map(this::mapToProductTitleDto).toList();
	}

	@Override
	public List<Product> getProductsByIds(@NonNull List<Integer> ids) {
		if (ids.size() == 0)
			return List.of();
		return productRepository.findAllByIdInAndDeletedFalse(ids);
	}

	// MUST be used in methods that are using a transaction or the lock is not going to work
	@Override
	public List<Product> getProductsByIdsWithLock(@NonNull List<Integer> ids) {
		if (ids.size() == 0)
			return List.of();
		return productRepository.getProductsByIdsWithLock(ids);
	}

	@Override
	public Optional<Product> getProductByIdWithLock(@NonNull Integer id) {
		if (id < 1)
			return Optional.empty();
		return productRepository.getProductByIdWithLock(id);
	}

	@Override
	public ProductTranslation getRightTranslation(Product product) {
		return getRightTranslation(product, ProductTranslation.class);
	}

	@Override
	public ProductTranslation getSelectedTranslation(Product product, LocaleCode localeCode) {
		return getRightTranslationForSelectedLocale(product, ProductTranslation.class, localeCode);
	}

	@Override
	@Transactional
	public BasicMessageDto createProduct(NewProductDto dto) {
		validateNewProductDto(dto, null);
		List<CreateProductTranslationDto> providedTranslations = dto.getTranslations();
		CreateProductTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
		localeService.validateOneTranslationPerEachLocale(providedTranslations);
		boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);

		Product newProduct = Product.builder().amount(dto.getAmount()).price(dto.getPrice())
				.category(productCategoryService.findById(dto.getCategoryId())) // has been pre-validated
				.build();

		attachMaterials(newProduct, dto);
		attachTranslations(newProduct, dto, isManualTranslation, defaultTranslation);
		List<ProductImage> images = attachImages(newProduct, dto);
		attachColors(newProduct, dto);

		try {
			persist(newProduct);
		} catch (Exception e) {
			if (images != null) {
				productImageService.clearImagesUp(images, null);
			}
		}

		return new BasicMessageDto("Jauns produkts tika pievienots.");
	}

	@Override
	@Transactional
	public BasicMessageDto updateProduct(NewProductDto dto, Integer id) {
		Product existingProduct = findById(id);
		validateNewProductDto(dto, existingProduct);

		List<CreateProductTranslationDto> providedTranslations = dto.getTranslations();
		CreateProductTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
		localeService.validateOneTranslationPerEachLocale(providedTranslations);
		boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);

		existingProduct.setAmount(dto.getAmount());
		existingProduct.setPrice(dto.getPrice());
		existingProduct.setCategory(productCategoryService.findById(dto.getCategoryId()));

		attachMaterials(existingProduct, dto);
		attachTranslations(existingProduct, dto, isManualTranslation, defaultTranslation);
		attachColors(existingProduct, dto);

		persist(existingProduct);
		return new BasicMessageDto("Produkts tika atjaunots.");
	}

	/*
	 * Result of the delete (+ associated cascade-deletes):
	 * Product (SOFT)
	 * ProductTranslation (SOFT)
	 * ProductImage (HARD) (but need to also clear up S3-Bucket)
	 * ProductMaterials(HARD)
	 * Product-Colors sites (HARD)
	 */
	@Override
	@Transactional
	public BasicMessageDto deleteProduct(Integer id) {
		Product productForDelete = findById(id);
		List<ProductImage> productImages = productForDelete.getImages();
		productImages.stream().forEach(image -> productImageService.deleteImage(image.getId()));
		productRepository.delete(productForDelete);
		return new BasicMessageDto("Produkts veiksmīgi dzēsts");
	}

	@Override
	public ExistingProductDto getExistingProductForAdmin(Integer id) {
		Product product = findById(id);
		return productMapper.toExistingDto(findById(id), productMaterialService.getMaterialsPerProduct(product, LocaleCode.LV));
	}

	private void attachColors(Product newProduct, NewProductDto dto) {
		List<Integer> colorIds = dto.getColors();
		newProduct.getColors().clear();
		if (colorIds != null && !colorIds.isEmpty()) {
			newProduct.getColors().addAll(colorService.getAllColorsByIds(colorIds));
		}
	}

	private List<ProductImage> attachImages(Product newProduct, NewProductDto dto) {
		List<MultipartFile> imagesDto = dto.getImages();
		if (imagesDto == null || imagesDto.isEmpty())
			return Collections.emptyList();

		List<ProductImage> images = productImageService.proccessImagesAndUpload(newProduct, imagesDto);
		newProduct.getImages().addAll(images);
		return images; // for delete from s3 if persisting of product fails later
	}

	private void attachMaterials(Product newProduct, NewProductDto dto) {
		List<NewProductMaterialDto> materialsDto = dto.getMaterials();
		newProduct.getMadeOfMaterials().clear();
		entityManager.flush();

		if (materialsDto != null && !materialsDto.isEmpty()) {
			newProduct.getMadeOfMaterials().addAll(materialsDto.stream()
					.filter(pm -> pm.getPercentage() != null && pm.getPercentage() > 0)
					.map(pm -> ProductMaterial.builder()
							.material(materialService.findById(pm.getId()))
							.product(newProduct)
							.percentage(pm.getPercentage())
							.build())
					.toList());
		}
	}

	private void attachTranslations(Product newProduct, NewProductDto dto, boolean isManualTranslation, CreateProductTranslationDto defaultTranslation) {
		List<ProductTranslation> translations = new ArrayList<>();
		if (isManualTranslation) {
			dto.getTranslations().forEach(tr -> {
				translations.add(ProductTranslation.builder()
						.locale(tr.getLocale())
						.product(newProduct)
						.title(tr.getTitle())
						.description(tr.getDescription())
						.build());
			});
		} else {
			translations.add(ProductTranslation.builder()
					.locale(defaultTranslation.getLocale())
					.product(newProduct)
					.title(defaultTranslation.getTitle())
					.description(defaultTranslation.getDescription())
					.build());

			// In the system, at the moment, the are only "lv" and "en" locales.
			// So I am only translating to English, but this can be easily adjusted to be more dynamic if required
			translations.add(ProductTranslation.builder()
					.locale(LocaleCode.EN)
					.product(newProduct)
					.title(translationService.translateToEnglish(defaultTranslation.getTitle()))
					.description(
							defaultTranslation.getDescription() != null ? translationService.translateToEnglish(defaultTranslation.getDescription()) : null)
					.build());
		}

		// translations are soft deleted by default =>
		// but in this case we actually want them to be hard deleted or a unqiueness cosntraint will fire off every time!!
		productTranslationRepository.hardDeleteByProductId(newProduct.getId());
		newProduct.getNarrowTranslations().clear();
		newProduct.getNarrowTranslations().addAll(translations);
	}

	private void validateNewProductDto(NewProductDto dto, Product forUpdate) {
		Set<ConstraintViolation<NewProductDto>> violations = validator.validate(dto);
		Map<String, Object> errors = new HashMap<>();

		if (!violations.isEmpty()) {
			for (ConstraintViolation<?> violation : violations) {
				String path = violation.getPropertyPath().toString(); // e.g., translations[0].title
				String message = violation.getMessage();

				Matcher matcher = Pattern.compile("translations\\[(\\d+)]\\.(\\w+)").matcher(path);
				if (matcher.find()) {
					int index = Integer.parseInt(matcher.group(1));
					String field = matcher.group(2);

					String locale = "unknown";
					if (dto.getTranslations() != null && index < dto.getTranslations().size()) {
						LocaleCode lc = dto.getTranslations().get(index).getLocale();
						if (lc != null) {
							locale = lc.getCode();
						}
					}

					Map<String, String> subMap = (Map<String, String>) errors.computeIfAbsent(field, k -> new HashMap<>());
					subMap.put(locale, message);
				} else {
					errors.put(path, message);
				}
			}
			throw new NestedValidationException(errors);
		}

		boolean isCreating = forUpdate == null;

		dto.getTranslations().forEach(tr -> {
			String newTitle = tr.getTitle();
			LocaleCode forLocale = tr.getLocale();
			boolean titleAlreadyExists = productTranslationRepository.existsByTitleAndLocaleAndDeletedFalse(newTitle, forLocale);
			boolean titleRemainsTheSame = !isCreating && forUpdate.getNarrowTranslations()
					.stream()
					.filter(existingTr -> existingTr.getLocale() == forLocale && newTitle.equals(existingTr.getTitle()))
					.findFirst()
					.isPresent();

			if ((isCreating || !titleRemainsTheSame) && titleAlreadyExists) {
				Map<String, String> subMap = (Map<String, String>) errors.computeIfAbsent("title", k -> new HashMap<>());
				subMap.put(tr.getLocale().getCode(), "Norādīts nosaukums jau eksistē.");
			}
		});

		Integer categoryId = dto.getCategoryId();
		if (!productCategoryService.existsById(categoryId)) {
			errors.put("categoryId", "Norādītā kategorija neeksistē.");
		}

		List<Integer> colorIds = dto.getColors();
		if (colorIds != null) {
			for (Integer colorId : colorIds) {
				if (!colorService.existsById(colorId)) {
					errors.put("colors", "Norādītā krāsa nav atrasta.");
					break;
				}
			}
		}


		List<NewProductMaterialDto> materials = dto.getMaterials();
		if (materials != null) {
			int percentageTotal = 0;
			for (NewProductMaterialDto material : materials) {
				if (!materialService.existsById(material.getId())) {
					errors.put("materials", "Norādītais materiāls neeksistē.");
					break;
				}
				percentageTotal += material.getPercentage();
			}
			if (percentageTotal > 100) {
				errors.put("materials", "Kopējais materiālu procents nedrīkst pārsniegt 100%.");
			}
		}

		if (!errors.isEmpty())
			throw new NestedValidationException(errors);
	}

	private ProductTitleDto mapToProductTitleDto(Product product) {
		return productMapper.toProductTitleDto(product, getRightTranslation(product, ProductTranslation.class));
	}

	private SearchedProductDto mapToSearchedProductDto(ProductTranslation productTranslation) {
		Product product = productTranslation.getEntity();
		return productMapper.toSearchedProductDto(product, productTranslation, productImageService.getWallpaperByOwnerId(product.getId()));
	}

	private RandomProductDto mapToRandomProductDto(Product product) {
		return productMapper.toRandomDto(
				product,
				getRightTranslation(product, ProductTranslation.class),
				productCategoryService.mapToShortDto(product.getCategory()),
				productImageService.getTwoImagesForCover(product.getId()));
	}

	private ShortProductDto mapToShortProductDto(Product product) {
		return productMapper.toShortDto(
				product,
				getRightTranslation(product, ProductTranslation.class),
				productImageService.getTwoImagesForCover(product.getId()));
	}

	private ProductDto mapToProductDto(Product product) {
		Integer productId = product.getId();
		ProductTranslation localisedTranslation = getRightTranslation(product, ProductTranslation.class);
		ShortProductCategoryDto category = productCategoryService.mapToShortDto(product.getCategory());
		List<ImageDto> images = productImageService.getImagesAsDtoPerOwnerId(productId);
		// Many To Many relationship
		List<ColorDto> colors = product.getColors().stream().map(colorService::mapColorToDto).toList();
		List<ProductMaterialDto> materials = productMaterialService.getProductMaterialsPerProductId(productId);
		return productMapper.toDto(product, localisedTranslation, category, images, colors, materials);
	}
}
