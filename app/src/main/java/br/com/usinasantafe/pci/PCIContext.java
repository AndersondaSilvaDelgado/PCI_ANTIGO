package br.com.usinasantafe.pci;

import android.app.Application;

import java.util.ArrayList;

import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

/**
 * Created by anderson on 30/10/2015.
 */
public class PCIContext extends Application {

    private CabecTO cabecTO;
    private ArrayList<RespItemTO> listItemQuestoes;
    private ItemTO itemTO;
    private Long funcVer;

    public static String versaoAplic = "1.0";

    public PCIContext() {
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
