package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Pircejs;
import lv.wings.model.Pirkums;
import lv.wings.repo.IPircejs_Repo;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.service.IPircejsService;

@Service
public class PircejsServiceImpl implements IPircejsService{

    @Autowired
    private IPircejs_Repo pircejsRepo;

    @Autowired 
    private IPirkums_Repo pirkumsRepo;

    @Override
    public ArrayList<Pircejs> selectAllPircejs() throws Exception {
       if(pircejsRepo.count() == 0) throw new Exception("Nav neviena pirceja");

        return (ArrayList<Pircejs>) pircejsRepo.findAll();
    }

    @Override
    public Pircejs selectPircejsById(int idpirc) throws Exception {
        if(idpirc < 0) throw new Exception("ID ir negativs");

        if(pircejsRepo.existsById(idpirc)){
            return pircejsRepo.findById(idpirc).get();
        } else {
            throw new Exception("Pircejs ar ID [" + idpirc + "] neeksiste");
        }
    }

    @Override
    public void deletePircejsById(int idpirc) throws Exception {
        Pircejs pircejsToDelete = selectPircejsById(idpirc);

        ArrayList<Pirkums> pirkumi = pirkumsRepo.findByPircejs(pircejsToDelete);
        
        for(int i = 0; i < pirkumi.size(); i++) {
            pirkumi.get(i).setPircejs(null);
            pirkumsRepo.save(pirkumi.get(i));
        }

        pircejsRepo.delete(pircejsToDelete);
    }

    @Override
    public void insertNewPircejs(Pircejs pircejs) throws Exception {
       Pircejs pircejsExist = pircejsRepo.findByBankasSwiftKodsAndBankasKods(pircejs.getBankasSwiftKods(), pircejs.getBankasKods());

        if(pircejsExist == null) {
            pircejsRepo.save(pircejs);
        } else {
            throw new Exception("Pircejs jau eksiste");
        }

    }

    @Override
    public void updatePircejsById(int idpirc, Pircejs pircejs) throws Exception {
        Pircejs pircejsToUpdate = selectPircejsById(idpirc);

        pircejsToUpdate.setVards(pircejs.getVards());
        pircejsToUpdate.setUzvards(pircejs.getUzvards());
        pircejsToUpdate.setAdrese(pircejs.getAdrese());
        pircejsToUpdate.setPersonasKods(pircejs.getPersonasKods());
        pircejsToUpdate.setBankasNosaukums(pircejs.getBankasNosaukums());
        pircejsToUpdate.setBankasKods(pircejs.getBankasKods());
        pircejsToUpdate.setBankasSwiftKods(pircejs.getBankasSwiftKods());
    }
    
}
