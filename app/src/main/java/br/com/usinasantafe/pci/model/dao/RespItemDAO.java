package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.util.Tempo;

public class RespItemDAO {

    public RespItemDAO() {
    }

    public void salvarRespItem(Long idCabec, ItemBean itemBean, Long opcao, String obs){

        RespItemBean respItemBean = new RespItemBean();
        respItemBean.setIdCabRespItem(idCabec);
        respItemBean.setIdItOsMecanRespItem(itemBean.getIdItem());
        respItemBean.setOpcaoRespItem(opcao);
        respItemBean.setObsRespItem(obs);
        respItemBean.setIdPlantaItem(itemBean.getIdPlantaItem());
        respItemBean.setDthrRespItem(Tempo.getInstance().dataCHora());
        respItemBean.insert();

    }

    public void updRespItem(Long idCabec, ItemBean itemBean, Long opcao, String obs){
        RespItemBean respItemBean = getRespItem(idCabec, itemBean.getIdItem());
        respItemBean.setOpcaoRespItem(opcao);
        respItemBean.setObsRespItem(obs);
        respItemBean.setDthrRespItem(Tempo.getInstance().dataCHora());
        respItemBean.update();
    }

    public RespItemBean getRespItem(Long idCabec, Long idItem){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabResp(idCabec));
        pesqArrayList.add(getPesqItOs(idItem));

        RespItemBean respItemBean = new RespItemBean();
        List<RespItemBean> respItemList = respItemBean.get(pesqArrayList);
        respItemBean = respItemList.get(0);
        respItemList.clear();
        return respItemBean;

    }

    public boolean verRespItem(Long idCabec, Long idItem){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabResp(idCabec));
        pesqArrayList.add(getPesqItOs(idItem));

        RespItemBean respItemBean = new RespItemBean();
        List<RespItemBean> respItemList = respItemBean.get(pesqArrayList);
        boolean ret = (respItemList.size() == 0);
        respItemList.clear();
        return ret;

    }

    public void deleteItemCabec(Long idCabec){
        List<RespItemBean> respItemList = respItemList(idCabec);
        for(int i = 0; i < respItemList.size(); i++){
            RespItemBean respItemBean = (RespItemBean) respItemList.get(i);
            respItemBean.delete();
        }
    }

    public void deleteItemCabec(ArrayList<Long> idCabecList){
        List<RespItemBean> respItemList = respItemList(idCabecList);
        for(int i = 0; i < respItemList.size(); i++){
            RespItemBean respItemBean = (RespItemBean) respItemList.get(i);
            respItemBean.delete();
        }
    }

    public List respItemList(ArrayList<Long> idCabecList){
        RespItemBean respItemBean = new RespItemBean();
        return respItemBean.in("idCabRespItem", idCabecList);
    }

    public List respItemList(Long idCabec){
        RespItemBean respItemBean = new RespItemBean();
        return respItemBean.get("idCabRespItem", idCabec);
    }

    private EspecificaPesquisa getPesqCabResp(Long idCabec){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idCabRespItem");
        pesquisa.setValor(idCabec);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqItOs(Long idItem){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idItOsMecanRespItem");
        pesquisa.setValor(idItem);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
