package lv.wings.repo;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Pircejs;

public interface IPircejs_Repo extends CrudRepository<Pircejs, Integer>{

    Pircejs findByBankas_SWIFT_kodsAndBankas_kods(String bankas_SWIFT_kods, String bankas_kods);

}
