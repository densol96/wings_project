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

import lv.wings.model.ProductPicture;
import lv.wings.repo.IProductPictureRepo;
import lv.wings.service.ICRUDService;

@Service
public class ProductPictureServiceImpl implements ICRUDService<ProductPicture> {

	@Autowired
	private IProductPictureRepo productPictureRepo;

	@Override
	@Cacheable("ProductPictures")
	public List<ProductPicture> retrieveAll() {
		// izmest izņēmumu, ja ir tukša tabula
		if (productPictureRepo.count() == 0)
			throw new RuntimeException("There are no product pictures");

		// pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<ProductPicture>) productPictureRepo.findAll();
	}

	@Override
	@Cacheable(value = "ProductPictures", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<ProductPicture> retrieveAll(Integer page, Integer size, String sortBy, String sortDirection) {
		if (productPictureRepo.count() == 0)
			throw new RuntimeException("There are no product pictures");
		Pageable pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.fromString(sortDirection), sortBy));
		return (Page<ProductPicture>) productPictureRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "ProductPictures", key = "#id")
	public ProductPicture retrieveById(Integer id) {
		if (id < 1)
			throw new RuntimeException("Invalid ID");

		if (productPictureRepo.existsById(id)) {
			return productPictureRepo.findById(id).get();
		} else {
			throw new RuntimeException("Product picture with id (" + id + ") does not exist");
		}
	}

	@Override
	@CacheEvict(value = "ProductPictures", allEntries = true)
	public void deleteById(Integer id) {
		// atrast preces bilde kuru gribam dzēst
		ProductPicture productPicture = retrieveById(id);
		if (productPicture == null)
			throw new RuntimeException("Product picture with the id: (" + id + ") does not exist!");

		// dzēšam no repo un DB
		productPictureRepo.delete(productPicture);
	}

	@Override
	@CacheEvict(value = "ProductPictures", allEntries = true)
	public void create(ProductPicture productPicture) {
		ProductPicture existedProductPicture = productPictureRepo
				.findByReferenceToPicture(productPicture.getReferenceToPicture());

		// tāda bilde jau eksistē
		if (existedProductPicture != null)
			throw new RuntimeException("Picture with name already exists!");

		// atrodu preci pēc id
		// if(productRepo.findById(productPicture.getProduct().getProductId()).isEmpty())
		// throw new RuntimeException("Prece ar sekojošu id: " +
		// productPicture.getProduct().getProductId() + " neeksistē!");

		productPictureRepo.save(productPicture);
	}

	@Override
	@CacheEvict(value = "ProductPictures", allEntries = true)
	@CachePut(value = "ProductPictures", key = "#id")
	public void update(Integer id, ProductPicture productPicture) {
		// atrodu
		ProductPicture productPictureForUpdating = retrieveById(id);

		productPictureForUpdating.setReferenceToPicture(productPicture.getReferenceToPicture());
		productPictureForUpdating.setDescription(productPicture.getDescription());

		// saglabāju repo un DB
		productPictureRepo.save(productPictureForUpdating);

	}

}
