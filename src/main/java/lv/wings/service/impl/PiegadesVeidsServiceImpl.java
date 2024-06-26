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
    public Piegades_veids selectPiegadesVeidsById(int pvID) throws Exception {
        if(pvID < 0) throw new Exception("ID ir negativs");

        if(pvRepo.existsById(pvID)){
            return pvRepo.findById(pvID).get();
        } else {
            throw new Exception("Piegades veids ar ID [" + pvID+ "] neeksiste");
        }
    }

    @Override
    public void deletePiegadesVeidsById(int pvID) throws Exception {
        Piegades_veids pvToDelete = selectPiegadesVeidsById(pvID);

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
    public void updatePiegadesVeidsById(int pvID, Piegades_veids pv) throws Exception {
        Piegades_veids pvToUpdate = selectPiegadesVeidsById(pvID);

        pvToUpdate.setNosaukums(pv.getNosaukums());
        pvToUpdate.setApraksts(pv.getApraksts());

        pvRepo.save(pvToUpdate);
        
    }
    


}
