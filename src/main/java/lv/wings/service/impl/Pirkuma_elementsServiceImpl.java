package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Pirkuma_elements;
import lv.wings.repo.IPirkuma_elements_repo;
import lv.wings.service.IPirkuma_elementsService;

@Service
public class Pirkuma_elementsServiceImpl implements IPirkuma_elementsService{

	@Autowired
	private IPirkuma_elements_repo elementsRepo;
	
	@Override
	public ArrayList<Pirkuma_elements> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(elementsRepo.count()==0) throw new Exception("Datubāze ir tukša");
				
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Pirkuma_elements>) elementsRepo.findAll();
	}

	@Override
	public Pirkuma_elements retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Id should be positive");
		
		if(elementsRepo.existsById(id)) {
			return elementsRepo.findById(id).get();
		}else {
			throw new Exception("Pikruma elements with this id ("+ id + ") is not in system");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		//atrast driver kuru gribam dzēst
		Pirkuma_elements elementsForDeleting = retrieveById(id);
				
		//dzēšam no repo un DB
		elementsRepo.delete(elementsForDeleting);
	}

	@Override
	public void create(Pirkuma_elements pirkuma_elements) throws Exception {
		Pirkuma_elements existedPirkuma_elements = elementsRepo.findByPreceNosaukums(pirkuma_elements.getPrece().getNosaukums());
		
		//tāda pirkuma elements jau eksistē
		if(existedPirkuma_elements != null) throw new Exception("Pirkuma elements with name: " + pirkuma_elements.getPrece().getNosaukums() + " already exists in DB!");
				
		//tāds pirkuma elements vēl neeksistē
		elementsRepo.save(pirkuma_elements);
	}

	@Override
	public void update(int id, Pirkuma_elements pirkuma_elements) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
