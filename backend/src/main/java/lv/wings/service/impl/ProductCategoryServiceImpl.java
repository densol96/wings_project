package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import lv.wings.dto.response.product_category.ProductCategoryDto;
import lv.wings.dto.response.product_category.ProductCategoryWithAmountDto;
import lv.wings.mapper.ProductCategoryMapper;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductService;

@Service
public class ProductCategoryServiceImpl extends AbstractTranslatableCRUDService<ProductCategory, ProductCategoryTranslation, Integer> implements ProductCategoryService {

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

	private ProductCategoryWithAmountDto getSpecialAllCategoryWithAmount() {
		return ProductCategoryWithAmountDto.builder()
				.id(0)
				.title(localeService.getMessage("translation.all"))
				.productsTotal(productService.count())
				.build();
	}

	private ProductCategoryWithAmountDto categoryToWithAmountDto(ProductCategory category) {
		ProductCategoryTranslation translation = getRightTranslation(category, ProductCategoryTranslation.class);
		return mapper.toWithAmountDto(category, translation.getTitle());
	}
}
