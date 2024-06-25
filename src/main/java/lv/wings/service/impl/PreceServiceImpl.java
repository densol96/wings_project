package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Prece;
import lv.wings.repo.IKategorijas_Repo;
import lv.wings.repo.IPrece_Repo;
import lv.wings.service.IPreceService;


@Service
public class PreceServiceImpl implements IPreceService{

	@Autowired
	private IPrece_Repo preceRepo;
	
	@Autowired
	private IKategorijas_Repo kategorijaRepo;

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
		//atrast preci kuru gribam dzēst
		Prece preceForDeleting = retrieveById(id);
				
		//dzēšam no repo un DB
		preceRepo.delete(preceForDeleting);
	}

	@Override
	public void create(Prece prece, int kategorijasID) throws Exception {
		Prece existedPrece = preceRepo.findByNosaukums(prece.getNosaukums());
		
		//tāds pirkums jau eksistē
		if(existedPrece != null) throw new Exception("Pirkums with name: " + prece.getNosaukums() + " already exists in DB!");
				
		//atrodu kategoriju pēc id
		if(kategorijaRepo.findById(kategorijasID)==null) throw new 
		Exception("Kategorija ar sekojošu id: " + kategorijasID + " neeksistē!");
		
		//tāds pirkuma elements vēl neeksistē
		Prece newPrece = prece;
		newPrece.setKategorijas(kategorijaRepo.findById(kategorijasID).get());
		preceRepo.save(newPrece);
	}

	@Override
	public void update(int id, Prece prece) throws Exception {
		//atrodu
		Prece preceForUpdating = retrieveById(id);
		
		//izmainu
		preceForUpdating.setApraksts(prece.getApraksts());
		preceForUpdating.setCena(prece.getCena());
		preceForUpdating.setDaudzums(prece.getDaudzums());
		preceForUpdating.setKategorijas(prece.getKategorijas());
		preceForUpdating.setNosaukums(prece.getNosaukums());
		
		//saglabāju repo un DB
		preceRepo.save(preceForUpdating);
	}
	
	
}
