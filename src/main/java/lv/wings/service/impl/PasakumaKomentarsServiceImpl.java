package lv.wings.service.impl;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.PasakumaKomentars;
import lv.wings.repo.IPasakumaKomentarsRepo;
import lv.wings.repo.IPasakumsRepo;
import lv.wings.service.IPasakumaKomentarsService;

@Service
public class PasakumaKomentarsServiceImpl implements IPasakumaKomentarsService {
	@Autowired
	private IPasakumaKomentarsRepo pasakumaKomentarsRepo;

	@Autowired
	private IPasakumsRepo pasakumsRepo;

	@Override
	public ArrayList<PasakumaKomentars> retrieveAll() throws Exception {
		if (pasakumaKomentarsRepo.count() == 0)
			throw new Exception("Datubāze ir tukša!");

		return (ArrayList<PasakumaKomentars>) pasakumaKomentarsRepo.findAll();
	}

	@Override
	public PasakumaKomentars retrieveById(int id) throws Exception {
		if (id < 1)
			throw new Exception("Id jābūt pozitīvam!");

		if (pasakumaKomentarsRepo.existsById(id)) {
			return pasakumaKomentarsRepo.findById(id).get();
		} else {
			throw new Exception("PasakumaKomentars ar šo (" + id + ") neeksistē!");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		PasakumaKomentars pasakumaKomentars = retrieveById(id);
		if (pasakumaKomentars == null)
			throw new Exception("Id neekstistē!");

		pasakumaKomentarsRepo.delete(pasakumaKomentars);

	}

	@Override
	public void create(PasakumaKomentars pasakumaKomentars) throws Exception {
		pasakumaKomentars.setDatums(Date.from(Instant.now()));
		pasakumaKomentarsRepo.save(pasakumaKomentars);
	}

	@Override
	public void update(int id, PasakumaKomentars pasakumaKomentars) throws Exception {
		PasakumaKomentars foundPasakumaKomentars = retrieveById(id);
		if (pasakumaKomentars == null)
			throw new Exception("Komentārs neekstistē!");

		foundPasakumaKomentars.setKomentars(pasakumaKomentars.getKomentars());

		pasakumaKomentarsRepo.save(foundPasakumaKomentars);
	}

}
