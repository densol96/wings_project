package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Piegades_veids;

public interface IPiegades_veids_Repo extends CrudRepository<Piegades_veids, Integer>{

    Piegades_veids findByNosaukums(String nosaukums);

}
