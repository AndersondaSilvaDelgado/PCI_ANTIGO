package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;

public class ItemDAO {

    public ItemDAO() {
    }

    public List<ItemBean> itemList(Long idPlantaItem) {
        ItemBean itemBean = new ItemBean();
        return itemBean.getAndOrderBy("idPlantaItem", idPlantaItem, "idComponenteItem", true);
    }

    public ArrayList<Long> idPlantaArrayList(Long idOS){
        ItemBean itemBean = new ItemBean();
        List<ItemBean> itemList = itemBean.getAndOrderBy("idOsItem", idOS, "idPlantaItem", true);
        Long idPlanta = 0L;
        ArrayList<Long> idPlantaList = new ArrayList<>();
        for (ItemBean itemBeanBD : itemList) {
            if(!idPlanta.equals(itemBeanBD.getIdPlantaItem())){
                idPlantaList.add(itemBeanBD.getIdPlantaItem());
            }
        }
        return idPlantaList;
    }

}
