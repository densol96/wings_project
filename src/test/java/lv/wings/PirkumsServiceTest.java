package lv.wings;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.model.Pirkums;
import lv.wings.service.ICRUDService;

@SpringBootTest
public class PirkumsServiceTest {
    
    @Autowired
    ICRUDService<Pirkums> pirkumsService;


    @Test
    void selectAllPirkumiTest() throws Exception {
        ArrayList<Pirkums> pirkumi = pirkumsService.retrieveAll(); 
        assertNotNull(pirkumi);
    }

    @Test
    void selectOnePirkumsTest() throws Exception {
        Pirkums pirkums = pirkumsService.retrieveById(1);
        assertNotNull(pirkums);
    }

    @Test
    void deletePirkumsTest() throws Exception {
        pirkumsService.deleteById(444);
        assertThrowsExactly(Exception.class, () -> pirkumsService.retrieveById(444));
    }


    @Test
    void insertPirkumsTest() throws Exception {
        ArrayList<Pirkums> oldPirkumi = pirkumsService.retrieveAll();
        int oldAmount = oldPirkumi.size();
        
        Pirkums pirkums = new Pirkums();
        pirkumsService.create(pirkums);

        ArrayList<Pirkums> newPirkumi = pirkumsService.retrieveAll();
        int newAmount = newPirkumi.size();

        assertNotEquals(oldAmount, newAmount);
    }


    @Test
    void updatePirkumsTest() throws Exception {
        Pirkums pirkums = new Pirkums();
        Pirkums oldPirkums = pirkumsService.retrieveById(1);

        pirkumsService.update(1, pirkums);
        Pirkums newPirkums = pirkumsService.retrieveById(1);

        assertNotEquals(newPirkums, oldPirkums);
    }


}
