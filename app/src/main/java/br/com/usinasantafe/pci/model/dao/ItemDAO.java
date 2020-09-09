package br.com.usinasantafe.pci.model.dao;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;

public class ItemDAO {

    public ItemDAO() {
    }

    public List<ItemBean> itemList(){
        ItemBean itemBean = new ItemBean();
        return itemBean.all();
    }

    public List<ItemBean> itemList(Long idPlantaItem){
        ItemBean itemBean = new ItemBean();
        return itemBean.getAndOrderBy("idPlantaItem", idPlantaItem, "idComponenteItem", true);
    }

}
