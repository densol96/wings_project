package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import lv.wings.model.Product;
import lv.wings.model.ProductCategory;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.service.ICRUDService;

@Service
public class ProductCategoryServiceImpl implements ICRUDService<ProductCategory> {

	@Autowired
	private IProductCategoryRepo productCategoriesRepo;

	@Autowired
	private IProductRepo productRepo;

	@Override
	@Cacheable("ProductCategories")
	public List<ProductCategory> retrieveAll() {
		// izmest izņēmumu, ja ir tukša tabula
		if (productCategoriesRepo.count() == 0)
			throw new RuntimeException("Product category table is empty");
		return productCategoriesRepo.findAll();
	}

	@Override
	@Cacheable(value = "ProductCategories", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<ProductCategory> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {

		if (productCategoriesRepo.count() == 0)
			throw new RuntimeException("Product category table is empty");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));

		return (Page<ProductCategory>) productCategoriesRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "ProductCategories", key = "#id")
	public ProductCategory retrieveById(Integer id) {
		if (id < 0)
			throw new RuntimeException("Id should be positive");

		if (productCategoriesRepo.existsById(id)) {
			return productCategoriesRepo.findById(id).get();
		} else {
			throw new RuntimeException("Product category with this id (" + id + ") is not in system");
		}
	}

	@Override
	@CacheEvict(value = "ProductCategories", allEntries = true)
	public void deleteById(Integer id) {
		if (retrieveById(id) == null)
			throw new RuntimeException("Product category with (id:" + id + ") does not exist!");

		// atrast kategoriju kuru gribam dzēst
		ProductCategory ProductCategoryForDeleting = retrieveById(id);

		List<Product> product = productRepo.findByProductCategory(ProductCategoryForDeleting);

		for (Integer i = 0; i < product.size(); i++) {
			product.get(i).setProductCategory(null);
			productRepo.save(product.get(i));
		}

		// dzēšam no repo un DB
		productCategoriesRepo.delete(ProductCategoryForDeleting);
	}

	@Override
	@CacheEvict(value = "ProductCategories", allEntries = true)
	public void create(ProductCategory category) {
		ProductCategory existingProductCategory = productCategoriesRepo.findByTitleAndDescription(category.getTitle(),
				category.getDescription());

		// tāda category jau eksistē
		if (existingProductCategory != null)
			throw new RuntimeException("Product category with name: " + category.getTitle() + " already exists in DB!");

		// tāds driver vēl neeksistē
		productCategoriesRepo.save(category);

	}

	@Override
	@CacheEvict(value = "ProductCategories", allEntries = true)
	@CachePut(value = "ProductCategories", key = "#id")
	public void update(Integer id, ProductCategory category) {
		// atrodu
		ProductCategory productCategoryForUpdating = retrieveById(id);

		// izmainu
		productCategoryForUpdating.setTitle(category.getTitle());
		productCategoryForUpdating.setDescription(category.getDescription());
		productCategoryForUpdating.setProducts(category.getProducts());

		// saglabāju repo un DB
		productCategoriesRepo.save(productCategoryForUpdating);

	}

}
