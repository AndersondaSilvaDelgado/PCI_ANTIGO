package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;

public class RespItemDAO {

    public RespItemDAO() {
    }

    public List respItemList(ArrayList<Long> idCabecList){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.in("idCabRespItem", idCabecList);
    }

    public void deleteItemCabec(ArrayList<Long> idCabecList){
        List respItemList = respItemList(idCabecList);
        for(int i = 0; i < respItemList.size(); i++){
            RespItemBean respItemBean = (RespItemBean) respItemList.get(i);
            respItemBean.delete();
        }
    }

}
