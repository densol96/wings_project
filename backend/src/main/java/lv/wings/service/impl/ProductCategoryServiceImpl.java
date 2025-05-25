package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import jakarta.transaction.Transactional;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

import lombok.NonNull;

import lv.wings.dto.request.admin.products.CreateCategoryTranslationDto;
import lv.wings.dto.request.admin.products.NewCategoryDto;
import lv.wings.dto.response.BasicMessageDto;
import lv.wings.dto.response.admin.products.AdminProductCategoryDto;
import lv.wings.dto.response.admin.products.EditCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.entity.EntityNotFoundException;
import lv.wings.exception.validation.ConstraintValidationException;
import lv.wings.exception.validation.NestedValidationException;
import lv.wings.mapper.ProductCategoryMapper;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.interfaces.LocalableWithTitle;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.repo.ProductCategoryTranslationRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductService;
import lv.wings.service.TranslationService;
import lv.wings.util.CustomValidator;

@Service
public class ProductCategoryServiceImpl extends AbstractTranslatableCRUDService<ProductCategory, ProductCategoryTranslation, Integer>
		implements ProductCategoryService {

	private final ProductCategoryRepository productCategoryRepo;
	private final ProductCategoryTranslationRepository productCategoryTranslationRepo;
	private final ProductService productService;
	private final ProductCategoryMapper productCategoryMapper;
	private final Validator validator;
	private final TranslationService translationService;

	public ProductCategoryServiceImpl(
			ProductCategoryRepository productCategoryRepo,
			ProductCategoryTranslationRepository productCategoryTranslationRepo,
			LocaleService localeService,
			ProductService productService,
			ProductCategoryMapper productCategoryMapper,
			Validator validator,
			TranslationService translationService) {
		super(productCategoryRepo, "ProductCategory", "entity.product-category", localeService);
		this.productCategoryRepo = productCategoryRepo;
		this.productCategoryTranslationRepo = productCategoryTranslationRepo;
		this.productService = productService;
		this.productCategoryMapper = productCategoryMapper;
		this.validator = validator;
		this.translationService = translationService;
	}

	@Override
	public List<ProductCategoryWithAmountDto> getAllCategories() {
		List<ProductCategoryWithAmountDto> categories = new ArrayList<>(findAll().stream()
				.filter((category -> !category.isDeleted()))
				.map(this::categoryToWithAmountDto)
				.toList());
		categories.add(0, getSpecialAllCategoryWithAmount());
		return categories;
	}

	@Override
	public List<AdminProductCategoryDto> getAllAdminCategories(Sort sort) {
		List<AdminProductCategoryDto> categories = new ArrayList<>(productCategoryRepo.findAll(sort).stream()
				.filter((category -> !category.isDeleted()))
				.map(productCategoryMapper::toAdminDto)
				.toList());
		return categories;
	}

	@Override
	public ProductCategoryDto getCategory(Integer id) {
		return productCategoryMapper.translationToDto(getRightTranslation(findByIdAndNotDeleted(id), ProductCategoryTranslation.class));
	}

	@Override
	public ShortProductCategoryDto getShortCategory(Integer id) {
		return mapToShortDto(findByIdAndNotDeleted(id));
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
		return productCategoryMapper.translationToShortDto(category, getRightTranslation(category, ProductCategoryTranslation.class));
	}

	@Override
	@Transactional
	public BasicMessageDto createCategory(NewCategoryDto dto) {
		validateNewCategoryDto(dto, null);
		List<CreateCategoryTranslationDto> providedTranslations = dto.getTranslations();
		CreateCategoryTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
		localeService.validateOneTranslationPerEachLocale(providedTranslations);
		boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);
		ProductCategory newCategory = new ProductCategory();
		attachTranslations(newCategory, dto, isManualTranslation, defaultTranslation);
		persist(newCategory);
		return new BasicMessageDto("Jauna kategorija tika pievienota.");
	}

	@Override
	@Transactional
	public BasicMessageDto updateCategory(NewCategoryDto dto, Integer id) {
		ProductCategory existingCategory = findByIdAndNotDeleted(id);
		validateNewCategoryDto(dto, existingCategory);
		List<CreateCategoryTranslationDto> providedTranslations = dto.getTranslations();
		CreateCategoryTranslationDto defaultTranslation = localeService.validateDefaultLocaleIsPresent(providedTranslations);
		localeService.validateOneTranslationPerEachLocale(providedTranslations);
		boolean isManualTranslation = localeService.validateRequiredTranslationsPresentIfManual(dto);
		attachTranslations(existingCategory, dto, isManualTranslation, defaultTranslation);
		persist(existingCategory);
		return new BasicMessageDto("Kategorija tika atjaunota.");
	}

	@Override
	@Transactional
	public BasicMessageDto deleteCategory(Integer id) {
		ProductCategory categoryForDelete = findByIdAndNotDeleted(id);
		categoryForDelete.getProducts().stream().filter(pr -> !pr.isDeleted()).forEach(product -> productService.deleteProduct(product.getId()));
		// to trigger deleting images from S3
		productCategoryRepo.delete(categoryForDelete);
		return new BasicMessageDto("Kategorija veiksmīgi dzēsta");
	}

	@Override
	public ProductCategory findByIdAndNotDeleted(Integer id) {
		return productCategoryRepo.findByIdAndDeletedFalse(id).orElseThrow(() -> new EntityNotFoundException(entityNameKey, entityName, id));
	}

	@Override
	public EditCategoryDto getExistingCategoryForAdmin(Integer id) {
		return productCategoryMapper.toEditDto(findByIdAndNotDeleted(id));
	}


	private void attachTranslations(
			ProductCategory newCategory,
			NewCategoryDto dto,
			boolean isManualTranslation,
			CreateCategoryTranslationDto defaultTranslation) {
		List<ProductCategoryTranslation> translations = new ArrayList<>();
		if (isManualTranslation) {
			dto.getTranslations().forEach(tr -> {
				translations.add(ProductCategoryTranslation.builder()
						.locale(tr.getLocale())
						.category(newCategory)
						.title(tr.getTitle())
						.description(tr.getDescription())
						.build());
			});
		} else {
			translations.add(ProductCategoryTranslation.builder()
					.locale(defaultTranslation.getLocale())
					.category(newCategory)
					.title(defaultTranslation.getTitle())
					.description(defaultTranslation.getDescription())
					.build());

			translations.add(ProductCategoryTranslation.builder()
					.locale(LocaleCode.EN)
					.category(newCategory)
					.title(translationService.translateToEnglish(defaultTranslation.getTitle()))
					.description(
							defaultTranslation.getDescription() != null ? translationService.translateToEnglish(defaultTranslation.getDescription()) : null)
					.build());
		}

		/*
		 * translations are soft deleted by default =>
		 * but in this case we actually want them to be hard deleted or a unqiueness constraint will fire off every time!!
		 * on top of that see the note about the Hibernate behaviour in the comment above!
		 */
		productCategoryTranslationRepo.hardDeleteByCategoryId(newCategory.getId());
		newCategory.getNarrowTranslations().clear();
		newCategory.getNarrowTranslations().addAll(translations);
	}

	private void validateNewCategoryDto(NewCategoryDto dto, ProductCategory forUpdate) {
		Set<ConstraintViolation<NewCategoryDto>> violations = validator.validate(dto);
		if (!violations.isEmpty()) {
			throw new ConstraintValidationException((Set<ConstraintViolation<?>>) (Set<?>) violations);
		}

		Map<String, Object> errors = new HashMap<>();
		boolean isCreating = forUpdate == null;
		CustomValidator.validateTitleUniqueness(dto, productCategoryTranslationRepo, errors,
				isCreating ? null : forUpdate.getNarrowTranslations().stream().map(tr -> (LocalableWithTitle) tr).toList());

		if (!errors.isEmpty())
			throw new NestedValidationException(errors);
	}

	private ProductCategoryWithAmountDto getSpecialAllCategoryWithAmount() {
		return ProductCategoryWithAmountDto.builder()
				.id(0)
				.title(localeService.getMessage("translation.all-products"))
				.productsTotal(productService.count())
				.build();
	}

	private ProductCategoryWithAmountDto categoryToWithAmountDto(ProductCategory category) {
		return productCategoryMapper.toWithAmountDto(category, getRightTranslation(category, ProductCategoryTranslation.class));
	}
}
