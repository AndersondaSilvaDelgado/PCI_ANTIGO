package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class PlantaCabecDAO {

    public PlantaCabecDAO() {
    }

    public PlantaCabecBean salvarPlantaCabec(Long idPlantaItem, Long idCabec){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        plantaCabecBean.setIdPlanta(idPlantaItem);
        plantaCabecBean.setIdCabec(idCabec);
        plantaCabecBean.setStatusPlantaCabec(1L);
        plantaCabecBean.setStatusApontPlanta(0L);
        plantaCabecBean.insert();
        return plantaCabecBean;
    }

    public void updStatusApontPlanta(PlantaCabecBean plantaCabecBean, Long idCabec){
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

    public void updStatusPlantaFechadoEnvio(){
        List<PlantaCabecBean> plantaCabecList = plantaCabecTermList();
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

    public boolean verPlantaAberto(Long idCabec){
        ArrayList<PlantaCabecBean> plantaCabecArrayList = plantaCabecArrayList(idCabec);
        boolean ret = (plantaCabecArrayList.size() > 0);
        plantaCabecArrayList.clear();
        return ret;
    }

    public boolean verPlantaEnvio(Long idCabec){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqPlantaIdCabec(idCabec));
        pesqArrayList.add(getPesqPlantaFechada());

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

    public ArrayList<Long> idCabecPlantaEnvioList(){

        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList();

        ArrayList<Long> idCabecPlantaList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
            idCabecPlantaList.add(plantaCabecBean.getIdCabec());
        }

        plantaCabecList.clear();

        return idCabecPlantaList;

    }

    public ArrayList<Long> idPlantaCabecEnvioList(){

        List<PlantaCabecBean> plantaCabecList = plantaCabecEnvioList();

        ArrayList<Long> idPlantaCabecList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
            idPlantaCabecList.add(plantaCabecBean.getIdPlantaCabec());
        }

        plantaCabecList.clear();

        return idPlantaCabecList;

    }

    public ArrayList<Long> idPlantaCabecAbertaList(Long idCabec){

        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);

        ArrayList<Long> idPlantaCabecList = new ArrayList<>();
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            if(plantaCabecBean.getStatusPlantaCabec() < 2) {
                idPlantaCabecList.add(plantaCabecBean.getIdPlantaCabec());
            }
        }

        plantaCabecList.clear();
        return idPlantaCabecList;

    }

    public boolean verPlantaCabecFechada(Long idCabec){

        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        boolean ver = false;
        for (int i = 0; i < plantaCabecList.size(); i++) {
            PlantaCabecBean plantaCabecBean = plantaCabecList.get(i);
            if(plantaCabecBean.getStatusPlantaCabec() >= 2) {
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

    public void deletePlantaCabec(Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        for(int i = 0; i < plantaCabecList.size(); i++){
            PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
            plantaCabecBean.delete();
        }
    }

    public void deletePlantaCabecAberto(Long idCabec){
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        for(int i = 0; i < plantaCabecList.size(); i++){
            PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(i);
            plantaCabecBean.delete();
        }
    }

    public ArrayList<PlantaCabecBean> plantaCabecArrayList(Long idCabec){
        ArrayList<PlantaCabecBean> plantaCabecArrayList = new ArrayList<>();
        List<PlantaCabecBean> plantaCabecList = plantaCabecList(idCabec);
        for (PlantaCabecBean plantaCabecBean : plantaCabecList) {
            if(plantaCabecBean.getStatusPlantaCabec() < 3){
                plantaCabecArrayList.add(plantaCabecBean);
            }
        }
        return plantaCabecArrayList;
    }

    public PlantaCabecBean getPlantaCabecApont(){
        List<PlantaCabecBean> plantaCabecList = plantaCabecApontList();
        PlantaCabecBean plantaCabecBean = plantaCabecList.get(0);
        plantaCabecList.clear();
        return plantaCabecBean;
    }

    public List<PlantaCabecBean> plantaCabecList(ArrayList<Long> idPlantaCabecList){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.in("idPlantaCabec", idPlantaCabecList);
    }

    public List<PlantaCabecBean> plantaCabecApontList(){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get("statusApontPlanta", 1L);
    }

    public List<PlantaCabecBean> plantaCabecTermList(){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get("statusPlantaCabec", 2L);
    }

    public List<PlantaCabecBean> plantaCabecEnvioList(){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get("statusPlantaCabec", 3L);
    }

    public List<PlantaCabecBean> plantaCabecList(Long idCabec){
        PlantaCabecBean plantaCabecBean = new PlantaCabecBean();
        return plantaCabecBean.get("idCabec", idCabec);
    }

    private EspecificaPesquisa getPesqPlantaIdCabec(Long idCabec){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idCabec");
        pesquisa.setValor(idCabec);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqPlantaFechada(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusPlantaCabec");
        pesquisa.setValor(2L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
