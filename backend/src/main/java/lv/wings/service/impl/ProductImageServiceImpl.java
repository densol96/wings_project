package lv.wings.service.impl;

import org.springframework.stereotype.Service;

import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductImageTranslation;
import lv.wings.repo.ImageRepository;
import lv.wings.service.AbstractImageService;
import lv.wings.service.LocaleService;

@Service
public class ProductImageServiceImpl extends AbstractImageService<ProductImage, Product, ProductImageTranslation, Integer> {

    public ProductImageServiceImpl(ImageRepository<ProductImage, Integer> productImageRepo, LocaleService localeService) {
        super(productImageRepo, ProductImageTranslation.class, "ProductImage", "entity.product-image", localeService);
    }
}
