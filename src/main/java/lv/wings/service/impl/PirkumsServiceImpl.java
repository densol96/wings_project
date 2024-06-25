package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Pirkums;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.service.IPirkumsService;

@Service
public class PirkumsServiceImpl implements IPirkumsService{
    
    @Autowired
    private IPirkums_Repo pirkumsRepo;


    @Override
    public ArrayList<Pirkums> selectAllPirkums() throws Exception {
        if(pirkumsRepo.count() == 0) throw new Exception("Nav neviena pirkuma");

        return (ArrayList<Pirkums>) pirkumsRepo.findAll();
    }


    @Override
    public Pirkums selectPirkumsById(int pirkums_ID) throws Exception {
        
        if(pirkums_ID < 0) throw new Exception("ID ir negativs");

        if(pirkumsRepo.existsById(pirkums_ID)){
            return pirkumsRepo.findById(pirkums_ID).get();
        } else {
            throw new Exception("Pirkums ar ID [" + pirkums_ID + "] neeksiste");
        }

    }


    @Override
    public void deletePirkumsById(int pirkums_ID) throws Exception {
        Pirkums pirkumsToDelete = selectPirkumsById(pirkums_ID);

        pirkumsRepo.delete(pirkumsToDelete);
    }


    @Override
    public void insertNewPirkums(Pirkums pirkums) throws Exception {
        
        pirkumsRepo.save(pirkums);

    }


    @Override
    public void updatePirkumsById(int pirkums_ID, Pirkums pirkums) {
        



    }

}
