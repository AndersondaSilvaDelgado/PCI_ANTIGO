package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.estatica.PlantaBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;

public class PlantaDAO {

    public PlantaDAO() {
    }

    public boolean verPlanta(ArrayList<Long> idPlantaList){
        PlantaBean plantaBean = new PlantaBean();
        List<PlantaBean> plantaList = plantaBean.in("idPlanta", idPlantaList);
        boolean ret = (plantaList.size() == 0);
        plantaList.clear();
        return ret;
    }

    public boolean verPlanta(Long idPlanta){
        List<PlantaBean> plantaList = plantaList(idPlanta);
        boolean ret = plantaList.size() == 0;
        plantaList.clear();
        return ret;
    }

    public PlantaBean getPlanta(Long idPlanta){
        List<PlantaBean> plantaList = plantaList(idPlanta);
        PlantaBean plantaBean = plantaList.get(0);
        plantaList.clear();
        return plantaBean;
    }

    public List<PlantaBean> plantaList(Long idPlanta){
        PlantaBean plantaBean = new PlantaBean();
        return plantaBean.get("idPlanta", idPlanta);
    }

}
