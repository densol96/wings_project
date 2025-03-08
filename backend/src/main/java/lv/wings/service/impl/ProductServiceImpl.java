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
import lv.wings.model.ProductPicture;
import lv.wings.model.PurchaseElement;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.service.ICRUDInsertedService;
import lv.wings.service.IProductsFilterService;

@Service
public class ProductServiceImpl implements ICRUDInsertedService<Product>, IProductsFilterService {

	@Autowired
	private IProductRepo productRepo;

	@Autowired
	private IProductCategoryRepo productCategoryRepo;

	@Autowired
	private IPurchaseElementRepo elementRepo;

	@Autowired
	private IProductPictureRepo pictureRepo;

	@Override
	@Cacheable("Products")
	public List<Product> retrieveAll() {
		// izmest izņēmumu, ja ir tukša tabula
		if (productRepo.count() == 0)
			throw new RuntimeException("There are no products");

		// pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Product>) productRepo.findAll();
	}

	@Override
	@Cacheable(value = "Products", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<Product> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
		if (productRepo.count() == 0)
			throw new RuntimeException("There are no products");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
		return (Page<Product>) productRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "Products", key = "#id")
	public Product retrieveById(Integer id) {
		if (id < 0)
			throw new RuntimeException("Id should be positive");

		if (productRepo.existsById(id)) {
			return productRepo.findById(id).get();
		} else {
			throw new RuntimeException("Product with this id (" + id + ") does not exist");
		}
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	public void deleteById(Integer id) {
		// atrast preci kuru gribam dzēst
		Product productForDeleting = retrieveById(id);

		// Ja tiek dzēsts pirkuma elements, tad pirkuma elementā prece tiek setota kā
		// null
		List<PurchaseElement> purchaseElement = elementRepo.findByProduct(productForDeleting);

		for (Integer i = 0; i < purchaseElement.size(); i++) {
			purchaseElement.get(i).setProduct(null);
			elementRepo.save(purchaseElement.get(i));
		}

		// Ja tiek dzēsta preces bilde, tad preces bildē prece tiek setota kā null
		List<ProductPicture> productPictures = pictureRepo.findByProduct(productForDeleting);

		for (Integer i = 0; i < productPictures.size(); i++) {
			productPictures.get(i).setProduct(null);
			pictureRepo.save(productPictures.get(i));
		}

		// dzēšam no repo un DB
		productRepo.delete(productForDeleting);
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	public void create(Product product) {
		Product existedProduct = productRepo.findByTitle(product.getTitle());

		// tāds pirkums jau eksistē
		if (existedProduct != null)
			throw new RuntimeException("Product with name: " + product.getTitle() + " already exists");

		// atrodu kategoriju pēc id
		if (productCategoryRepo.findById(product.getProductCategory().getProductCategoryId()).isEmpty())
			throw new RuntimeException(
					"Product Category with id: " + product.getProductCategory().getProductCategoryId()
							+ " does not exist");

		// tāds pirkuma elements vēl neeksistē
		Product newProduct = product;
		productRepo.save(newProduct);
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	public void create(Product product, Integer id) {
		Product existedProduct = productRepo.findByTitle(product.getTitle());

		// tāds pirkums jau eksistē
		if (existedProduct != null)
			throw new RuntimeException("Product with name: " + product.getTitle() + " already exists");

		// atrodu kategoriju pēc id
		if (productCategoryRepo.findById(id).isEmpty())
			throw new RuntimeException("Product Category with id: " + id + " does not exist");

		// tāds pirkuma elements vēl neeksistē
		Product newProduct = product;
		newProduct.setProductCategory(productCategoryRepo.findById(id).get());
		productRepo.save(newProduct);
	}

	@Override
	@CacheEvict(value = "Products", allEntries = true)
	@CachePut(value = "Products", key = "#id")
	public void update(Integer id, Product product) {
		// atrodu
		Product productForUpdating = retrieveById(id);

		// izmainu
		productForUpdating.setDescription(product.getDescription());
		productForUpdating.setPrice(product.getPrice());
		productForUpdating.setAmount(product.getAmount());
		productForUpdating.setProductCategory(product.getProductCategory());
		productForUpdating.setTitle(product.getTitle());

		// saglabāju repo un DB
		productRepo.save(productForUpdating);
	}

	@Override
	@CachePut(value = "Products", key = "'c' + #categoryId")
	public ArrayList<Product> selectAllByProductCategory(Integer categoryId) {
		ProductCategory productCategory = productCategoryRepo.findById(categoryId).get();
		ArrayList<Product> products = new ArrayList<>();
		if (productCategory != null) {
			products = (ArrayList<Product>) productRepo.findByProductCategory(productCategory);
			if (products.isEmpty())
				throw new RuntimeException("There are no products");
			return products;
		}

		return products;
	}

	public List<Product> randomProducts() {
		List<Product> randomProducts = productRepo.findRandomProducts(6);

		return randomProducts;
	}

}
