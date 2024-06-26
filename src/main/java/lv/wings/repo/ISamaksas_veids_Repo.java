package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Samaksas_veids;

public interface ISamaksas_veids_Repo extends CrudRepository<Samaksas_veids, Integer>{

    Samaksas_veids findByNosaukums(String nosaukums);

}
