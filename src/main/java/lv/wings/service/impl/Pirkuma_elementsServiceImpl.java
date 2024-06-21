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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Pirkuma_elements pirkuma_elements) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Pirkuma_elements pirkuma_elements) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
