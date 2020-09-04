package br.com.usinasantafe.pci.model.dao;

import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;

public class FuncDAO {

    public FuncDAO() {
    }

    public boolean hasElements(){
        FuncBean funcBean = new FuncBean();
        return funcBean.hasElements();
    }

    private List funcList(Long matricFunc){
        FuncBean funcBean = new FuncBean();
        return funcBean.get("matricFunc", matricFunc);
    }

    public boolean verFunc(Long matricFunc){
        List funcList = funcList(matricFunc);
        boolean ret = funcList.size() > 0;
        funcList.clear();
        return ret;
    }

    public FuncBean getFunc(Long matricFunc){
        List funcList = funcList(matricFunc);
        FuncBean funcBean = (FuncBean) funcList.get(0);
        funcList.clear();
        return funcBean;
    }

}
