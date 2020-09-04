package br.com.usinasantafe.pci.model.dao;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.ConfigBean;

public class ConfigDAO {

    public ConfigDAO() {
    }

    public boolean hasElements(){
        ConfigBean configBean = new ConfigBean();
        return configBean.hasElements();
    }

    public ConfigBean getConfig(){
        ConfigBean configBean = new ConfigBean();
        List configList = configBean.all();
        configBean = (ConfigBean) configList.get(0);
        configList.clear();
        return configBean;
    }

    public void salvarConfig(Long numLinha){
        ConfigBean configBean = new ConfigBean();
        configBean.deleteAll();
        configBean.setNumLinhaConfig(numLinha);
        configBean.insert();
    }

}
