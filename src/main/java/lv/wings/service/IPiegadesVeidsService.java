package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Piegades_veids;

public interface IPiegadesVeidsService {
    
    public abstract ArrayList<Piegades_veids> selectAllPiegadesVeids() throws Exception;

    public abstract Piegades_veids selectPiegadesVeidsById(int pvID) throws Exception;

    public abstract void deletePiegadesVeidsById(int pvID) throws Exception;

    public abstract void insertNewPiegadesVeids(Piegades_veids pv) throws Exception;

    public abstract void updatePiegadesVeidsById(int pvID, Piegades_veids pv) throws Exception;

}
