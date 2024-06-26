package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Piegades_veids;
import lv.wings.model.Pircejs;
import lv.wings.model.Pirkums;
import lv.wings.model.Samaksas_veids;

public interface IPirkums_Repo extends CrudRepository<Pirkums, Integer>{

    ArrayList<Pirkums> findBySamaksasVeids(Samaksas_veids sv);

    ArrayList<Pirkums> findByPiegadesVeids(Piegades_veids pv);

    ArrayList<Pirkums> findByPircejs(Pircejs pircejs);


}
