package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Prece;
import lv.wings.model.Preces_bilde;

public interface IPreces_bilde_Repo extends CrudRepository<Preces_bilde,Integer> {

	Preces_bilde findByBilde(String bilde);
	
	ArrayList<Preces_bilde> findByPrece(Prece prece);

}
