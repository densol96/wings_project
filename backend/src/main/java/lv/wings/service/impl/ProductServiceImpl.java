package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lv.wings.model.entity.Product;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductService;

@Service
public class ProductServiceImpl extends AbstractTranslatableCRUDService<Product, ProductTranslation, Integer> implements ProductService {

	public ProductServiceImpl(ProductRepository productRepository, LocaleService localeService) {
		super(productRepository, "Product", "entity.product", localeService);
	}
}
