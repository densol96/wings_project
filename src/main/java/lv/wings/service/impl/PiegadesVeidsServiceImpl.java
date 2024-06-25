package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Piegades_veids;
import lv.wings.repo.IPiegades_veids_Repo;
import lv.wings.service.IPiegadesVeidsService;

@Service
public class PiegadesVeidsServiceImpl implements IPiegadesVeidsService {

    @Autowired
    private IPiegades_veids_Repo pvRepo;


    @Override
    public ArrayList<Piegades_veids> selectAllPiegadesVeids() throws Exception {
        if(pvRepo.count() == 0) throw new Exception("Nav neviena piegades veida");

        return (ArrayList<Piegades_veids>) pvRepo.findAll();
    }

    @Override
    public Piegades_veids selectPiegadesVeidsById(int pv_ID) throws Exception {
        if(pv_ID < 0) throw new Exception("ID ir negativs");

        if(pvRepo.existsById(pv_ID)){
            return pvRepo.findById(pv_ID).get();
        } else {
            throw new Exception("Piegades veids ar ID [" + pv_ID+ "] neeksiste");
        }
    }

    @Override
    public void deletePiegadesVeidsById(int pv_ID) throws Exception {
        Piegades_veids pvToDelete = selectPiegadesVeidsById(pv_ID);

        pvRepo.delete(pvToDelete);
    }

    @Override
    public void insertNewPiegadesVeids(Piegades_veids pv) throws Exception {
        Piegades_veids pvExist = pvRepo.findByNosaukums(pv.getNosaukums());

        if(pvExist == null) {
            pvRepo.save(pv);
        } else {
            throw new Exception("Piegades veids eksiste");
        }
    }

    @Override
    public void updatePiegadesVeidsById(int pv_ID, Piegades_veids pv) throws Exception {
        Piegades_veids pvToUpdate = selectPiegadesVeidsById(pv_ID);

        pvToUpdate.setNosaukums(pv.getNosaukums());
        pvToUpdate.setApraksts(pv.getApraksts());

        pvRepo.save(pvToUpdate);
        
    }
    


}
