package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lv.wings.model.entity.Product;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;

@Service
public class ProductServiceImpl extends AbstractTranslatableCRUDService<Product, ProductTranslation, Integer> {

	public ProductServiceImpl(ProductRepository productRepository, LocaleService localeService) {
		super(productRepository, "Product", "entity.product", localeService);
	}
}
