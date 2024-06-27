package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PasakumaKategorija;
import lv.wings.model.Pasakums;

public interface IPasakumsRepo extends CrudRepository<Pasakums, Integer> {

	Pasakums findByIdpa(int id);

	ArrayList<Pasakums> findAllByOrderByIdpaDesc();

	ArrayList<Pasakums> findAllByOrderByIdpaAsc();

	Pasakums findByNosaukums(String nosaukums);

	ArrayList<Pasakums> findByPasakumaKategorija(PasakumaKategorija pasakumaKategorija);

}
