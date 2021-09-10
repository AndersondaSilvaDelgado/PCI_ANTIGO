package br.com.usinasantafe.pci.control;

import android.app.ProgressDialog;
import android.content.Context;

import br.com.usinasantafe.pci.view.MenuInicialActivity;
import br.com.usinasantafe.pci.model.bean.variavel.ConfigBean;
import br.com.usinasantafe.pci.model.dao.ConfigDAO;
import br.com.usinasantafe.pci.util.AtualDadosServ;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class ConfigCTR {

    public ConfigCTR() {
    }


    /////////////////////////////MANIPULAR CONFIC///////////////////////////////////////////

    public void salvarConfig(Long numLinha){
        ConfigDAO configDAO = new ConfigDAO();
        configDAO.salvarConfig(numLinha);
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    ///////////////////////////////////////// CONFIG //////////////////////////////////////////////

    public boolean hasElements(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.hasElements();
    }

    public void verAtualAplic(String versaoAplic, MenuInicialActivity menuInicialActivity, ProgressDialog progressDialog) {
        VerifDadosServ.getInstance().verAtualAplic(versaoAplic, menuInicialActivity, progressDialog);
    }


    ///////////////////////////////////////////////////////////////////////////////////////////////

    //////////////////////////////// GET CONFIG, EQUIP E COLAB ////////////////////////////////////

    public ConfigBean getConfig(){
        ConfigDAO configDAO = new ConfigDAO();
        return configDAO.getConfig();
    }

    ///////////////////////////////////////////////////////////////////////////////////////////////

    public void atualTodasTabelas(Context tela, ProgressDialog progressDialog){
        AtualDadosServ.getInstance().atualTodasTabBD(tela, progressDialog);
    }

}
