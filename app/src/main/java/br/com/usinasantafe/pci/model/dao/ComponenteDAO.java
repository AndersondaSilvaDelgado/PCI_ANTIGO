package br.com.usinasantafe.pci.model.dao;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ComponenteBean;
import br.com.usinasantafe.pci.model.bean.estatica.ServicoBean;

public class ComponenteDAO {

    public ComponenteDAO() {
    }

    public boolean verComponente(Long idComponente){
        List<ComponenteBean> componenteList = componenteList(idComponente);
        boolean ret = (componenteList.size() > 0);
        componenteList.clear();
        return ret;
    }

    public ComponenteBean getComponente(Long idComponente){
        List<ComponenteBean> componenteList = componenteList(idComponente);
        ComponenteBean componenteBean = componenteList.get(0);
        componenteList.clear();
        return componenteBean;
    }

    public List<ComponenteBean> componenteList(Long idComponente){
        ComponenteBean componenteBean = new ComponenteBean();
        return componenteBean.get("idComponente", idComponente);
    }

}
