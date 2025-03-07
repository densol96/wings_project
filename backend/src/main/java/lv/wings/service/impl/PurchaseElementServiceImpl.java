package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import lv.wings.model.PurchaseElement;
import lv.wings.repo.IProductRepo;
import lv.wings.repo.IPurchaseElementRepo;
import lv.wings.service.ICRUDInsertedService;

@Service
public class PurchaseElementServiceImpl implements ICRUDInsertedService<PurchaseElement> {

	@Autowired
	private IPurchaseElementRepo elementRepo;

	@Autowired
	private IProductRepo productRepo;

	@Override
	@Cacheable("PurchaseElements")
	public ArrayList<PurchaseElement> retrieveAll() throws Exception {
		// izmest izņēmumu, ja ir tukša tabula
		if (elementRepo.count() == 0)
			throw new Exception("There are no Purchase Elements");

		// pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<PurchaseElement>) elementRepo.findAll();
	}

	@Override
	@Cacheable(value = "PurchaseElements", key = "#pageable.pageNumber + '-' + #pageable.pageSize + '-' + #pageable.sort")
	public Page<PurchaseElement> retrieveAll(Pageable pageable) throws Exception {

		if (elementRepo.count() == 0)
			throw new Exception("There are no Purchase Elements");
		return (Page<PurchaseElement>) elementRepo.findAll(pageable);
	}

	@Override
	@Cacheable(value = "PurchaseElements", key = "#id")
	public PurchaseElement retrieveById(Integer id) throws Exception {
		if (id < 0)
			throw new Exception("Invalid ID");

		if (elementRepo.existsById(id)) {
			return elementRepo.findById(id).get();
		} else {
			throw new Exception("Purchase element with id: (" + id + ") does not exist");
		}
	}

	@Override
	@CacheEvict(value = "PurchaseElements", allEntries = true)
	public void deleteById(Integer id) throws Exception {
		// atrast driver kuru gribam dzēst
		PurchaseElement elementForDeleting = retrieveById(id);

		// dzēšam no repo un DB
		elementRepo.delete(elementForDeleting);
	}

	@Override
	@CacheEvict(value = "PurchaseElements", allEntries = true)
	public void create(PurchaseElement element) throws Exception {
		// atrodu preci pēc id
		if (productRepo.findById(element.getProduct().getProductId()) == null)
			throw new Exception("Product with id: " + element.getProduct().getProductId() + " does not exist");
		// izveidoju noklusējuma pirkuma elementu
		PurchaseElement newElement = element;
		elementRepo.save(newElement);
	}

	@Override
	@CacheEvict(value = "PurchaseElements", allEntries = true)
	public void create(PurchaseElement element, Integer id) throws Exception {
		// atrodu preci pēc id
		if (productRepo.findById(id) == null)
			throw new Exception("Product with id: " + id + " does not exist");

		// izveidoju noklusējuma pirkuma elementu
		PurchaseElement newElement = element;
		newElement.setProduct(productRepo.findById(id).get());
		elementRepo.save(newElement);
	}

	@Override
	@CacheEvict(value = "PurchaseElements", allEntries = true)
	@CachePut(value = "PurchaseElements", key = "#id")
	public void update(Integer id, PurchaseElement element) throws Exception {
		// atrodu
		PurchaseElement elementForUpdating = retrieveById(id);

		// izmainu
		// elementsForUpdating.setPrece(pirkuma_elements.getPrece());
		elementForUpdating.setAmount(element.getAmount());

		// saglabāju repo un DB
		elementRepo.save(elementForUpdating);

	}

}
