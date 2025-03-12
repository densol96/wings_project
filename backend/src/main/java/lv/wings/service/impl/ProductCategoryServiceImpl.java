package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lv.wings.model.entity.ProductCategory;
import lv.wings.model.translation.ProductCategoryTranslation;
import lv.wings.repo.ProductCategoryRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;

@Service
public class ProductCategoryServiceImpl extends AbstractTranslatableCRUDService<ProductCategory, ProductCategoryTranslation, Integer> {

	public ProductCategoryServiceImpl(ProductCategoryRepository productCategoryRepo, LocaleService localeService) {
		super(productCategoryRepo, "ProductCategory", "entity.product-category", localeService);
	}

}
