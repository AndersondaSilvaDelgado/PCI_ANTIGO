package br.com.usinasantafe.pci.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.OSBaseBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.util.Tempo;

public class CabecDAO {

    public CabecDAO() {
    }

    public void salvarCabecAberto(CabecBean cabecBean, OSBaseBean osBaseBean){
        cabecBean.setIdExtCabec(0L);
        cabecBean.setIdOSCabec(osBaseBean.getIdOS());
        cabecBean.setDataCabec(Tempo.getInstance().dataCHora());
        cabecBean.setStatusCabec(1L);
        cabecBean.setStatusApontCabec(1L);
        cabecBean.insert();
    }

    public void updStatusFechado(CabecBean cabecBean){
        cabecBean.setStatusCabec(2L);
        cabecBean.update();
    }

    public void updStatusApont(OSBaseBean osBaseBean){
        List<CabecBean> cabecList = cabecAbertoList();
        for(CabecBean cabecBean : cabecList){
            if(osBaseBean.getIdOS().equals(cabecBean.getIdOSCabec())){
                cabecBean.setStatusApontCabec(1L);
            }
            else{
                cabecBean.setStatusApontCabec(0L);
            }
            cabecBean.update();
        }
    }

    public void deleteCabec(CabecBean cabecBean){
        cabecBean.delete();
    }

    public CabecBean getCabecApont(){
        List<CabecBean> cabecList = cabecApontList();
        CabecBean cabecBean = cabecList.get(0);
        cabecList.clear();
        return cabecBean;
    }

    public boolean verCabecAbertoOS(OSBaseBean osBaseBean){
        List<CabecBean> cabecList = cabecAbertoOSList(osBaseBean);
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public boolean verCabecFechado(){
        List<CabecBean> cabecList = cabecFechadoList();
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public List getListCabecEnvio(ArrayList<Long> idCabecList){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.in("idCabec", idCabecList);
    }

    public List<CabecBean> cabecApontList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusApontCabec", 1L);
    }

    public List<CabecBean> cabecAbertoList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusCabec", 1L);
    }

    public List<CabecBean> cabecFechadoList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusCabec", 2L);
    }

    public String dadosEnvioCabec(List cabecList){

        JsonArray jsonArrayCabec = new JsonArray();

        for (int i = 0; i < cabecList.size(); i++) {

            CabecBean cabecBean = (CabecBean) cabecList.get(i);
            Gson gson = new Gson();
            jsonArrayCabec.add(gson.toJsonTree(cabecBean, cabecBean.getClass()));

        }

        cabecList.clear();

        JsonObject jsonCabec = new JsonObject();
        jsonCabec.add("cabecalho", jsonArrayCabec);

        return jsonCabec.toString();

    }

    public List<CabecBean> cabecAbertoOSList(OSBaseBean osBaseBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecAberto());
        pesqArrayList.add(getPesqOS(osBaseBean));

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public List<CabecBean> cabecAbertoOficSecaoList(Long idOficSecao){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecAberto());
        pesqArrayList.add(getPesqIdOficSecao(idOficSecao));

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

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

    private EspecificaPesquisa getPesqCabecAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusCabec");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOS(OSBaseBean osBaseBean){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idOSCabec");
        pesquisa.setValor(osBaseBean.getIdOS());
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqIdOficSecao(Long idOficSecao){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idOficSecaoCabec");
        pesquisa.setValor(idOficSecao);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
