package br.com.usinasantafe.pci.control;

import android.app.ProgressDialog;
import android.content.Context;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.dao.CabecDAO;
import br.com.usinasantafe.pci.model.dao.FuncDAO;
import br.com.usinasantafe.pci.model.dao.OSDAO;
import br.com.usinasantafe.pci.model.dao.PlantaDAO;
import br.com.usinasantafe.pci.model.dao.RespItemDAO;
import br.com.usinasantafe.pci.util.AtualDadosServ;
import br.com.usinasantafe.pci.util.Tempo;

public class CheckListCTR {

    private CabecBean cabecBean;

    public CheckListCTR() {
    }

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

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// VERIFICAR DADOS /////////////////////////////////////

    public boolean hasElementFunc(){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.hasElements();
    }

    public boolean verFunc(Long matricFunc){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.verFunc(matricFunc);
    }

    public boolean verPlanta(){
        OSDAO osDAO = new OSDAO();
        PlantaDAO plantaDAO = new PlantaDAO();
        ArrayList<Long> idPlantaList = osDAO.idPlantaOSList();
        boolean ret = plantaDAO.verPlanta(idPlantaList);
        idPlantaList.clear();
        return ret;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// GET CAMPOS /////////////////////////////////////

    public FuncBean getFunc(Long matricFunc){
        FuncDAO funcDAO = new FuncDAO();
        return funcDAO.getFunc(matricFunc);
    }

    public CabecBean getCabecBean() {
        return cabecBean;
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ////////////////////////////// SETAR CAMPOS /////////////////////////////////////

    public void setCabecBean(CabecBean cabecBean) {
        this.cabecBean = cabecBean;
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
