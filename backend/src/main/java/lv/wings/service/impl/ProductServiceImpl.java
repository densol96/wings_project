package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.context.annotation.Lazy;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.dto.response.ImageDto;
import lv.wings.dto.response.color.ColorDto;
import lv.wings.dto.response.product.ProductDto;
import lv.wings.dto.response.product.ProductMaterialDto;
import lv.wings.dto.response.product.RandomProductDto;
import lv.wings.dto.response.product.SearchedProductDto;
import lv.wings.dto.response.product.ShortProductDto;
import lv.wings.dto.response.product_category.ShortProductCategoryDto;
import lv.wings.enums.LocaleCode;
import lv.wings.exception.validation.InvalidParameterException;
import lv.wings.mapper.ProductMapper;
import lv.wings.model.entity.Product;
import lv.wings.model.entity.ProductImage;
import lv.wings.model.translation.ProductTranslation;
import lv.wings.repo.ProductRepository;
import lv.wings.repo.ProductTranslationRepository;
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
	private final ProductTranslationRepository productTranslationRepository;
	private final ProductMapper productMapper;
	private final ImageService<ProductImage, Integer> productImageService;
	private final ProductCategoryService productCategoryService;
	private final ColorService colorService;
	private final ProductMaterialService productMaterialService;

	// From the previous request
	private List<Product> randomProducts;
	private LocaleCode lastRequestedLocaleCode;

	@Lazy
	public ProductServiceImpl(
			ProductRepository productRepository,
			ProductTranslationRepository productTranslationRepository,
			ProductMapper productMapper,
			ImageService<ProductImage, Integer> productImageService,
			LocaleService localeService,
			ProductCategoryService productCategoryService,
			ColorService colorService,
			ProductMaterialService productMaterialService) {
		super(productRepository, "Product", "entity.product", localeService);
		this.productTranslationRepository = productTranslationRepository;
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
			throw new InvalidParameterException("category.id", categoryId + "", false);
		// categoryId == 0 means "All products"
		Page<Product> products = categoryId == 0 ? findAll(pageable) : productRepository.findAllByCategoryId(categoryId, pageable);
		return products.map(this::mapToShortProductDto);
	}

	@Override
	public ProductDto getProductById(Integer id) {
		Product product = findById(id);
		return mapToProductDto(product);
	}

	@Override
	public List<RandomProductDto> getRandomProducts(Integer categoryId, Integer amount) {
		if (categoryId < 0)
			throw new InvalidParameterException("category.id", categoryId + "", false);
		if (amount < 1)
			throw new InvalidParameterException("general.amount", amount + "", false);

		boolean isAllCategories = categoryId == 0;

		/**
		 * Cache randomised list.
		 * 
		 * If a new locale => user wants to see the translation of the previously randomly assembled list.
		 * Else, randomly select a new list.
		 */

		if (randomProducts == null || localeService.getCurrentLocaleCode() == lastRequestedLocaleCode) {
			randomProducts = isAllCategories
					? productRepository.findAvaialableRandomProductsFromAll(amount)
					: productRepository.findAvaialableRandomProductsByCategory(categoryId, amount);

			/**
			 * Ideally, we do not want to show products that are currently unavailable (aka amount = 0), however if there are no enough avaialable products, use
			 * unavailable * as it might still interest the customer and he / she will add the product page to Bookmarks and check it later.
			 * 
			 * Or if we are doing the search by category, get available products from other categories to advertise.
			 */

			int missing = amount - randomProducts.size();
			if (missing > 0) {
				if (!isAllCategories) {
					randomProducts.addAll(productRepository.findAvaialableRandomProductsFromAll(missing));
					missing = amount - randomProducts.size();
				}
				if (missing > 0) {
					if (!isAllCategories) {
						randomProducts.addAll(productRepository.findUnavaialableRandomProductsByCategory(categoryId, missing));
						missing = amount - randomProducts.size();
					}
					if (missing > 0) {
						randomProducts.addAll(productRepository.findUnavaialableRandomProductsFromAll(missing));
					}
				}
			}
		}
		lastRequestedLocaleCode = localeService.getCurrentLocaleCode();
		return randomProducts.stream().map(this::mapToRandomProductDto).toList();
	}

	@Override
	public List<SearchedProductDto> getSearchedProducts(String q) {
		if (q.equals(""))
			return new ArrayList<>();

		return productTranslationRepository.findByTitleContainingIgnoreCaseAndLocaleEquals(q, localeService.getCurrentLocaleCode()).stream()
				.map(this::mapToSearchedProductDto)
				.toList();
	}

	private SearchedProductDto mapToSearchedProductDto(ProductTranslation productTranslation) {
		Product product = productTranslation.getEntity();
		return productMapper.toSearchedProductDto(product, productTranslation, productImageService.getWallpaperByOwnerId(product.getId()));
	}

	private RandomProductDto mapToRandomProductDto(Product product) {
		return productMapper.toRandomDto(
				product,
				getRightTranslation(product, ProductTranslation.class),
				productCategoryService.mapToShortDto(product.getCategory()),
				productImageService.getTwoImagesForCover(product.getId()));
	}

	private ShortProductDto mapToShortProductDto(Product product) {
		return productMapper.toShortDto(
				product,
				getRightTranslation(product, ProductTranslation.class),
				productImageService.getTwoImagesForCover(product.getId()));
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
