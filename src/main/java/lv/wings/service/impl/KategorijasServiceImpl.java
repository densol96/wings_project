package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Kategorijas;
import lv.wings.repo.IKategorijas_Repo;
import lv.wings.service.IKategorijasService;

@Service
public class KategorijasServiceImpl implements IKategorijasService{
	
	@Autowired
	private IKategorijas_Repo kategorijasRepo;

	@Override
	public ArrayList<Kategorijas> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(kategorijasRepo.count()==0) throw new Exception("Datubāze ir tukša");
		
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Kategorijas>) kategorijasRepo.findAll();
	}

	@Override
	public Kategorijas retrieveById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Kategorijas kategorija) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Kategorijas kategorija) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
