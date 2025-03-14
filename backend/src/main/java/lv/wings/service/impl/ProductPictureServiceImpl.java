package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.ProductPicture;
import lv.wings.model.translation.ProductPictureTranslation;
import lv.wings.repo.ProductPictureRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductService;

@Service
public class ProductPictureServiceImpl extends AbstractTranslatableCRUDService<ProductPicture, ProductPictureTranslation, Integer> {

	public ProductPictureServiceImpl(ProductPictureRepository productPictureRepo, LocaleService localeService) {
		super(productPictureRepo, "ProductPicture", "entity.product-picture", localeService);
	}


}
