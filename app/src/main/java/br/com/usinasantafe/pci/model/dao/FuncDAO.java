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

    private List matricFuncList(Long matricFunc){
        FuncBean funcBean = new FuncBean();
        return funcBean.get("matricFunc", matricFunc);
    }

    private List idFuncList(Long idFunc){
        FuncBean funcBean = new FuncBean();
        return funcBean.get("idFunc", idFunc);
    }

    public boolean verMatricFunc(Long matricFunc){
        List funcList = matricFuncList(matricFunc);
        boolean ret = funcList.size() > 0;
        funcList.clear();
        return ret;
    }

    public FuncBean getMatricFunc(Long matricFunc){
        List funcList = matricFuncList(matricFunc);
        FuncBean funcBean = (FuncBean) funcList.get(0);
        funcList.clear();
        return funcBean;
    }

    public FuncBean getIdFunc(Long idFunc){
        List funcList = idFuncList(idFunc);
        FuncBean funcBean = (FuncBean) funcList.get(0);
        funcList.clear();
        return funcBean;
    }

}
