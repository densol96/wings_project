package lv.wings.repo;

import java.util.Date;

import org.springframework.data.repository.CrudRepository;

import lv.wings.model.Atlaide;

public interface IAtlaideRepo extends CrudRepository<Atlaide, Integer>{

	Atlaide findByAtlaidesApmersAndSakumaDatumsAndApraksts(int atlaidesApmers, Date sakumaDatums,
			String apraksts);

}
