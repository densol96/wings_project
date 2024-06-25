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
    public Samaksas_veids selectSamaksasVeidsById(int sv_ID) throws Exception {
        if(sv_ID < 0) throw new Exception("ID ir negativs");

        if(svRepo.existsById(sv_ID)){
            return svRepo.findById(sv_ID).get();
        } else {
            throw new Exception("Samaksas veids ar ID [" + sv_ID+ "] neeksiste");
        }
    }

    @Override
    public void deleteSamaksasVeidsById(int sv_ID) throws Exception {
        Samaksas_veids svToDelete = selectSamaksasVeidsById(sv_ID);

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
    public void updateSamaksasVeidsById(int sv_ID, Samaksas_veids sv) throws Exception{
        Samaksas_veids svToUpdate = selectSamaksasVeidsById(sv_ID);

        svToUpdate.setNosaukums(sv.getNosaukums());
        svToUpdate.setPiezimes(sv.getPiezimes());
        
        svRepo.save(svToUpdate);


    }
    
}
