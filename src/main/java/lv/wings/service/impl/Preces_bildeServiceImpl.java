package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Preces_bilde;
import lv.wings.repo.IPreces_bilde_Repo;
import lv.wings.service.IPreces_bildeService;

@Service
public class Preces_bildeServiceImpl implements IPreces_bildeService{

	@Autowired
	private IPreces_bilde_Repo bildeRepo;
	
	@Override
	public ArrayList<Preces_bilde> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(bildeRepo.count()==0) throw new Exception("Datubāze ir tukša");
				
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Preces_bilde>) bildeRepo.findAll();
	}

	@Override
	public Preces_bilde retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Id should be positive");
		
		if(bildeRepo.existsById(id)) {
			return bildeRepo.findById(id).get();
		}else {
			throw new Exception("Preces bilde with this id ("+ id + ") is not in system");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void create(Preces_bilde bilde) throws Exception {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(int id, Preces_bilde bilde) throws Exception {
		// TODO Auto-generated method stub
		
	}

}
