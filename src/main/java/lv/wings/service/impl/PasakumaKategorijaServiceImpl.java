package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.PasakumaKategorija;
import lv.wings.repo.IPasakumaKategorija;
import lv.wings.service.IPasakumaKategorijaService;

@Service
public class PasakumaKategorijaServiceImpl implements IPasakumaKategorijaService {
	@Autowired
	IPasakumaKategorija pasakumaKategorijaRepo;

	@Override
	public ArrayList<PasakumaKategorija> retrieveAll() throws Exception {
		if (pasakumaKategorijaRepo.count() == 0)
			throw new Exception("Datubāze ir tukša!");

		return (ArrayList<PasakumaKategorija>) pasakumaKategorijaRepo.findAll();
	}

	@Override
	public PasakumaKategorija retrieveById(int id) throws Exception {
		if (id < 1)
			throw new Exception("Id jābūt pozitīvam!");

		if (pasakumaKategorijaRepo.existsById(id)) {
			return pasakumaKategorijaRepo.findById(id).get();
		} else {
			throw new Exception("Pasākuma kategorija ar šo id: (" + id + ") neeksistē!");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		PasakumaKategorija pasakumaKategorija = retrieveById(id);
		
		if (pasakumaKategorija == null) throw new Exception("Id neekstistē!");

		pasakumaKategorijaRepo.delete(pasakumaKategorija);

	}

	@Override
	public void create(PasakumaKategorija pasakumaKategorija) throws Exception {
		PasakumaKategorija existedPaskumaKategorija = pasakumaKategorijaRepo
				.findByNosaukums(pasakumaKategorija.getNosaukums());

		if (existedPaskumaKategorija != null)
			throw new Exception(
					"Pasākuma kategorija ar nosaukumu: " + pasakumaKategorija.getNosaukums() + " atrodas DB!");

		pasakumaKategorijaRepo.save(pasakumaKategorija);

	}

	@Override
	public void update(int id, PasakumaKategorija pasakumaKategorija) throws Exception {
		PasakumaKategorija foundPasakumaKategorija = retrieveById(id);
		if (foundPasakumaKategorija == null) throw new Exception("Pasākuma kategorija neeksistē!");

		foundPasakumaKategorija.setNosaukums(pasakumaKategorija.getNosaukums());

		pasakumaKategorijaRepo.save(foundPasakumaKategorija);

	}

}
