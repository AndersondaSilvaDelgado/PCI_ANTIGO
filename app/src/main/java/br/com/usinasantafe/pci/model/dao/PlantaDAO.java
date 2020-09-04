package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.PlantaBean;

public class PlantaDAO {

    public PlantaDAO() {
    }

    public boolean verPlanta(ArrayList<Long> idPlantaList){
        PlantaBean plantaBean = new PlantaBean();
        List plantaList = plantaBean.in("idPlanta", idPlantaList);
        boolean ret = plantaList.size() == 0;
        plantaList.clear();
        return ret;
    }

}
