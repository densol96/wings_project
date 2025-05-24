package lv.wings.service.impl;

import org.springframework.stereotype.Service;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

import lv.wings.enums.LocaleCode;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductImageTranslation;
import lv.wings.repo.base.ImageRepository;
import lv.wings.service.AbstractImageService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductService;
import lv.wings.service.shared.GenericImageProcessor;


@Slf4j
@Service
public class ProductImageService extends AbstractImageService<ProductImage, Product, ProductImageTranslation, Integer> {

    private final ProductService productService;

    public ProductImageService(
            ImageRepository<ProductImage, Integer> repository,
            ProductService productService,
            LocaleService localeService,
            GenericImageProcessor imageProcessor) {
        super(repository,
                ProductImageTranslation.class,
                "product-image",
                "entity.product-image",
                localeService,
                imageProcessor,
                "products");

        this.productService = productService;
    }

    @Override
    protected ProductImage getImageInstance(Product parentProduct, String url, Integer position) {
        return new ProductImage(parentProduct, url, position);
    }

    @Override
    protected ProductImageTranslation createImageTranslation(LocaleCode locale, ProductImage image, String title) {
        return new ProductImageTranslation(locale, image, title);
    }

    @Override
    protected String getTitleFromParent(Product parentProduct, LocaleCode locale) {
        return productService.getSelectedTranslation(parentProduct, locale).getTitle();
    }

    @Override
    protected Product getOwnerById(@NonNull Integer id) {
        return productService.findById(id);
    }
}
