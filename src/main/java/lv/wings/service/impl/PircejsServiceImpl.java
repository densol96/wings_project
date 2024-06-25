package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Pircejs;
import lv.wings.repo.IPircejs_Repo;
import lv.wings.service.IPircejsService;

@Service
public class PircejsServiceImpl implements IPircejsService{

    @Autowired
    private IPircejs_Repo pircejsRepo;

    @Override
    public ArrayList<Pircejs> selectAllPircejs() throws Exception {
       if(pircejsRepo.count() == 0) throw new Exception("Nav neviena pirceja");

        return (ArrayList<Pircejs>) pircejsRepo.findAll();
    }

    @Override
    public Pircejs selectPircejsById(int id_pirc) throws Exception {
        if(id_pirc < 0) throw new Exception("ID ir negativs");

        if(pircejsRepo.existsById(id_pirc)){
            return pircejsRepo.findById(id_pirc).get();
        } else {
            throw new Exception("Pircejs ar ID [" + id_pirc + "] neeksiste");
        }
    }

    @Override
    public void deletePircejsById(int id_pirc) throws Exception {
        Pircejs pircejsToDelete = selectPircejsById(id_pirc);

        pircejsRepo.delete(pircejsToDelete);
    }

    @Override
    public void insertNewPircejs(Pircejs pircejs) throws Exception {
       Pircejs pircejsExist = pircejsRepo.findByBankas_SWIFT_kodsAndBankas_kods(pircejs.getBankas_SWIFT_kods(), pircejs.getBankas_kods());

        if(pircejsExist == null) {
            pircejsRepo.save(pircejs);
        } else {
            throw new Exception("Pircejs jau eksiste");
        }

    }

    @Override
    public void updatePircejsById(int id_pirc, Pircejs pircejs) throws Exception {
        Pircejs pircejsToUpdate = selectPircejsById(id_pirc);

        pircejsToUpdate.setVards(pircejs.getVards());
        pircejsToUpdate.setUzvards(pircejs.getUzvards());
        pircejsToUpdate.setAdrese(pircejs.getAdrese());
        pircejsToUpdate.setPersonas_kods(pircejs.getPersonas_kods());
        pircejsToUpdate.setBankas_nosaukums(pircejs.getBankas_nosaukums());
        pircejsToUpdate.setBankas_kods(pircejs.getBankas_kods());
        pircejsToUpdate.setBankas_SWIFT_kods(pircejs.getBankas_SWIFT_kods());
    }
    
}
