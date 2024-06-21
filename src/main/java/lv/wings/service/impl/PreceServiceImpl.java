package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Prece;
import lv.wings.repo.IPrece_Repo;
import lv.wings.service.IPreceService;


@Service
public class PreceServiceImpl implements IPreceService{

	@Autowired
	private IPrece_Repo preceRepo;

	@Override
	public ArrayList<Prece> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(preceRepo.count()==0) throw new Exception("Datubāze ir tukša");
				
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Prece>) preceRepo.findAll();
	}

	@Override
	public Prece retrieveById(int id) throws Exception {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Prece prece) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Prece prece) throws Exception {
		// TODO Auto-generated method stub
		
	}
	
	
}
