package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.ProductCategory;
import lv.wings.model.Product;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.service.ICRUDService;

@Service
public class ProductCategoryServiceImpl implements ICRUDService<ProductCategory>{
	
	@Autowired
	private IProductCategoryRepo productCategoriesRepo;
	
	@Autowired
	private IProductRepo productRepo;

	@Override
	public ArrayList<ProductCategory> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(productCategoriesRepo.count()==0) throw new Exception("Product category table is empty");
		
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<ProductCategory>) productCategoriesRepo.findAll();
	}

	@Override
	public ProductCategory retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Id should be positive");
		
		if(productCategoriesRepo.existsById(id)) {
			return productCategoriesRepo.findById(id).get();
		}else {
			throw new Exception("Product category with this id ("+ id + ") is not in system");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		if(retrieveById(id)==null) throw new Exception("Product category with (id:" + id + ") does not exist!");
		
		//atrast kategoriju kuru gribam dzēst
		ProductCategory ProductCategoryForDeleting = retrieveById(id);
		
		ArrayList<Product> product = productRepo.findByProductCategory(ProductCategoryForDeleting);
		
		for(int i = 0; i < product.size();i++) {
			product.get(i).setProductCategory(null);
			productRepo.save(product.get(i));
		}
				
		//dzēšam no repo un DB
		productCategoriesRepo.delete(ProductCategoryForDeleting);
	}

	@Override
	public void create(ProductCategory category) throws Exception {
		ProductCategory existingProductCategory = productCategoriesRepo.findByTitleAndDescription(category.getTitle(), category.getDescription());
				
		//tāda category jau eksistē
		if(existingProductCategory != null) throw new Exception("Product category with name: " + category.getTitle() + " already exists in DB!");
				
		//tāds driver vēl neeksistē
		productCategoriesRepo.save(category);
		
	}

	@Override
	public void update(int id, ProductCategory category) throws Exception {
		//atrodu
		ProductCategory productCategoryForUpdating = retrieveById(id);
		
		//izmainu
		productCategoryForUpdating .setTitle(category.getTitle());
		productCategoryForUpdating .setDescription(category.getDescription());
		productCategoryForUpdating .setProducts(category.getProducts());
		
		//saglabāju repo un DB
		productCategoriesRepo.save(productCategoryForUpdating);
		
	}

}
