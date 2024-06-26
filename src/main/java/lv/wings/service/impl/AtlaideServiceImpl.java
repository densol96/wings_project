package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Atlaide;
import lv.wings.model.Kategorijas;
import lv.wings.repo.IAtlaideRepo;
import lv.wings.service.IAtlaideService;

@Service
public class AtlaideServiceImpl implements IAtlaideService{
	@Autowired
	private IAtlaideRepo atlaideRepo;

	@Override
	public ArrayList<Atlaide> retrieveAll() throws Exception {
		if(atlaideRepo.count()==0) throw new Exception("Datubāze ir tukša!");
		
		return (ArrayList<Atlaide>) atlaideRepo.findAll();
	}

	@Override
	public Atlaide retrieveById(int id) throws Exception {
		if(id < 1) throw new Exception("Id jābūt pozitīvam!");
		
		
		if(atlaideRepo.existsById(id)) {
			return atlaideRepo.findById(id).get();
		}else {
			throw new Exception("Atlaide ar šo ("+ id + ") neeksistē!");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
	  Atlaide atlaide = retrieveById(id);
	  
	  atlaideRepo.delete(atlaide);
		
	}

	@Override
	public void create(Atlaide atlaide) throws Exception {
		Atlaide existedAtlaide = atlaideRepo.findByAtlaidesApmersAndSakumaDatumsAndApraksts(atlaide.getAtlaidesApmers(), atlaide.getSakumaDatums(), atlaide.getApraksts());
		
		if(existedAtlaide != null) throw new Exception("Atlaide ar apmēru: " + atlaide.getAtlaidesApmers() + " atrodas DB!");
		
		atlaideRepo.save(atlaide);
	}

	@Override
	public void update(int id, Atlaide atlaide) throws Exception {
		Atlaide foundAtlaide = retrieveById(id);
		
		foundAtlaide.setApraksts(atlaide.getApraksts());
		foundAtlaide.setAtlaidesApmers(atlaide.getAtlaidesApmers());
		foundAtlaide.setSakumaDatums(atlaide.getSakumaDatums());
		foundAtlaide.setBeiguDatums(atlaide.getBeiguDatums());
		
		atlaideRepo.save(foundAtlaide);
		
	}

}
