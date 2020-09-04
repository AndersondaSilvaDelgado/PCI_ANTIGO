package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.util.Tempo;

public class CabecDAO {

    public CabecDAO() {
    }

    public boolean verCabecFechado(){
        List cabecFitoList = cabecFechadoList();
        boolean ret = cabecFitoList.size() > 0;
        cabecFitoList.clear();
        return ret;
    }

    public List cabecFechadoList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusCabec", 3L);
    }

    public ArrayList<Long> deleteCabec(){
        ArrayList<Long> idCabecList = new ArrayList<>();
        if(verCabecFechado()){
            List cabecList = cabecFechadoList();
            for(int i = 0; i < cabecList.size(); i++){
                CabecBean cabecBean = (CabecBean) cabecList.get(i);
                if(!cabecBean.getDataCabec().substring(0,10).equals(Tempo.getInstance().dataSHora())){
                    idCabecList.add(cabecBean.getIdCabec());
                    cabecBean.delete();
                }
            }
        }
        return idCabecList;
    }

}
