package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.Product;
import lv.wings.model.ProductPicture;
import lv.wings.model.PurchaseElement;
import lv.wings.repo.IProductCategoryRepo;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.service.ICRUDInsertedService;


@Service
public class ProductServiceImpl implements ICRUDInsertedService<Product>{

	@Autowired
	private IProductRepo productRepo;
	
	@Autowired
	private IProductCategoryRepo productCategoryRepo;
	
	@Autowired
	private IPurchaseElementRepo elementRepo;
	
	@Autowired
	private IProductPictureRepo pictureRepo;

	@Override
	public ArrayList<Product> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(productRepo.count()==0) throw new Exception("There are no products");
				
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Product>) productRepo.findAll();
	}

	@Override
	public Page<Product> retrieveAll(Pageable pageable) throws Exception{
		if(productRepo.count()==0) throw new Exception("There are no products");
		return (Page<Product>) productRepo.findAll(pageable);
	}

	@Override
	public Product retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Id should be positive");
		
		if(productRepo.existsById(id)) {
			return productRepo.findById(id).get();
		}else {
			throw new Exception("Product with this id ("+ id + ") does not exist");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		//atrast preci kuru gribam dzēst
		Product productForDeleting = retrieveById(id);
		
		//Ja tiek dzēsts pirkuma elements, tad pirkuma elementā prece tiek setota kā null
		ArrayList<PurchaseElement> purchaseElement = elementRepo.findByProduct(productForDeleting);
		
		for(int i = 0; i < purchaseElement.size(); i++) {
			purchaseElement.get(i).setProduct(null);
			elementRepo.save(purchaseElement.get(i));
		}
		
		//Ja tiek dzēsta preces bilde, tad preces bildē prece tiek setota kā null
		ArrayList<ProductPicture> productPictures = pictureRepo.findByProduct(productForDeleting);
				
		for(int i = 0; i < productPictures.size(); i++) {
			productPictures.get(i).setProduct(null);
			pictureRepo.save(productPictures.get(i));
		}
		
		//dzēšam no repo un DB
		productRepo.delete(productForDeleting);
	}

	@Override
	public void create(Product product) throws Exception {
		Product existedProduct = productRepo.findByTitle(product.getTitle());
		
		//tāds pirkums jau eksistē
		if(existedProduct != null) throw new Exception("Product with name: " + product.getTitle() + " already exists");
				
		//atrodu kategoriju pēc id
		if(productCategoryRepo.findById(product.getProductCategory().getProductCategoryId()).isEmpty()) 
			throw new Exception("Product Category with id: " + product.getProductCategory().getProductCategoryId() + " does not exist");
		
		//tāds pirkuma elements vēl neeksistē
		Product newProduct = product;
		productRepo.save(newProduct);
	}

	@Override
	public void create(Product product, int id) throws Exception {
		Product existedProduct = productRepo.findByTitle(product.getTitle());
		
		//tāds pirkums jau eksistē
		if(existedProduct != null) throw new Exception("Product with name: " + product.getTitle() + " already exists");
				
		//atrodu kategoriju pēc id
		if(productCategoryRepo.findById(id).isEmpty())
			throw new Exception("Product Category with id: " + id + " does not exist");
		
		//tāds pirkuma elements vēl neeksistē
		Product newProduct = product;
		newProduct.setProductCategory(productCategoryRepo.findById(id).get());
		productRepo.save(newProduct);
	}

	@Override
	public void update(int id, Product product) throws Exception {
		//atrodu
		Product productForUpdating = retrieveById(id);
		
		//izmainu
		productForUpdating.setDescription(product.getDescription());
		productForUpdating.setPrice(product.getPrice());
		productForUpdating.setAmount(product.getAmount());
		//preceForUpdating.setKategorijas(prece.getKategorijas());
		productForUpdating.setTitle(product.getTitle());
		
		//saglabāju repo un DB
		productRepo.save(productForUpdating);
	}
	
	
}
