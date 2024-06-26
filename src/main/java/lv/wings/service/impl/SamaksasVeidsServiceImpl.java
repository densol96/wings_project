package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Samaksas_veids;
import lv.wings.repo.ISamaksas_veids_Repo;
import lv.wings.service.ISamaksasVeidsService;


@Service
public class SamaksasVeidsServiceImpl implements ISamaksasVeidsService {

    @Autowired ISamaksas_veids_Repo svRepo;


    @Override
    public ArrayList<Samaksas_veids> selectAllSamaksasVeids() throws Exception {
        if(svRepo.count() == 0) throw new Exception("Nav neviena samaksas veida");

        return (ArrayList<Samaksas_veids>) svRepo.findAll();
    }

    @Override
    public Samaksas_veids selectSamaksasVeidsById(int svID) throws Exception {
        if(svID < 0) throw new Exception("ID ir negativs");

        if(svRepo.existsById(svID)){
            return svRepo.findById(svID).get();
        } else {
            throw new Exception("Samaksas veids ar ID [" + svID + "] neeksiste");
        }
    }

    @Override
    public void deleteSamaksasVeidsById(int svID) throws Exception {
        Samaksas_veids svToDelete = selectSamaksasVeidsById(svID);



        svRepo.delete(svToDelete);
    }

    @Override
    public void insertSamaksasVeids(Samaksas_veids sv) throws Exception {
        Samaksas_veids svExist = svRepo.findByNosaukums(sv.getNosaukums());

        if(svExist == null) {
            svRepo.save(sv);
        } else {
            throw new Exception("Samaksas veids eksiste");
        }
    }

    @Override
    public void updateSamaksasVeidsById(int svID, Samaksas_veids sv) throws Exception{
        Samaksas_veids svToUpdate = selectSamaksasVeidsById(svID);

        svToUpdate.setNosaukums(sv.getNosaukums());
        svToUpdate.setPiezimes(sv.getPiezimes());
        
        svRepo.save(svToUpdate);


    }
    
}
