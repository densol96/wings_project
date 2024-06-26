package lv.wings.service;

import java.util.ArrayList;

import lv.wings.model.Samaksas_veids;

public interface ISamaksasVeidsService {
    
    public abstract ArrayList<Samaksas_veids> selectAllSamaksasVeids() throws Exception;

    public abstract Samaksas_veids selectSamaksasVeidsById(int svID) throws Exception;

    public abstract void deleteSamaksasVeidsById(int svID) throws Exception;

    public abstract void insertSamaksasVeids(Samaksas_veids sv) throws Exception;

    public abstract void updateSamaksasVeidsById(int svID, Samaksas_veids sv) throws Exception;
}
