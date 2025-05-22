package lv.wings.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;
import org.springframework.boot.autoconfigure.web.WebProperties.LocaleResolver;
import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import lv.wings.config.interceptors.locale.RequestParamLocaleResolver;
import lv.wings.dto.request.admin.products.CreateProductTranslationDto;
import lv.wings.dto.request.admin.products.NewProductDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.ImageDto;
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
import lv.wings.enums.TranslationMethod;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.validation.InvalidFieldsException;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.exception.validation.NestedValidationException;
import lv.wings.exception.validation.NonLocalisedException;
import lv.wings.mapper.ProductMapper;
import lv.wings.model.base.OwnerableEntity;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductImageTranslation;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.repo.ProductTranslationRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ColorService;
import lv.wings.service.ImageService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductMaterialService;
import lv.wings.service.ProductService;
import lv.wings.service.S3Service;
import lv.wings.service.TranslationService;
import jakarta.persistence.EntityManager;
import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ValidationException;
import jakarta.validation.Validator;

@Service
@Slf4j
public class ProductServiceImpl extends AbstractTranslatableCRUDService<Product, ProductTranslation, Integer> implements ProductService {

	private final ProductRepository productRepository;
	private final ProductTranslationRepository productTranslationRepository;
	private final ProductMapper productMapper;
	private final ImageService<ProductImage, Integer> productImageService;
	private final ProductCategoryService productCategoryService;
	private final ColorService colorService;
	private final ProductMaterialService productMaterialService;
	private final Validator validator;
	private final TranslationService translationService;
	private final S3Service imageUploader;
	// From the previous request
	private List<Product> randomProducts;
	private LocaleCode lastRequestedLocaleCode;

	@Lazy
	public ProductServiceImpl(
			ProductRepository productRepository,
			ProductTranslationRepository productTranslationRepository,
			ProductMapper productMapper,
			ImageService<ProductImage, Integer> productImageService,
			LocaleService localeService,
			ProductCategoryService productCategoryService,
			ColorService colorService,
			ProductMaterialService productMaterialService,
			Validator validator,
			TranslationService translationService,
			S3Service imageUploader) {
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
		this.imageUploader = imageUploader;
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
		// use Validation Annotations to do basic format validation
		validateProductDto(dto);

		// self-explanotory error messages
		LocaleCode defaultLocale = localeService.getDefaultLocale();
		List<CreateProductTranslationDto> providedTranslations = dto.getTranslations();

		CreateProductTranslationDto defaultTranslation = providedTranslations
				.stream()
				.filter(tr -> tr.getLocale() == defaultLocale)
				.findFirst()
				.orElseThrow(() -> new NonLocalisedException("Noklusētā valoda (" + defaultLocale + ") vienmēr ir obligāta, veidojot jaunu ierakstu.",
						HttpStatus.UNPROCESSABLE_ENTITY));

		Set<LocaleCode> providedLocales = providedTranslations.stream().map(l -> l.getLocale()).collect(Collectors.toSet());

		if (providedTranslations.size() != providedLocales.size())
			throw new NonLocalisedException("Valodu sarakstā ir dublikāti — katrai valodai jābūt tikai vienam tulkojumam.", HttpStatus.UNPROCESSABLE_ENTITY);

		TranslationMethod translationMethod = dto.getTranslationMethod();

		boolean isManualTranslation = translationMethod == TranslationMethod.MANUAL;

		if (isManualTranslation && (providedTranslations.size() != localeService.getAllowedLocales().size()))
			throw new NonLocalisedException("Manuālajā režīmā jānorāda tulkojumi visās atbalstītajās valodās.", HttpStatus.UNPROCESSABLE_ENTITY);

		Product newProduct = Product.builder()
				.amount(dto.getAmount())
				.price(dto.getPrice())
				.category(productCategoryService.findById(1)) // placeholder for now TODO []
				.build();


		List<ProductTranslation> translations;
		if (translationMethod == TranslationMethod.MANUAL) {
			translations = new ArrayList<>();
			dto.getTranslations().forEach(tr -> {
				// default
				translations.add(ProductTranslation.builder()
						.locale(tr.getLocale())
						.product(newProduct)
						.title(tr.getTitle())
						.description(tr.getDescription())
						.build());
			});
			newProduct.setTranslations(translations);
		} else {
			translations = new ArrayList<>();
			translations.add(ProductTranslation.builder()
					.locale(defaultTranslation.getLocale())
					.product(newProduct)
					.title(defaultTranslation.getTitle())
					.description(defaultTranslation.getDescription())
					.build());

			// in the system at the moment the are only lv and en locales. so I am only translating to English, but this can be easily adjusted to be more
			// dynamic if required
			translations.add(ProductTranslation.builder()
					.locale(LocaleCode.EN)
					.product(newProduct)
					.title(translationService.translateToEnglish(defaultTranslation.getTitle()))
					.description(
							defaultTranslation.getDescription() != null ? translationService.translateToEnglish(defaultTranslation.getDescription()) : null)
					.build());
		}

		newProduct.setTranslations(translations);


		List<ProductImage> images = proccessImagesAndUpload(newProduct, dto.getImages());
		newProduct.setImages(images);
		try {
			persist(newProduct);
		} catch (Exception e) {
			clearImagesUp(images, null);
		}

		return new BasicMessageDto("Jauns produkts tika pievienots.");
	}

	private List<ProductImage> proccessImagesAndUpload(Product newProduct, List<MultipartFile> dtoImages) {
		List<ProductImage> images = new ArrayList<>();

		dtoImages.stream().forEach((image -> {
			try {
				String url = imageUploader.uploadFile(image, "products");
				ProductImage productImage = new ProductImage(newProduct, url);
				List<ProductImageTranslation> imageTranslations = new ArrayList<>();
				imageTranslations.add(
						new ProductImageTranslation(
								LocaleCode.LV,
								productImage,
								String.format("Produkta %s attels Nr. %s",
										getSelectedTranslation(newProduct, LocaleCode.LV).getTitle(),
										images.size() + 1)));
				imageTranslations.add(
						new ProductImageTranslation(
								LocaleCode.EN,
								productImage,
								String.format("Product %s image Nr. %s",
										getSelectedTranslation(newProduct, LocaleCode.EN).getTitle(),
										images.size() + 1)));
				productImage.setTranslations(imageTranslations);
				images.add(productImage);
			} catch (Exception e) {
				log.error(e.getMessage());
				if (images.size() > 0) {
					clearImagesUp(images, image);
				}
				throw new RuntimeException("Failed to upload image: " + image.getOriginalFilename(), e); // Will trigger a procedural exception
			}
		}));

		return images;
	}

	private void clearImagesUp(List<ProductImage> images, MultipartFile faultyImage) {
		try {
			images.forEach(alreadyUploaded -> {
				imageUploader.deleteFile(alreadyUploaded.getSrc());
			});
		} catch (Exception ex) {
			log.error(ex.getMessage());
			if (faultyImage == null) {
				throw new RuntimeException("Failed to save to DB links to the uploaded images => required image clean up",
						ex);
			} else {
				throw new RuntimeException("Failed to upload image: " + faultyImage.getOriginalFilename() + " and failed to clear already uploaded images",
						ex);
			}

		}
	}

	private void validateProductDto(NewProductDto dto) {
		Set<ConstraintViolation<NewProductDto>> violations = validator.validate(dto);

		if (!violations.isEmpty()) {
			Map<String, Object> errors = new HashMap<>();

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
