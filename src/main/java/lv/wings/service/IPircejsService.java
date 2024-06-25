package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Pircejs;

public interface IPircejsService {
    public abstract ArrayList<Pircejs> selectAllPircejs() throws Exception;

    public abstract Pircejs selectPircejsById(int id_pirc) throws Exception;

    public abstract void deletePircejsById(int id_pirc) throws Exception;

    public abstract void insertNewPircejs(Pircejs pircejs) throws Exception;

    public abstract void updatePircejsById(int id_pirc, Pircejs pircejs) throws Exception;
}
