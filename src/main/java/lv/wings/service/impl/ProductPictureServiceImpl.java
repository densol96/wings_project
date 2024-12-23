package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.ProductPicture;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.repo.IProductRepo;
import lv.wings.service.ICRUDInsertedService;

@Service
public class ProductPictureServiceImpl implements ICRUDInsertedService<ProductPicture>{

	@Autowired
	private IProductPictureRepo productPictureRepo;
	
	@Autowired
	private IProductRepo productRepo;
	
	@Override
	public ArrayList<ProductPicture> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(productPictureRepo.count()==0) throw new Exception("There are no product pictures");
				
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<ProductPicture>) productPictureRepo.findAll();
	}

	@Override
	public Page<ProductPicture> retrieveAll(Pageable pageable) throws Exception {
		if(productPictureRepo.count()==0) throw new Exception("There are no product pictures");
		return (Page<ProductPicture>)  productPictureRepo.findAll(pageable);
	}

	@Override
	public ProductPicture retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Invalid ID");
		
		if(productPictureRepo.existsById(id)) {
			return productPictureRepo.findById(id).get();
		}else {
			throw new Exception("Product picture with id ("+ id + ") does not exist");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		//atrast preces bilde kuru gribam dzēst
		ProductPicture driverForDeleting = retrieveById(id);
				
		//dzēšam no repo un DB
		productPictureRepo.delete(driverForDeleting);
	}

	@Override
	public void create(ProductPicture productPicture) throws Exception {
		ProductPicture existedProductPicture = productPictureRepo.findByPicture(productPicture.getPicture());
		
		//tāda bilde jau eksistē
		if(existedProductPicture != null) throw new Exception("Picture with name: " + productPicture.getPicture() + " already exists");
		
		//atrodu preci pēc id
		if(productRepo.findById(productPicture.getProduct().getProductId()).isEmpty())
			throw new Exception("Prece ar sekojošu id: " + productPicture.getProduct().getProductId() + " neeksistē!");
				
		productPictureRepo.save(productPicture);
	}

	@Override
	public void create(ProductPicture productPicture, int id) throws Exception {
		ProductPicture existedProductPicture = productPictureRepo.findByPicture(productPicture.getPicture());
		
		//tāda bilde jau eksistē
		if(existedProductPicture != null) throw new Exception("Picture with name: " + productPicture.getPicture() + " already exists");
		
		//atrodu preci pēc id
		if(productRepo.findById(id).isEmpty())
			throw new Exception("Product with id: " + id + " does not exist!");
				
		//tāda bilde vēl neeksistē
		ProductPicture newPicture = productPicture;
		newPicture.setProduct(productRepo.findById(id).get());
		productPictureRepo.save(newPicture);
	}

	@Override
	public void update(int id, ProductPicture productPicture) throws Exception {
		//atrodu
		ProductPicture productPictureForUpdating = retrieveById(id);
		
		//izmainu
		productPictureForUpdating.setDescription(productPicture.getDescription());
		productPictureForUpdating.setPicture(productPicture.getPicture());
		
		//saglabāju repo un DB
		productPictureRepo.save(productPictureForUpdating);
		
	}

}
