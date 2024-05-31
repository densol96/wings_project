package lv.wings;

import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import lv.wings.model.Piegades_veids;
import lv.wings.model.Pircejs;
import lv.wings.model.Pirkums;
import lv.wings.model.Samaksas_veids;
import lv.wings.repo.IPiegades_veids_Repo;
import lv.wings.repo.IPircejs_Repo;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.repo.ISamaksas_veids_Repo;

@SpringBootApplication
public class SparniProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(SparniProjectApplication.class, args);
	}

	@Bean
	public CommandLineRunner sparniDB(IPircejs_Repo pircejs_repo, IPirkums_Repo pirkums_repo,
			IPiegades_veids_Repo piegades_veids_repo, ISamaksas_veids_Repo samaksas_veids_repo) {
		
		
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
            	
            	
            	Pirkums pirkums1 = new Pirkums(piegades_veids1, samaksas_veids1, pircejs1, LocalDateTime.now(),"Nav detalas");
            	Pirkums pirkums2 = new Pirkums(piegades_veids2, samaksas_veids2, pircejs2, LocalDateTime.now(),"Nav detalas");
            	pirkums_repo.save(pirkums1);
            	pirkums_repo.save(pirkums2);
            }
		};
		
		
	}
}
