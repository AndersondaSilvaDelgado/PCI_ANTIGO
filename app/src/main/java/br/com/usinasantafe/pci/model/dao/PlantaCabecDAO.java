package br.com.usinasantafe.pci.model.dao;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class PlantaCabecDAO {

    public PlantaCabecDAO() {
    }

    public PlantaCabecBean salvarPlantaCabec(Long idPlanta, Long idCabec){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        plantaCabecBean.setIdPlanta(idPlanta);
        plantaCabecBean.setIdCabec(idCabec);
        plantaCabecBean.setStatusPlantaCabec(1L);
        plantaCabecBean.setStatusApontPlanta(0L);
        plantaCabecBean.insert();
        return plantaCabecBean;
    }

    public void updStatusApontPlanta(){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList();
        for(PlantaCabecBean plantaCabec : plantaCabecList){
            plantaCabec.setStatusApontPlanta(0L);
            plantaCabec.update();
        }
    }

    public void updStatusApontPlantaCabec(PlantaCabecBean plantaCabecBean, Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        for(PlantaCabecBean plantaCabec : plantaCabecList){
            if(plantaCabec.getIdPlanta().equals(plantaCabecBean.getIdPlanta())){
                plantaCabec.setStatusApontPlanta(1L);
                plantaCabec.update();
            }
            else{
                plantaCabec.setStatusApontPlanta(0L);
                plantaCabec.update();
            }
        }
        plantaCabecList.clear();
    }

    public void updStatusPlantaFechadoEnvio(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaTerminada());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        List<PlantaCabecBean> plantaCabecList = plantaCabecBean.get(pesqArrayList);
        for(PlantaCabecBean plantaCabec : plantaCabecList){
            plantaCabec.setStatusPlantaCabec(3L);
            plantaCabec.update();
        }
        plantaCabecList.clear();
    }

    public void updStatusPlantaFinalizar(PlantaCabecBean plantaCabec){
        plantaCabec.setStatusPlantaCabec(2L);
        plantaCabec.update();
    }

    public void updPlantaEnviada(String objeto) throws Exception{

        JSONObject plantaJsonObj = new JSONObject(objeto);
        JSONArray plantaJSONArray = plantaJsonObj.getJSONArray("planta");

        for (int i = 0; i < plantaJSONArray.length(); i++) {

            JSONObject plantaObj = plantaJSONArray.getJSONObject(i);
            Gson gsonPlanta = new Gson();
            PlantaCabecBean plantaCabecBean = gsonPlanta.fromJson(plantaObj.toString(), PlantaCabecBean.class);

            List<PlantaCabecBean> plantaCabecList = plantaCabecBean.get("idPlantaCabec", plantaCabecBean.getIdPlantaCabec());
            PlantaCabecBean plantaCabecBD = (PlantaCabecBean) plantaCabecList.get(0);
            plantaCabecList.clear();

            plantaCabecBD.setStatusPlantaCabec(4L);
            plantaCabecBD.update();

        }

    }

    public void deletePlanta(ArrayList<Long> idPlantaCabecArrayList){

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        List<PlantaCabecBean> plantaList = plantaCabecBean.in("idPlantaCabec", idPlantaCabecArrayList);

        for (int i = 0; i < plantaList.size(); i++) {
            plantaCabecBean = (PlantaCabecBean) plantaList.get(i);
            plantaCabecBean.delete();
        }

        idPlantaCabecArrayList.clear();

    }

    public boolean verPlantaAberto(Long idCabec){
        ArrayList<PlantaCabecBean> plantaCabecArrayList = plantaCabecSemEnvioArrayList(idCabec);
        boolean ret = (plantaCabecArrayList.size() > 0);
        plantaCabecArrayList.clear();
        return ret;
    }

    public boolean verPlantaTerminada(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaTerminada());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        List<PlantaCabecBean> plantaCabecList = plantaCabecBean.get(pesqArrayList);
        boolean ret = plantaCabecList.size() > 0;
        plantaCabecList.clear();
        return ret;

    }

    public boolean verPlantaEnvio(){
        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList();
        boolean ret = plantaCabecList.size() > 0;
        plantaCabecList.clear();
        return ret;
    }

    public boolean verPlantaEnvio(Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList(idCabec);
        boolean ret = plantaCabecList.size() > 0;
        plantaCabecList.clear();
        return ret;
    }

    public boolean verPlantaCabecFechada(Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        boolean ver = false;
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            if(plantaCabecBean.getStatusPlantaCabec() > 2L) {
                ver = true;
            }
        }
        plantaCabecList.clear();
        return ver;
    }

    public boolean verPlantaCabec(Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        boolean ret = plantaCabecList.size() > 0;
        plantaCabecList.clear();
        return ret;
    }

    public ArrayList<Long> idCabecPlantaEnvioList(){

        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList();

        ArrayList<Long> idCabecPlantaList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            idCabecPlantaList.add(plantaCabecBean.getIdCabec());
        }

        plantaCabecList.clear();

        return idCabecPlantaList;

    }

    public ArrayList<Long> idPlantaCabecEnvioList(){

        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList();

        ArrayList<Long> idPlantaCabecList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            idPlantaCabecList.add(plantaCabecBean.getIdPlantaCabec());
        }

        plantaCabecList.clear();

        return idPlantaCabecList;

    }

    public ArrayList<Long> idPlantaCabecNaoEnvioList(Long idCabec){

        List<PlantaCabecBean> plantaCabecList = plantaCabecNaoEnvioList(idCabec);

        ArrayList<Long> idPlantaCabecList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            idPlantaCabecList.add(plantaCabecBean.getIdPlantaCabec());
        }

        plantaCabecList.clear();

        return idPlantaCabecList;

    }

    public ArrayList<Long> idPlantaCabecSemEnvioArrayList(Long idCabec){

        ArrayList<PlantaCabecBean> plantaCabecList = plantaCabecSemEnvioArrayList(idCabec);

        ArrayList<Long> idPlantaCabecList = new ArrayList<>();
        for (PlantaCabecBean plantaCabecBean : plantaCabecList) {
            idPlantaCabecList.add(plantaCabecBean.getIdPlantaCabec());
        }

        plantaCabecList.clear();
        return idPlantaCabecList;

    }

    public PlantaCabecBean getPlantaCabecApont(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaAponta());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        List<PlantaCabecBean> plantaCabecList = plantaCabecBean.get(pesqArrayList);
        plantaCabecBean = plantaCabecList.get(0);
        plantaCabecList.clear();
        return plantaCabecBean;

    }

    public ArrayList<PlantaCabecBean> plantaCabecSemEnvioArrayList(Long idCabec){
        ArrayList<PlantaCabecBean> plantaCabecArrayList = new ArrayList<>();
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        for (PlantaCabecBean plantaCabecBean : plantaCabecList) {
            if(plantaCabecBean.getStatusPlantaCabec() < 3L){
                plantaCabecArrayList.add(plantaCabecBean);
            }
        }
        plantaCabecList.clear();
        return plantaCabecArrayList;
    }

    public List<PlantaCabecBean> plantaCabecList(){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.all();
    }

    public List<PlantaCabecBean> plantaCabecEnvioList(){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaEnvio());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get(pesqArrayList);
    }

    public List<PlantaCabecBean> plantaCabecEnvioList(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaEnvio());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get(pesqArrayList);

    }

    public List<PlantaCabecBean> plantaCabecNaoEnvioList(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaNaoEnvio());

        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get(pesqArrayList);

    }

    public List<PlantaCabecBean> plantaCabecList(Long idCabec){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get("idCabec", idCabec);
    }

    public String dadosEnvioPlantaCabec(List plantaCabecList){

        JsonArray jsonArrayPlantaCabec = new JsonArray();

        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
            Gson gson = new Gson();
            jsonArrayPlantaCabec.add(gson.toJsonTree(plantaCabecBean, plantaCabecBean.getClass()));
        }

        plantaCabecList.clear();

        JsonObject jsonPlanta = new JsonObject();
        jsonPlanta.add("planta", jsonArrayPlantaCabec);

        return jsonPlanta.toString();

    }

    private EspecificaPesquisa getPesqPlantaIdCabec(Long idCabec){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idCabec");
        pesquisa.setValor(idCabec);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPlantaAponta(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusApontPlanta");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPlantaTerminada(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusPlantaCabec");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPlantaEnvio(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusPlantaCabec");
        pesquisa.setValor(3L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPlantaNaoEnvio(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusPlantaCabec");
        pesquisa.setValor(3L);
        pesquisa.setTipo(2);
        return pesquisa;
    }

}
