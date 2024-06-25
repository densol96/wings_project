package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Pirkums;

public interface IPirkumsService {

    public abstract ArrayList<Pirkums> selectAllPirkums() throws Exception;

    public abstract Pirkums selectPirkumsById(int pirkums_ID) throws Exception;

    public abstract void deletePirkumsById(int pirkums_ID) throws Exception;

    public abstract void insertNewPirkums(Pirkums pirkums) throws Exception;

    public abstract void updatePirkumsById(int pirkums_ID, Pirkums pirkums);

}
