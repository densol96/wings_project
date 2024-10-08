package lv.wings;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Date;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import lv.wings.model.Kategorijas;
import lv.wings.model.Piegades_veids;
import lv.wings.model.Pircejs;
import lv.wings.model.Pirkuma_elements;
import lv.wings.model.Pirkums;
import lv.wings.model.Prece;
import lv.wings.model.Preces_bilde;
import lv.wings.model.Samaksas_veids;
import lv.wings.repo.IKategorijas_Repo;
import lv.wings.repo.IPiegades_veids_Repo;
import lv.wings.repo.IPircejs_Repo;
import lv.wings.repo.IPirkuma_elements_repo;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.repo.IPrece_Repo;
import lv.wings.repo.IPreces_bilde_Repo;
import lv.wings.repo.ISamaksas_veids_Repo;

import lv.wings.model.Atlaide;
import lv.wings.model.PasakumaBilde;
import lv.wings.model.PasakumaKategorija;
import lv.wings.model.PasakumaKomentars;
import lv.wings.model.Pasakums;
import lv.wings.repo.IAtlaideRepo;
import lv.wings.repo.IPasakumaBildeRepo;
import lv.wings.repo.IPasakumaKategorija;
import lv.wings.repo.IPasakumaKomentarsRepo;
import lv.wings.repo.IPasakumsRepo;

@SpringBootApplication
@EnableJpaAuditing
public class SparniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner sparniDB(IPircejs_Repo pircejs_repo, IPirkums_Repo pirkums_repo,
			IPiegades_veids_Repo piegades_veids_repo, ISamaksas_veids_Repo samaksas_veids_repo,
			IPasakumsRepo pasakumsRepo, IPasakumaBildeRepo pasakumaBildeRepo,
			IPasakumaKomentarsRepo pasakumaKomentarsRepo, IPasakumaKategorija pasakumaKategorijaRepo,
			IAtlaideRepo atlaideRepo, IKategorijas_Repo kategorijasRepo, IPirkuma_elements_repo pirkuma_elementsRepo,
			IPrece_Repo preceRepo, IPreces_bilde_Repo bildeRepo) {

		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				Pircejs pircejs1 = new Pircejs("Markuss", "Blumbergs", "random1@gmail.com", "Talsi, Street 1",
						"000000-00001", "Liela banka", "246810", "Bankas kods 123");
				Pircejs pircejs2 = new Pircejs("Lauris", "Kairo", "random2@gmail.com", "Liepaja, Street 4",
						"000000-00002", "Liela banka", "123456", "Bankas kods 321");
				pircejs_repo.save(pircejs1);
				pircejs_repo.save(pircejs2);

				Piegades_veids piegades_veids1 = new Piegades_veids("Piegades Veids1", "Apraksts1");
				Piegades_veids piegades_veids2 = new Piegades_veids("Piegades Veids2", "Apraksts2");
				piegades_veids_repo.save(piegades_veids1);
				piegades_veids_repo.save(piegades_veids2);

				Samaksas_veids samaksas_veids1 = new Samaksas_veids("Samaksas Veids 1", "Piezimes1");
				Samaksas_veids samaksas_veids2 = new Samaksas_veids("Samaksas Veids 2", "Piezimes2");
				samaksas_veids_repo.save(samaksas_veids1);
				samaksas_veids_repo.save(samaksas_veids2);

				Pirkums pirkums1 = new Pirkums(piegades_veids1, samaksas_veids1, pircejs1, LocalDateTime.now(),
						"Nav detalas");
				Pirkums pirkums2 = new Pirkums(piegades_veids2, samaksas_veids2, pircejs2, LocalDateTime.now(),
						"Nav detalas");
				pirkums_repo.save(pirkums1);
				pirkums_repo.save(pirkums2);

				
				Atlaide atlaide1 = new Atlaide(10, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 4)),
						"Melnās piekdienas atlaides!");
				Atlaide atlaide2 = new Atlaide(20, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 5)),
						"Melnās piekdienas atlaides!");
				Atlaide atlaide3 = new Atlaide(50, Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 6)),
						"Melnās piekdienas atlaides!");
				atlaideRepo.save(atlaide1);
				atlaideRepo.save(atlaide2);
				atlaideRepo.save(atlaide3);
				PasakumaKategorija pasakumaKategorija1 = new PasakumaKategorija("Sporta spēles");
				PasakumaKategorija pasakumaKategorija2 = new PasakumaKategorija("Vasaras svētki");
				PasakumaKategorija pasakumaKategorija3 = new PasakumaKategorija("Līgo");

				pasakumaKategorijaRepo.save(pasakumaKategorija1);
				pasakumaKategorijaRepo.save(pasakumaKategorija2);
				pasakumaKategorijaRepo.save(pasakumaKategorija3);
 
				Pasakums pasakums1 = new Pasakums(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 1)), "Pasakums 1",
						"Liepāja", "Pasākums visiem cilvēkiem!", "vasara atpūta", pasakumaKategorija1);
				Pasakums pasakums2 = new Pasakums(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 2)), "Pasakums 2",
						"Ventspils", "Pasākums visiem cilvēkiem!", "vasara atpūta", pasakumaKategorija2);
				Pasakums pasakums3 = new Pasakums(Date.from(Instant.now()), Date.from(Instant.now().plusSeconds(86400 * 3)), "Pasakums 3",
						"Rīga", "Pasākums visiem cilvēkiem!", "vasara beigas", pasakumaKategorija2);
				pasakumsRepo.save(pasakums1);
				pasakumsRepo.save(pasakums2);
				pasakumsRepo.save(pasakums3);

				PasakumaKomentars komentars1 = new PasakumaKomentars("Ļoti labs pasākums!", Date.from(Instant.now()),
						pasakums1);
				PasakumaKomentars komentars2 = new PasakumaKomentars("Slikts pasākums!", Date.from(Instant.now()),
						pasakums1);
				PasakumaKomentars komentars3 = new PasakumaKomentars("Man patika!", Date.from(Instant.now()), pasakums2);
				pasakumaKomentarsRepo.save(komentars1);
				pasakumaKomentarsRepo.save(komentars2);
				pasakumaKomentarsRepo.save(komentars3);

				PasakumaBilde bilde1 = new PasakumaBilde("bilde1.jpg", "bilde1", "laba bilde", pasakums1);
				PasakumaBilde bilde2 = new PasakumaBilde("bilde2.jpg", "bilde2", "slikta bilde", pasakums2);
				PasakumaBilde bilde3 = new PasakumaBilde("bilde3.jpg", "bilde3", "laba bilde", pasakums1);
				pasakumaBildeRepo.save(bilde1);
				pasakumaBildeRepo.save(bilde2);
				pasakumaBildeRepo.save(bilde3);

				// KATEGORIJAS
				Kategorijas kategorija1 = new Kategorijas("Cepure", "Galvas segas");
				Kategorijas kategorija2 = new Kategorijas("Cimdi", "Adīti cimdi");
				Kategorijas kategorija3 = new Kategorijas("Zeķes", "Adītas zeķes");
				kategorijasRepo.save(kategorija1);
				kategorijasRepo.save(kategorija2);
				kategorijasRepo.save(kategorija3);

				// PRECES
				Prece prece1 = new Prece("Adīti ziemas dūraiņi", "Krustūrīenā izšūti adīti ziemas cimdi", 13.60f, 6,
						kategorija2);
				Prece prece2 = new Prece("Tamborētas zeķes pusaudžiem", "Baltas taborētas zeķes izmērs 35", 12.20f, 4,
						kategorija3);
				preceRepo.save(prece1);
				preceRepo.save(prece2);

				// PIRKUMA ELEMENTS
				Pirkuma_elements pirkuma_elements1 = new Pirkuma_elements(pirkums1, prece1, 2);
				Pirkuma_elements pirkuma_elements2 = new Pirkuma_elements(pirkums2, prece2, 1);
				pirkuma_elementsRepo.save(pirkuma_elements1);
				pirkuma_elementsRepo.save(pirkuma_elements2);

				// BILDES
				// public Preces_bilde(String bilde, String apraksts, Prece prece)
				Preces_bilde bilde11 = new Preces_bilde("bilde1.jpg", "Zeķes1", prece1);
				Preces_bilde bilde22 = new Preces_bilde("bilde2.jpg", "Zeķes2", prece1);
				Preces_bilde bilde33 = new Preces_bilde("bilde3.jpg", "Cimdi1", prece2);
				bildeRepo.save(bilde11);
				bildeRepo.save(bilde22);
				bildeRepo.save(bilde33);

			}
		};

	}

}