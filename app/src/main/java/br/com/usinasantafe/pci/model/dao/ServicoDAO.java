package br.com.usinasantafe.pci.model.dao;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.PlantaBean;
import br.com.usinasantafe.pci.model.bean.estatica.ServicoBean;

public class ServicoDAO {

    public ServicoDAO() {
    }

    public boolean verServico(Long idServico){
        List servicoList = servicoList(idServico);
        boolean ret = servicoList.size() == 0;
        servicoList.clear();
        return ret;
    }

    public ServicoBean getServico(Long idServico){
        List<ServicoBean> servicoList = servicoList(idServico);
        ServicoBean servicoBean = servicoList.get(0);
        servicoList.clear();
        return servicoBean;
    }

    public List<ServicoBean> servicoList(Long idServico){
        ServicoBean servicoBean = new ServicoBean();
        return servicoBean.get("idServico", idServico);
    }

}
