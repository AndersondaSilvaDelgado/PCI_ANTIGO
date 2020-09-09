package br.com.usinasantafe.pci.control;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ComponenteBean;
import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;
import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.estatica.ServicoBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;
import br.com.usinasantafe.pci.model.dao.CabecDAO;
import br.com.usinasantafe.pci.model.dao.ComponenteDAO;
import br.com.usinasantafe.pci.model.dao.FuncDAO;
import br.com.usinasantafe.pci.model.dao.ItemDAO;
import br.com.usinasantafe.pci.model.dao.OSDAO;
import br.com.usinasantafe.pci.model.dao.PlantaCabecDAO;
import br.com.usinasantafe.pci.model.dao.PlantaDAO;
import br.com.usinasantafe.pci.model.dao.RespItemDAO;
import br.com.usinasantafe.pci.model.dao.ServicoDAO;
import br.com.usinasantafe.pci.util.AtualDadosServ;

public class CheckListCTR {

    private CabecBean cabecBean;
    private ItemBean itemBean;

    public CheckListCTR() {
    }

    ////////////////////////////// SALVAR OU ATUALIZAR CABEC /////////////////////////////////////

    public void salvarAtualCabec(OSBean osBean) {

        CabecDAO cabecDAO = new CabecDAO();
        if(!cabecDAO.verCabecAbertoOS(osBean)){
            cabecDAO.salvarCabecAberto(cabecBean, osBean);
        }

        cabecDAO.updStatusApont(osBean);

    }

    public ArrayList retSalvarPlantaCabec(){

        ArrayList plantaCabecArrayList = new ArrayList();
        ItemDAO itemDAO = new ItemDAO();
        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        CabecDAO cabecDAO = new CabecDAO();

        if(!plantaCabecDAO.verPlantaCabec(cabecDAO.getCabecApont().getIdCabec())){
            List<ItemBean> itemList = itemDAO.itemList();
            Long idPlanta = 0L;
            for (ItemBean itemBean : itemList) {
                if(!idPlanta.equals(itemBean.getIdPlantaItem())){
                    plantaCabecArrayList.add(plantaCabecDAO.salvarPlantaCabec(itemBean.getIdPlantaItem(), cabecDAO.getCabecApont().getIdCabec()));
                    idPlanta = itemBean.getIdPlantaItem();
                }
            }
        }
        else{
            plantaCabecArrayList = plantaCabecDAO.plantaCabecArrayList(cabecDAO.getCabecApont().getIdCabec());
        }

        return plantaCabecArrayList;

    }

    public void salvarAtualRespItem(Long opcao, String obs) {

        RespItemDAO respItemDAO = new RespItemDAO();
        CabecDAO cabecDAO = new CabecDAO();
        if(verRespItem()){
            respItemDAO.salvarRespItem(cabecDAO.getCabecApont().getIdCabec(), itemBean, opcao, obs);
        }
        else{
            respItemDAO.updRespItem(cabecDAO.getCabecApont().getIdCabec(), itemBean, opcao, obs);
        }

    }

    public void atualStatusApontPlanta(PlantaCabecBean plantaCabecBean){

        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        CabecDAO cabecDAO = new CabecDAO();
        plantaCabecDAO.updStatusApontPlanta(plantaCabecBean, cabecDAO.getCabecApont().getIdCabec());

    }

    public void atualStatusEnvio(){

        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        plantaCabecDAO.updStatusPlantaFechadoEnvio();
        CabecDAO cabecDAO = new CabecDAO();
        if(!plantaCabecDAO.verPlantaAberto(cabecDAO.getCabecApont().getIdCabec())){
            cabecDAO.updStatusFechado(cabecDAO.getCabecApont());
        }

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////////// DELETAR DADOS  ////////////////////////////////////////////

    public void deleteOSFeita(){
        OSDAO osDAO = new OSDAO();
        osDAO.deleteOSFeita();
    }

    public void deleteCabecRespAntiga(){
        CabecDAO cabecDAO = new CabecDAO();
        ArrayList<Long> idCabecList = cabecDAO.deleteCabec();
        if(idCabecList.size() > 0){
            RespItemDAO respItemDAO = new RespItemDAO();
            respItemDAO.deleteItemCabec(idCabecList);
        }
    }

    public void deleteCheckListApont(){

        CabecDAO cabecDAO = new CabecDAO();
        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        RespItemDAO respItemDAO = new RespItemDAO();

        CabecBean cabecBean = cabecDAO.getCabecApont();

        plantaCabecDAO.deletePlantaCabec(cabecBean.getIdCabec());
        respItemDAO.deleteItemCabec(cabecBean.getIdCabec());
        cabecDAO.deleteCabec(cabecBean);

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// VERIFICAR DADOS /////////////////////////////////////

    public boolean hasElementFunc(){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.hasElements();
    }

    public boolean verFunc(Long matricFunc){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.verMatricFunc(matricFunc);
    }

    public boolean verPlanta(){
        OSDAO osDAO = new OSDAO();
        PlantaDAO plantaDAO = new PlantaDAO();
        ArrayList<Long> idPlantaList = osDAO.idPlantaOSList();
        boolean ret = plantaDAO.verPlanta(idPlantaList);
        idPlantaList.clear();
        return ret;
    }

    public boolean verPlantaEnvio(){
        CabecDAO cabecDAO = new CabecDAO();
        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        return plantaCabecDAO.verPlantaEnvio(cabecDAO.getCabecApont().getIdCabec());
    }

    public boolean verPlanta(ArrayList<ItemBean> itemList){
        boolean verPlanta = false;
        PlantaDAO plantaDAO = new PlantaDAO();
        for(int i = 0; i < itemList.size(); i++){
            ItemBean itemBean = (ItemBean) itemList.get(i);
            verPlanta = plantaDAO.verPlanta(itemBean.getIdPlantaItem());
        }
        return verPlanta;
    }

    public boolean verServico(ArrayList<ItemBean> itemList){
        boolean verServico = false;
        ServicoDAO servicoDAO = new ServicoDAO();
        for(int i = 0; i < itemList.size(); i++){
            ItemBean itemBean = (ItemBean) itemList.get(i);
            verServico = servicoDAO.verServico(itemBean.getIdServicoItem());
        }
        return verServico;
    }

    public boolean verRespItem() {
        RespItemDAO respItemDAO = new RespItemDAO();
        CabecDAO cabecDAO = new CabecDAO();
        return respItemDAO.verRespItem(cabecDAO.getCabecApont().getIdCabec(), itemBean.getIdItem());
    }

    public boolean verServico(Long idServico){
        ServicoDAO servicoDAO = new ServicoDAO();
        return servicoDAO.verServico(idServico);
    }

    public boolean verComponente(Long idComponente){
        ComponenteDAO componenteDAO = new ComponenteDAO();
        return componenteDAO.verComponente(idComponente);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// GET CAMPOS /////////////////////////////////////

    public ServicoBean getServico(Long idServico){
        ServicoDAO servicoDAO = new ServicoDAO();
        return servicoDAO.getServico(idServico);
    }

    public ComponenteBean getComponente(Long idComponente){
        ComponenteDAO componenteDAO = new ComponenteDAO();
        return componenteDAO.getComponente(idComponente);
    }

    public RespItemBean getRespItem(){
        RespItemDAO respItemDAO = new RespItemDAO();
        CabecDAO cabecDAO = new CabecDAO();
        return respItemDAO.getRespItem(cabecDAO.getCabecApont().getIdCabec(), itemBean.getIdItem());
    }

    public ItemBean getItemBean() {
        return itemBean;
    }

    public FuncBean getFunc(Long matricFunc){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.getMatricFunc(matricFunc);
    }

    public FuncBean getFunc(){
        CabecDAO cabecDAO = new CabecDAO();
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.getIdFunc(cabecDAO.getCabecApont().getIdFuncCabec());
    }

    public OSBean getOS(){
        CabecDAO cabecDAO = new CabecDAO();
        OSDAO osDAO = new OSDAO();
        return osDAO.getOS(cabecDAO.getCabecApont().getIdOSCabec());
    }

    public CabecBean getCabecBean() {
        return cabecBean;
    }

    public ArrayList<OSBean> osList(){
        CabecDAO cabecDAO = new CabecDAO();
        OSDAO osDAO = new OSDAO();
        List<CabecBean> cabecAbertoList = cabecDAO.cabecAbertoList();
        List<br.com.usinasantafe.pci.model.bean.estatica.OSBean> osBaseList = osDAO.osBaseList();
        List<br.com.usinasantafe.pci.model.bean.variavel.OSBean> osVarList = osDAO.osVarList();
        ArrayList osCabList = new ArrayList();
        ArrayList<Long> qtdeDiasList = new ArrayList<Long>();
        if(cabecAbertoList.size() > 0){
            for(CabecBean cabecBean : cabecAbertoList){
                for(OSBean osBean : osBaseList){
                    if(cabecBean.getIdOSCabec().equals(osBean.getIdOS())){
                        osCabList.add(osBean);
                        qtdeDiasList.add(osBean.getQtdeDiaOS());
                    }
                }
            }
        }
        for(OSBean osBean : osBaseList){
            boolean verOS = true;
            for (Long qtdeDias : qtdeDiasList) {
                if(osBean.getQtdeDiaOS() == qtdeDias){
                    verOS = false;
                }
            }
            for (br.com.usinasantafe.pci.model.bean.variavel.OSBean osVarBean : osVarList) {
                if(osBean.getIdOS() == osVarBean.getNroOS()){
                    verOS = false;
                }
            }
            if(verOS){
                osCabList.add(osBean);
            }
        }
        return osCabList;
    }

    public ArrayList<ItemBean> getItemArrayList(){

        ArrayList<ItemBean> itemArrayList = new ArrayList<>();

        PlantaCabecDAO plantaCabecDAO = new PlantaCabecDAO();
        PlantaCabecBean plantaCabecBean = plantaCabecDAO.getPlantaCabecApont();

        ItemDAO itemDAO = new ItemDAO();
        List itemList = itemDAO.itemList(plantaCabecBean.getIdPlanta());

        RespItemDAO respItemDAO = new RespItemDAO();
        CabecDAO cabecDAO = new CabecDAO();
        List respItemList = respItemDAO.respItemList(cabecDAO.getCabecApont().getIdCabec());

        for(int i = 0; i < itemList.size(); i++){
            boolean ver = true;
            ItemBean itemBean = (ItemBean) itemList.get(i);
            for (int j = 0; j < respItemList.size(); j++) {
                RespItemBean respItemBean = (RespItemBean) respItemList.get(j);
                if(itemBean.getIdItem().equals(respItemBean.getIdItOsMecanRespItem())){
                    ver = false;
                }
            }
            if(ver) {
                itemBean.setOpcaoSelItem(0L);
                itemArrayList.add(itemBean);
            }
        }

        if(itemArrayList.size() == 0){
            plantaCabecDAO.updStatusPlantaFinalizar(plantaCabecBean);
        }

        for(int i = 0; i < itemList.size(); i++){
            boolean ver = false;
            ItemBean itemBean = (ItemBean) itemList.get(i);
            for (int j = 0; j < respItemList.size(); j++) {
                RespItemBean respItemBean = (RespItemBean) respItemList.get(j);
                if(itemBean.getIdItem().equals(respItemBean.getIdItOsMecanRespItem())){
                    ver = true;
                    if(respItemBean.getOpcaoRespItem() == 2L){
                        itemBean.setOpcaoSelItem(2L);
                    }
                    else if(respItemBean.getOpcaoRespItem() == 1L){
                        itemBean.setOpcaoSelItem(1L);
                    }
                }
            }
            if(ver) {
                itemArrayList.add(itemBean);
            }
        }

        return itemArrayList;

    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// SETAR CAMPOS /////////////////////////////////////

    public void setCabecBean(CabecBean cabecBean) {
        this.cabecBean = cabecBean;
    }

    public void setItemBean(ItemBean itemBean) {
        this.itemBean = itemBean;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////// VERIFICAÇÃO E ATUALIZAÇÃO DE DADOS POR SERVIDOR ///////////////////////

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        OSDAO osDAO = new OSDAO();
        osDAO.verOS(dado, telaAtual, telaProx, progressDialog);
    }

    public void atualDadosFunc(Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ArrayList operadorArrayList = new ArrayList();
        operadorArrayList.add("FuncBean");
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, operadorArrayList);
    }

    public void atualDadosPlanta(Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        ArrayList operadorArrayList = new ArrayList();
        operadorArrayList.add("PlantaBean");
        AtualDadosServ.getInstance().atualGenericoBD(telaAtual, telaProx, progressDialog, operadorArrayList);
    }

    /////////////////////////////////////////////////////////////////////////////////////////////////

}
