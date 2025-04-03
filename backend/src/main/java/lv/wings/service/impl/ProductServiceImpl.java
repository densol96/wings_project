package lv.wings.service.impl;

import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;

import lv.wings.mapper.ProductMapper;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.service.AbstractTranslatableCRUDService;
import lv.wings.service.ColorService;
import lv.wings.service.ImageService;
import lv.wings.service.LocaleService;
import lv.wings.service.ProductCategoryService;
import lv.wings.service.ProductMaterialService;
import lv.wings.service.ProductService;

@Service
public class ProductServiceImpl extends AbstractTranslatableCRUDService<Product, ProductTranslation, Integer> implements ProductService {

	private final ProductRepository productRepository;
	private final ProductMapper productMapper;
	private final ImageService<ProductImage, Integer> productImageService;
	private final ProductCategoryService productCategoryService;
	private final ColorService colorService;
	private final ProductMaterialService productMaterialService;

	@Lazy
	public ProductServiceImpl(
			ProductRepository productRepository,
			ProductMapper productMapper,
			ImageService<ProductImage, Integer> productImageService,
			LocaleService localeService,
			ProductCategoryService productCategoryService,
			ColorService colorService,
			ProductMaterialService productMaterialService) {
		super(productRepository, "Product", "entity.product", localeService);
		this.productRepository = productRepository;
		this.productMapper = productMapper;
		this.productImageService = productImageService;
		this.productCategoryService = productCategoryService;
		this.colorService = colorService;
		this.productMaterialService = productMaterialService;
	}

	@Override
	public Page<ShortProductDto> getAllByCategory(Integer categoryId, Pageable pageable) {
		if (categoryId < 0)
			throw new lv.wings.exception.validation.InvalidParameterException(entityNameKey, entityName, false);
		// categoryId == 0 means "All products"
		Page<Product> products = categoryId == 0 ? findAll(pageable) : productRepository.findAllByCategoryId(categoryId, pageable);
		return products.map(this::mapToShortProductDto);
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Product product = findById(id);
		return mapToProductDto(product);
	}

	private ShortProductDto mapToShortProductDto(Product product) {
		ProductTranslation localisedTranslation = getRightTranslation(product, ProductTranslation.class);
		List<ImageDto> images = productImageService.getImagesAsDtoPerOwnerId(product.getId());
		// Only 2 images are required on the fromt-end for an interactive card
		List<ImageDto> limitedImages = images.size() > 2 ? images.subList(0, Math.min(images.size(), 2)) : images;
		return productMapper.toShortDto(product, localisedTranslation, limitedImages);
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
