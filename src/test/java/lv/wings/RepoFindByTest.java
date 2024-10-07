package lv.wings;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.model.Piegades_veids;
import lv.wings.model.Pircejs;
import lv.wings.model.Pirkums;
import lv.wings.model.Samaksas_veids;
import lv.wings.repo.IPiegades_veids_Repo;
import lv.wings.repo.IPircejs_Repo;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.repo.ISamaksas_veids_Repo;

@SpringBootTest
public class RepoFindByTest {
    
    @Autowired
    private IPircejs_Repo pircejs_Repo;

    @Autowired
    private IPiegades_veids_Repo piegades_veids_Repo;

    @Autowired
    private ISamaksas_veids_Repo samaksas_veids_Repo;

    @Autowired
    private IPirkums_Repo pirkums_Repo;

    @Test
    @Order(3)
    public void findMarkussTest() {
        Pircejs markuss = new Pircejs("Markuss", "Blumbergs", "random1@gmail.com", "Talsi, Street 1",
        "000000-00001", "Liela banka", "246810", "Bankas kods 123");    
        Pircejs found = pircejs_Repo.findByBankasSwiftKodsAndBankasKods(markuss.getBankasSwiftKods(), markuss.getBankasKods());

        assertEquals(markuss.getVards(), found.getVards());
    }

    @Test
    @Order(2)
    @Disabled
    public void findPiegadesVeidsTest() {
        Piegades_veids pv1 = new Piegades_veids("Piegades Veids1", "Apraksts1");
        Piegades_veids found = piegades_veids_Repo.findByNosaukums(pv1.getNosaukums());

        assertEquals(pv1.getNosaukums(), found.getNosaukums());
    }


    @Test
    @Order(1)
    public void findSamaksasVeidsTest() {
        Samaksas_veids sv1 = new Samaksas_veids("Samaksas Veids 1", "Piezimes1");
        Samaksas_veids found = samaksas_veids_Repo.findByNosaukums(sv1.getNosaukums());

        assertEquals(sv1.getNosaukums(), found.getNosaukums());
    }


    @Test
    @Order(4)
    public void findPirkumsTest() {
        Pircejs markuss = pircejs_Repo.findByBankasSwiftKodsAndBankasKods("246810", "Bankas kods 123");
        Piegades_veids pv1 = piegades_veids_Repo.findByNosaukums("Piegades Veids1");
        Samaksas_veids sv1 = samaksas_veids_Repo.findByNosaukums("Samaksas Veids1");

        ArrayList<Pirkums> findPirkumsWithPircejs = pirkums_Repo.findByPircejs(markuss);
        ArrayList<Pirkums> findPirkumsWithPiegadesVeids = pirkums_Repo.findByPiegadesVeids(pv1);
        ArrayList<Pirkums> findPirkumsWithSamaksasVeids = pirkums_Repo.findBySamaksasVeids(sv1);

        assertAll( "pirkumi",
            () -> assertNotNull(findPirkumsWithPircejs),
            () -> assertNotNull(findPirkumsWithPiegadesVeids),
            () -> assertNotNull(findPirkumsWithSamaksasVeids)
        ); 
    }
    

    
}
