package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PasakumaKomentars;

public interface IPasakumaKomentarsRepo extends CrudRepository<PasakumaKomentars, Integer>{

	PasakumaKomentars findByKomentars(String komentars);

}
