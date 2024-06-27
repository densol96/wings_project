package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Pirkuma_elements;
import lv.wings.model.Pirkums;
import lv.wings.model.Prece;

public interface IPirkuma_elements_repo extends CrudRepository<Pirkuma_elements,Integer>{

    ArrayList<Pirkuma_elements> findByPirkums(Pirkums pirkums);

    ArrayList<Pirkuma_elements> findByPrece (Prece prece);
	

}
