package lv.wings.service.impl;

import java.util.ArrayList;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.PasakumaBilde;
import lv.wings.model.Pasakums;
import lv.wings.repo.IPasakumaBildeRepo;
import lv.wings.repo.IPasakumsRepo;
import lv.wings.service.IPasakumiService;


@Service
public class PasakumiServiceImpl implements IPasakumiService{
	
	@Autowired
	IPasakumsRepo pasakumsRepo;
	
	
	@Override
	public Pasakums selectPaskumsById(int id) throws Exception {
		if (id < 1) throw new Exception("Id ir jābūt pozitīvam!");
		Pasakums foundPasakums = pasakumsRepo.findByIdpa(id);
		if (foundPasakums == null) throw new Exception("Jaunums ar id: "+id +" nav atrasts!");
		
		return foundPasakums;
	}
	
	@Override
	public ArrayList<Pasakums> selectAllPasakumi() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAll();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		
		return allPasakumi;
	}

	@Override
	public ArrayList<Pasakums> selectAllPasakumiDescOrder() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAllByOrderByIdpaDesc();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		return allPasakumi;
	}

	@Override
	public ArrayList<Pasakums> selectAllPasakumiAscOrder() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAllByOrderByIdpaAsc();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		return allPasakumi;
	}

	@Override
	public void deletePasakumiById(int id) throws Exception {
		Pasakums pasakums = pasakumsRepo.findByIdpa(id);
		if (pasakums == null) throw new Exception("Pasākums ar id:"+id +" neeksistē!");
		
		pasakumsRepo.delete(pasakums);
	}

	

	/*
	@Override
	public ArrayList<Pasakums> selectAllPasakumiWithImages() throws Exception {
		ArrayList<Pasakums> allPasakumi = pasakumsRepo.findAllPasakumiWithImages();
		
		
		return allPasakumi;
	}
	*/

}
