package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Kategorijas;
import lv.wings.model.Prece;
import lv.wings.repo.IKategorijas_Repo;
import lv.wings.repo.IPrece_Repo;
import lv.wings.service.ICRUDService;

@Service
public class KategorijasServiceImpl implements ICRUDService<Kategorijas>{
	
	@Autowired
	private IKategorijas_Repo kategorijasRepo;
	
	@Autowired
	private IPrece_Repo preceRepo;

	@Override
	public ArrayList<Kategorijas> retrieveAll() throws Exception {
		//izmest izņēmumu, ja ir tukša tabula
		if(kategorijasRepo.count()==0) throw new Exception("Datubāze ir tukša");
		
		//pretējā gadījumā sameklēt visus ierakstus no repo
		return (ArrayList<Kategorijas>) kategorijasRepo.findAll();
	}

	@Override
	public Kategorijas retrieveById(int id) throws Exception {
		if(id < 0) throw new Exception("Id should be positive");
		
		if(kategorijasRepo.existsById(id)) {
			return kategorijasRepo.findById(id).get();
		}else {
			throw new Exception("Kategorija with this id ("+ id + ") is not in system");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		if(retrieveById(id)==null) throw new Exception("Id ir ārpus datubāzes robežām!");
		
		//atrast kategoriju kuru gribam dzēst
		Kategorijas kategorijaForDeleting = retrieveById(id);
		
		ArrayList<Prece> preces = preceRepo.findByKategorijas(kategorijaForDeleting);
		
		for(int i = 0; i < preces.size();i++) {
			preces.get(i).setKategorijas(null);
			preceRepo.save(preces.get(i));
		}
				
		//dzēšam no repo un DB
		kategorijasRepo.delete(kategorijaForDeleting);
	}

	@Override
	public void create(Kategorijas kategorija) throws Exception {
		Kategorijas existedKategorija = kategorijasRepo.findByNosaukumsAndApraksts(kategorija.getNosaukums(), kategorija.getApraksts());
				
		//tāda kategorija jau eksistē
		if(existedKategorija != null) throw new Exception("Kategorija with name: " + kategorija.getNosaukums() + " already exists in DB!");
				
		//tāds driver vēl neeksistē
		kategorijasRepo.save(kategorija);
		
	}

	@Override
	public void update(int id, Kategorijas kategorija) throws Exception {
		//atrodu
		Kategorijas kategorijaForUpdating = retrieveById(id);
		
		//izmainu
		kategorijaForUpdating.setNosaukums(kategorija.getNosaukums());
		kategorijaForUpdating.setApraksts(kategorija.getApraksts());
		kategorijaForUpdating.setPreces(kategorija.getPreces());
		
		//saglabāju repo un DB
		kategorijasRepo.save(kategorijaForUpdating);
		
	}

}
