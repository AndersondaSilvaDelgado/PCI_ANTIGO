package br.com.usinasantafe.pci;

import android.app.Application;

import java.util.ArrayList;

import br.com.usinasantafe.pci.control.CheckListCTR;
import br.com.usinasantafe.pci.control.ConfigCTR;
import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;

/**
 * Created by anderson on 30/10/2015.
 */
public class PCIContext extends Application {

    private CabecTO cabecTO;
    private ArrayList<RespItemTO> listItemQuestoes;
    private ItemTO itemTO;
    private Long funcVer;
    private ConfigCTR configCTR;
    private CheckListCTR checkListCTR;

    public static String versaoAplic = "2.00";

    public PCIContext() {
    }

    public ConfigCTR getConfigCTR() {
        if(configCTR == null)
            configCTR = new ConfigCTR();
        return configCTR;
    }

    public CheckListCTR getCheckListCTR() {
        if(checkListCTR == null)
            checkListCTR = new CheckListCTR();
        return checkListCTR;
    }

    public CabecTO getCabecTO() {
        if(cabecTO == null)
            cabecTO = new CabecTO();
        return cabecTO;
    }



    public void setCabecTO(CabecTO cabecTO) {
        this.cabecTO = cabecTO;
    }

    public ArrayList<RespItemTO> getListItemQuestoes() {
        return listItemQuestoes;
    }

    public void setListItemQuestoes(ArrayList<RespItemTO> listItemQuestoes) {
        this.listItemQuestoes = listItemQuestoes;
    }


    public ItemTO getItemTO() {
        return itemTO;
    }

    public void setItemTO(ItemTO itemTO) {
        this.itemTO = itemTO;
    }

    public Long getFuncVer() {
        return funcVer;
    }

    public void setFuncVer(Long funcVer) {
        this.funcVer = funcVer;
    }
}
