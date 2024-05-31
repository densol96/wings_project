package lv.wings;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;


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
public class SparniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
	}

	@Bean
	static CommandLineRunner testDB(IPasakumsRepo pasakumsRepo, IPasakumaBildeRepo pasakumaBildeRepo,
			IPasakumaKomentarsRepo pasakumaKomentarsRepo, IPasakumaKategorija pasakumaKategorijaRepo,
			IAtlaideRepo atlaideRepo) {

		return new CommandLineRunner() {

			@Override
			public void run(String... args) throws Exception {
				Atlaide atlaide1 = new Atlaide(10, LocalDateTime.now(), LocalDateTime.now().plusDays(4), "Melnās piekdienas atlaides!");
				Atlaide atlaide2 = new Atlaide(20, LocalDateTime.now(), LocalDateTime.now().plusDays(5), "Melnās piekdienas atlaides!");
				Atlaide atlaide3 = new Atlaide(50, LocalDateTime.now(), LocalDateTime.now().plusDays(6), "Melnās piekdienas atlaides!");
				atlaideRepo.save(atlaide1);
				atlaideRepo.save(atlaide2);
				atlaideRepo.save(atlaide3);
				PasakumaKategorija pasakumaKategorija1 = new PasakumaKategorija("Sporta spēles");
				PasakumaKategorija pasakumaKategorija2 = new PasakumaKategorija("Vasaras svētki");
				PasakumaKategorija pasakumaKategorija3 = new PasakumaKategorija("Līgo");
				
				pasakumaKategorijaRepo.save(pasakumaKategorija1);
				pasakumaKategorijaRepo.save(pasakumaKategorija2);
				pasakumaKategorijaRepo.save(pasakumaKategorija3);
				
				PasakumaKomentars komentars1 = new PasakumaKomentars("Ļoti labs pasākums!", LocalDateTime.now());
				PasakumaKomentars komentars2 = new PasakumaKomentars("Slikts pasākums!", LocalDateTime.now());
				PasakumaKomentars komentars3 = new PasakumaKomentars("Man patika!", LocalDateTime.now());
				pasakumaKomentarsRepo.save(komentars1);
				pasakumaKomentarsRepo.save(komentars2);
				pasakumaKomentarsRepo.save(komentars3);
				
				Pasakums pasakums1 = new Pasakums(LocalDateTime.now(), LocalDateTime.now().plusDays(20), "Pasakums 1", "Liepāja", "Pasākums visiem cilvēkiem!", "vasara atpūta", komentars1, pasakumaKategorija1);
				Pasakums pasakums2 = new Pasakums(LocalDateTime.now(), LocalDateTime.now().plusDays(45), "Pasakums 2", "Ventspils", "Pasākums visiem cilvēkiem!", "vasara atpūta",  komentars2, pasakumaKategorija2);
				Pasakums pasakums3 = new Pasakums(LocalDateTime.now(), LocalDateTime.now().plusDays(60), "Pasakums 3", "Rīga", "Pasākums visiem cilvēkiem!", "vasara beigas",  komentars3, pasakumaKategorija2);
				pasakumsRepo.save(pasakums1);
				pasakumsRepo.save(pasakums2);
				pasakumsRepo.save(pasakums3);
				PasakumaBilde bilde1 = new PasakumaBilde("bilde1.jpg", "bilde1", "laba bilde", pasakums1);
				PasakumaBilde bilde2 = new PasakumaBilde("bilde2.jpg", "bilde2", "slikta bilde", pasakums2);
				
				pasakumaBildeRepo.save(bilde1);
				pasakumaBildeRepo.save(bilde2);
			}
		};

	}

}
