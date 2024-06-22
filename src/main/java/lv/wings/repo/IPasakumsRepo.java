package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Pasakums;

public interface IPasakumsRepo extends CrudRepository<Pasakums, Integer> {
	
	Pasakums findByIdpa(int id);

    ArrayList<Pasakums> findAllByOrderByIdpaDesc();
	
	ArrayList<Pasakums> findAllByOrderByIdpaAsc();
	//@Query(nativeQuery = true, value = "select pasakumi.idpa, pasakumi.idpaka, pasakumi.idpako, pasakumi.nosaukums, pasakumi.apraksts, pasakumi.vieta, pasakumi.sakumadatums, pasakumi.beigudatums, pasakuma_bildes.idpab, pasakumi.key_words, pasakuma_bildes.atsauce_uz_bildi from pasakumi left join pasakuma_bildes on pasakumi.idpa = pasakuma_bildes.idpa order by pasakumi.idpa asc;")

	
	
	

}
