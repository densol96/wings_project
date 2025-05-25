package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import jakarta.transaction.Transactional;
import lombok.NonNull;
import lv.wings.dto.request.admin.products.CreateProductTranslationDto;
import lv.wings.dto.request.admin.products.NewProductDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.mapper.ProductCategoryMapper;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductService;

@Service
public class ProductCategoryServiceImpl extends AbstractTranslatableCRUDService<ProductCategory, ProductCategoryTranslation, Integer>
		implements ProductCategoryService {

	private final ProductService productService;
	private final ProductCategoryMapper mapper;

	public ProductCategoryServiceImpl(
			ProductCategoryRepository productCategoryRepo,
			LocaleService localeService,
			ProductService productService,
			ProductCategoryMapper mapper) {
		super(productCategoryRepo, "ProductCategory", "entity.product-category", localeService);
		this.productService = productService;
		this.mapper = mapper;
	}

	@Override
	public List<ProductCategoryWithAmountDto> getAllCategories() {
		List<ProductCategoryWithAmountDto> categories = new ArrayList<>(findAll().stream()
				.map(this::categoryToWithAmountDto)
				.toList());
		categories.add(0, getSpecialAllCategoryWithAmount());
		return categories;
	}

	@Override
	public ProductCategoryDto getCategory(Integer id) {
		return mapper.translationToDto(getRightTranslation(findById(id), ProductCategoryTranslation.class));
	}

	@Override
	public ShortProductCategoryDto getShortCategory(Integer id) {
		return mapToShortDto(findById(id));
	}


	/**
	 * This method is intended for cases where a ProductCategory entity is already loaded
	 * in the current Hibernate session (e.g., by another service).
	 *
	 * It would be inefficient to use a method that accepts only an ID and re-fetches
	 * the entity from the database unnecessarily.
	 * 
	 * Since this method is part of a public interface, the @NonNull annotation is used
	 * to ensure that a NullPointerException is not thrown at runtime.
	 */
	@Override
	public ShortProductCategoryDto mapToShortDto(@NonNull ProductCategory category) {
		return mapper.translationToShortDto(category, getRightTranslation(category, ProductCategoryTranslation.class));
	}

	@Override
	@Transactional
	public BasicMessageDto createCategory(NewCategoryDto dto) {
		validateNewProductDto(dto, null);
		List<CreateProductTranslationDto> providedTranslations = dto.getTranslations();
		CreateProductTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
		localeService.validateOneTranslationPerEachLocale(providedTranslations);
		boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);

		Product newProduct = Product.builder().amount(dto.getAmount()).price(dto.getPrice())
				.category(productCategoryService.findById(dto.getCategoryId())) // has been pre-validated
				.build();

		attachTranslations(newProduct, dto, isManualTranslation, defaultTranslation);
		attachMaterials(newProduct, dto);
		attachColors(newProduct, dto);
		List<ProductImage> images = attachImages(newProduct, dto.getImages());

		try {
			persist(newProduct);
		} catch (Exception e) {
			if (images != null) {
				productImageService.clearImagesUp(images, null);
			}
		}

		return new BasicMessageDto("Jauns produkts tika pievienots.");
	}

	private ProductCategoryWithAmountDto getSpecialAllCategoryWithAmount() {
		return ProductCategoryWithAmountDto.builder()
				.id(0)
				.title(localeService.getMessage("translation.all-products"))
				.productsTotal(productService.count())
				.build();
	}

	private ProductCategoryWithAmountDto categoryToWithAmountDto(ProductCategory category) {
		return mapper.toWithAmountDto(category, getRightTranslation(category, ProductCategoryTranslation.class));
	}
}
