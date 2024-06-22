package lv.wings.repo;

import java.util.ArrayList;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import lv.wings.model.PasakumaBilde;

public interface IPasakumaBildeRepo extends CrudRepository<PasakumaBilde, Integer>{
	
	@Query(nativeQuery = true, value = "select pasakumi.idpa, pasakumi.idpaka, pasakumi.idpako, pasakumi.nosaukums, pasakumi.apraksts, pasakumi.vieta, pasakumi.sakumadatums, pasakumi.beigudatums, pasakuma_bildes.idpab, pasakuma_bildes.atsauce_uz_bildi, pasakumi.key_words from pasakumi left join pasakuma_bildes on pasakumi.idpa = pasakuma_bildes.idpa;")
	ArrayList<PasakumaBilde> findAllPasakumiWithImages();
}
