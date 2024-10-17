package lv.wings.service.impl;

import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import lv.wings.model.Pirkuma_elements;
import lv.wings.model.Pirkums;
import lv.wings.repo.IPirkuma_elements_repo;
import lv.wings.repo.IPirkums_Repo;
import lv.wings.service.ICRUDService;

@Service
public class PirkumsServiceImpl implements ICRUDService<Pirkums>{
    
    @Autowired
    private IPirkums_Repo pirkumsRepo;

    @Autowired
	private IPirkuma_elements_repo elementsRepo;


    @Override
    public ArrayList<Pirkums> retrieveAll() throws Exception {
        if(pirkumsRepo.count() == 0) throw new Exception("Nav neviena pirkuma");

        return (ArrayList<Pirkums>) pirkumsRepo.findAll();
    }


    @Override
    public Pirkums retrieveById(int pirkumsID) throws Exception {
        
        if(pirkumsID < 0) throw new Exception("ID ir negativs");

        if(pirkumsRepo.existsById(pirkumsID)){
            return pirkumsRepo.findById(pirkumsID).get();
        } else {
            throw new Exception("Pirkums ar ID [" + pirkumsID + "] neeksiste");
        }

    }


    @Override
    public void deleteById(int pirkumsID) throws Exception {
        Pirkums pirkumsToDelete = retrieveById(pirkumsID);

        ArrayList<Pirkuma_elements> pirkumaElementi = elementsRepo.findByPirkums(pirkumsToDelete);
        
        for(int i = 0; i < pirkumaElementi.size(); i++) {
            pirkumaElementi.get(i).setPirkums(null);
            elementsRepo.save(pirkumaElementi.get(i));
        }

        pirkumsRepo.delete(pirkumsToDelete);
    }


    @Override
    public void create(Pirkums pirkums) throws Exception {
        
        pirkumsRepo.save(pirkums);

    }


    @Override
    public void update(int pirkumsID, Pirkums pirkums) {
        



    }

}
