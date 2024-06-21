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
		if(id < 0) throw new Exception("Id should be positive");
		
		if(preceRepo.existsById(id)) {
			return preceRepo.findById(id).get();
		}else {
			throw new Exception("Prece with this id ("+ id + ") is not in system");
		}
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
