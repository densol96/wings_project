package lv.wings;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import lv.wings.model.Pirkums;
import lv.wings.service.IPirkumsService;

@SpringBootTest
public class PirkumsServiceTest {
    
    @Autowired
    IPirkumsService pirkumsService;


    @Test
    void selectAllPirkumiTest() throws Exception {
        ArrayList<Pirkums> pirkumi = pirkumsService.selectAllPirkums(); 
        assertNotNull(pirkumi);
    }

    @Test
    void selectOnePirkumsTest() throws Exception {
        Pirkums pirkums = pirkumsService.selectPirkumsById(1);
        assertNotNull(pirkums);
    }

    @Test
    void deletePirkumsTest() throws Exception {
        pirkumsService.deletePirkumsById(444);
        assertThrowsExactly(Exception.class, () -> pirkumsService.selectPirkumsById(444));
    }


    @Test
    void insertPirkumsTest() throws Exception {
        ArrayList<Pirkums> oldPirkumi = pirkumsService.selectAllPirkums();
        int oldAmount = oldPirkumi.size();
        
        Pirkums pirkums = new Pirkums();
        pirkumsService.insertNewPirkums(pirkums);

        ArrayList<Pirkums> newPirkumi = pirkumsService.selectAllPirkums();
        int newAmount = newPirkumi.size();

        assertNotEquals(oldAmount, newAmount);
    }


    @Test
    void updatePirkumsTest() throws Exception {
        Pirkums pirkums = new Pirkums();
        Pirkums oldPirkums = pirkumsService.selectPirkumsById(1);

        pirkumsService.updatePirkumsById(1, pirkums);
        Pirkums newPirkums = pirkumsService.selectPirkumsById(1);

        assertNotEquals(newPirkums, oldPirkums);
    }


}
