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

    private ConfigCTR configCTR;
    private CheckListCTR checkListCTR;
    private Long idFunc;

    public static String versaoAplic = "2.01";

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

    public Long getIdFunc() {
        return idFunc;
    }

    public void setIdFunc(Long idFunc) {
        this.idFunc = idFunc;
    }
}
