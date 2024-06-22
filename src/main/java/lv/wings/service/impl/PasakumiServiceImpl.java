package lv.wings.service.impl;

import java.util.ArrayList;

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
	public ArrayList<Pasakums> selectAllPasakumi() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAllPasakumiWithImages();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		
		return allPasakumi;
	}

	@Override
	public ArrayList<Pasakums> selectAllPasakumiDescOrder() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAllPasakumiWithImagesDescOrder();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		return allPasakumi;
	}

	@Override
	public ArrayList<Pasakums> selectAllPasakumiAscOrder() throws Exception {
		ArrayList<Pasakums> allPasakumi = (ArrayList<Pasakums>) pasakumsRepo.findAllPasakumiWithImagesAscOrder();
		if (allPasakumi.isEmpty()) throw new Exception("Datubāzē nav ievadīts neviens pasākums!");
		return allPasakumi;
	}

	/*
	@Override
	public ArrayList<Pasakums> selectAllPasakumiWithImages() throws Exception {
		ArrayList<Pasakums> allPasakumi = pasakumsRepo.findAllPasakumiWithImages();
		
		
		return allPasakumi;
	}
	*/

}
