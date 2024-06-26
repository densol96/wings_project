package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PasakumaBilde;

public interface IPasakumaBildeRepo extends CrudRepository<PasakumaBilde, Integer>{

	PasakumaBilde findByNosaukums(String nosaukums);
	
	
}
