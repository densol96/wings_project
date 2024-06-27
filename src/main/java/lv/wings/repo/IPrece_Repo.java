package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Kategorijas;
import lv.wings.model.Prece;

public interface IPrece_Repo extends CrudRepository<Prece, Integer>{

	Prece findByNosaukums(String nosaukums);
	
	ArrayList<Prece> findByKategorijas(Kategorijas kategorija);

}
