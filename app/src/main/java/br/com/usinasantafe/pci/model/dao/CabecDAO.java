package br.com.usinasantafe.pci.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.util.Tempo;

public class CabecDAO {

    public CabecDAO() {
    }

    public void salvarCabecAberto(CabecBean cabecBean, OSBean osBean){
        cabecBean.setIdOSCabec(osBean.getIdOS());
        cabecBean.setDataCabec(Tempo.getInstance().dataCHora());
        cabecBean.setStatusCabec(1L);
        cabecBean.setStatusApontCabec(1L);
        cabecBean.insert();
    }

    public void updStatusFechado(CabecBean cabecBean){
        cabecBean.setStatusCabec(2L);
        cabecBean.update();
    }

    public void updStatusFechado(Long idCabec){

        List<CabecBean> cabecList = cabecList(idCabec);
        CabecBean cabecBean = cabecList.get(0);
        cabecList.clear();

        cabecBean.setStatusCabec(2L);
        cabecBean.update();

    }

    public void updStatusApont(){
        List<CabecBean> cabecList = cabecAllList();
        for(CabecBean cabecBean : cabecList){
            cabecBean.setStatusApontCabec(0L);
            cabecBean.update();
        }
    }

    public void updStatusApont(OSBean osBean){
        List<CabecBean> cabecList = cabecAbertoList();
        for(CabecBean cabecBean : cabecList){
            if(osBean.getIdOS().equals(cabecBean.getIdOSCabec())){
                cabecBean.setStatusApontCabec(1L);
            }
            else{
                cabecBean.setStatusApontCabec(0L);
            }
            cabecBean.update();
        }
    }

    public void deleteCabec(Long idCabec){
        List<CabecBean> cabecList = cabecList(idCabec);
        CabecBean cabecBean = cabecList.get(0);
        cabecList.clear();
        cabecBean.delete();
    }

    public CabecBean getCabecApont(){
        List<CabecBean> cabecList = cabecApontList();
        CabecBean cabecBean = cabecList.get(0);
        cabecList.clear();
        return cabecBean;
    }

    public boolean verCabecAbertoOS(OSBean osBean){
        List<CabecBean> cabecList = cabecAbertoOSList(osBean);
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public boolean verCabecFechado(Long idFunc){
        List<CabecBean> cabecList = cabecFechadoList(idFunc);
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public List<CabecBean> cabecAllList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.all();
    }

    public List<CabecBean> cabecList(ArrayList<Long> idCabecList){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.in("idCabec", idCabecList);
    }

    public List<CabecBean> cabecList(Long idCabec){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("idCabec", idCabec);
    }

    public List<CabecBean> cabecApontList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecApont());

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public List<CabecBean> cabecAbertoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecAberto());

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public List<CabecBean> cabecFechadoList(Long idFunc){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecFechado());
        pesqArrayList.add(getPesqIdFuncCabec(idFunc));

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public List<CabecBean> cabecFechadoList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecFechado());

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

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

    public List<CabecBean> cabecAbertoOSList(OSBean osBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecAberto());
        pesqArrayList.add(getPesqOS(osBean));

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public ArrayList<Long> cabecDiaAnterior(){
        ArrayList<Long> idCabecList = new ArrayList<>();
        List<CabecBean> cabecList = cabecAllList();
        for(CabecBean cabecBean : cabecList){
            if(!cabecBean.getDataCabec().substring(0,10).equals(Tempo.getInstance().dataSHora())){
                idCabecList.add(cabecBean.getIdCabec());
            }
        }
        return idCabecList;
    }

    private EspecificaPesquisa getPesqIdFuncCabec(Long idFuncCabec){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idFuncCabec");
        pesquisa.setValor(idFuncCabec);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqCabecAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusCabec");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqCabecFechado(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusCabec");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOS(OSBean osBean){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idOSCabec");
        pesquisa.setValor(osBean.getIdOS());
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqCabecApont(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontCabec");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
