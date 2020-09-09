package br.com.usinasantafe.pci.model.dao;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.util.Tempo;

public class CabecDAO {

    public CabecDAO() {
    }

    public void salvarCabecAberto(CabecBean cabecBean, OSBean osBean){
        cabecBean.setIdExtCabec(0L);
        cabecBean.setIdOSCabec(osBean.getIdOS());
        cabecBean.setDataCabec(Tempo.getInstance().dataCHora());
        cabecBean.setStatusCabec(1L);
        cabecBean.setStatusApontCabec(1L);
        cabecBean.insert();
    }

    public void updStatusFechado(CabecBean cabecBean){
        cabecBean.setStatusCabec(2L);
        cabecBean.update();
    }

    public void updStatusApont(OSBean osBean){
        List<CabecBean> cabecList = cabecAbertoList();
        for(CabecBean cabecBean : cabecList){
            if(osBean.getIdOS() == cabecBean.getIdOSCabec()){
                cabecBean.setStatusApontCabec(1L);
            }
            else{
                cabecBean.setStatusApontCabec(0L);
            }
            cabecBean.update();
        }
    }

    public void deleteCabec(CabecBean cabecBean){
        cabecBean.delete();
    }

    public CabecBean getCabecApont(){
        List<CabecBean> cabecList = cabecApontList();
        CabecBean cabecBean = cabecList.get(0);
        cabecList.clear();
        return cabecBean;
    }

    public boolean verCabecAberto(){
        List<CabecBean> cabecList = cabecAbertoList();
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public boolean verCabecAbertoOS(OSBean osBean){
        List<CabecBean> cabecList = cabecAbertoOSList(osBean);
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public boolean verCabecFechado(){
        List<CabecBean> cabecList = cabecFechadoList();
        boolean ret = cabecList.size() > 0;
        cabecList.clear();
        return ret;
    }

    public List<CabecBean> cabecApontList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusApontCabec", 1L);
    }

    public List<CabecBean> cabecAbertoList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusCabec", 1L);
    }

    public List<CabecBean> cabecFechadoList(){
        CabecBean cabecBean = new CabecBean();
        return cabecBean.get("statusCabec", 2L);
    }

    public List<CabecBean> cabecAbertoOSList(OSBean osBean){

        ArrayList pesqArrayList = new ArrayList();
        pesqArrayList.add(getPesqCabecAberto());
        pesqArrayList.add(getPesqOS(osBean));

        CabecBean cabecBean = new CabecBean();
        return cabecBean.get(pesqArrayList);

    }

    public ArrayList<Long> deleteCabec(){
        ArrayList<Long> idCabecList = new ArrayList<>();
        if(verCabecFechado()){
            List cabecList = cabecFechadoList();
            for(int i = 0; i < cabecList.size(); i++){
                CabecBean cabecBean = (CabecBean) cabecList.get(i);
                if(!cabecBean.getDataCabec().substring(0,10).equals(Tempo.getInstance().dataSHora())){
                    idCabecList.add(cabecBean.getIdCabec());
                    cabecBean.delete();
                }
            }
        }
        return idCabecList;
    }

    private EspecificaPesquisa getPesqCabecAberto(){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("statusCabec");
        pesquisa.setValor(1L);
        pesquisa.setTipo(1);
        return pesquisa;
    }

    private EspecificaPesquisa getPesqOS(OSBean osBean){
        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idOSCabec");
        pesquisa.setValor(osBean.getIdOS());
        pesquisa.setTipo(1);
        return pesquisa;
    }

}
