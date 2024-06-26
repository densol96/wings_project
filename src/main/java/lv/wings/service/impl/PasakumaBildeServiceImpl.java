package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Atlaide;
import lv.wings.model.PasakumaBilde;
import lv.wings.repo.IPasakumaBildeRepo;
import lv.wings.service.IPasakumaBildeService;

@Service
public class PasakumaBildeServiceImpl implements IPasakumaBildeService {
	@Autowired
	private IPasakumaBildeRepo pasakumaBildeRepo;

	@Override
	public ArrayList<PasakumaBilde> retrieveAll() throws Exception {
		if (pasakumaBildeRepo.count() == 0)
			throw new Exception("Datubāze ir tukša!");

		return (ArrayList<PasakumaBilde>) pasakumaBildeRepo.findAll();
	}

	@Override
	public PasakumaBilde retrieveById(int id) throws Exception {
		if (id < 1)
			throw new Exception("Id jābūt pozitīvam!");

		if (pasakumaBildeRepo.existsById(id)) {
			return pasakumaBildeRepo.findById(id).get();
		} else {
			throw new Exception("Pasākuma bilde ar šo id: (" + id + ") neeksistē!");
		}
	}

	@Override
	public void deleteById(int id) throws Exception {
		PasakumaBilde pasakumaBilde = retrieveById(id);
		if (pasakumaBilde == null) throw new Exception("Id neekstistē!");
		pasakumaBildeRepo.delete(pasakumaBilde);

	}

	@Override
	public void create(PasakumaBilde pasakumaBilde) throws Exception {
		PasakumaBilde existedPasakumaBilde = pasakumaBildeRepo.findByNosaukums(pasakumaBilde.getNosaukums());

		if (existedPasakumaBilde != null)
			throw new Exception("Pasākuma bilde ar nosaukumu: " + pasakumaBilde.getNosaukums() + " atrodas DB!");

		pasakumaBildeRepo.save(pasakumaBilde);

	}

	@Override
	public void update(int id, PasakumaBilde pasakumaBilde) throws Exception {
		PasakumaBilde foundPasakumaBilde = retrieveById(id);
		
		if (foundPasakumaBilde == null) throw new Exception("Id neekstistē!");

		foundPasakumaBilde.setAtsauceUzBildi(pasakumaBilde.getAtsauceUzBildi());
		foundPasakumaBilde.setNosaukums(pasakumaBilde.getNosaukums());
		foundPasakumaBilde.setApraksts(pasakumaBilde.getApraksts());

		pasakumaBildeRepo.save(foundPasakumaBilde);

	}

}
