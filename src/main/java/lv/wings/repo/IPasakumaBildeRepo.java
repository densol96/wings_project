package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PasakumaBilde;

public interface IPasakumaBildeRepo extends CrudRepository<PasakumaBilde, Integer>{

	PasakumaBilde findByNosaukums(String nosaukums);
	
	
}
