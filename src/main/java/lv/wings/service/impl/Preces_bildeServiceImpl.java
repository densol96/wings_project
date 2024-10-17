package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Preces_bilde;
import lv.wings.repo.IPrece_Repo;
import lv.wings.repo.IPreces_bilde_Repo;
import lv.wings.service.ICRUDInsertedService;

@Service
public class Preces_bildeServiceImpl implements ICRUDInsertedService<Preces_bilde>{

	@Autowired
	private IPreces_bilde_Repo bildeRepo;
	
	@Autowired
	private IPrece_Repo preceRepo;
	
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
		//atrast preces bilde kuru gribam dzēst
		Preces_bilde driverForDeleting = retrieveById(id);
				
		//dzēšam no repo un DB
		bildeRepo.delete(driverForDeleting);
	}

	@Override
	public void create(Preces_bilde bilde) throws Exception {
		Preces_bilde existedPreces_bilde = bildeRepo.findByBilde(bilde.getBilde());
		
		//tāda bilde jau eksistē
		if(existedPreces_bilde != null) throw new Exception("Bilde with name: " + bilde.getBilde() + " already exists in DB!");
		
		//atrodu preci pēc id
		if(preceRepo.findById(bilde.getPrece().getPrece_id())==null) throw new 
		Exception("Prece ar sekojošu id: " + bilde.getPrece().getPrece_id() + " neeksistē!");
				
		//tāda bilde vēl neeksistē
		Preces_bilde newBilde = bilde;
		bildeRepo.save(newBilde);
	}

	@Override
	public void create(Preces_bilde bilde, int preceID) throws Exception {
		Preces_bilde existedPreces_bilde = bildeRepo.findByBilde(bilde.getBilde());
		
		//tāda bilde jau eksistē
		if(existedPreces_bilde != null) throw new Exception("Bilde with name: " + bilde.getBilde() + " already exists in DB!");
		
		//atrodu preci pēc id
		if(preceRepo.findById(preceID)==null) throw new 
		Exception("Prece ar sekojošu id: " + preceID + " neeksistē!");
				
		//tāda bilde vēl neeksistē
		Preces_bilde newBilde = bilde;
		newBilde.setPrece(preceRepo.findById(preceID).get());
		bildeRepo.save(newBilde);
	}

	@Override
	public void update(int id, Preces_bilde bilde) throws Exception {
		//atrodu
		Preces_bilde bildeForUpdating = retrieveById(id);
		
		//izmainu
		bildeForUpdating.setApraksts(bilde.getApraksts());
		bildeForUpdating.setBilde(bilde.getBilde());
		
		//saglabāju repo un DB
		bildeRepo.save(bildeForUpdating);
		
	}

}
