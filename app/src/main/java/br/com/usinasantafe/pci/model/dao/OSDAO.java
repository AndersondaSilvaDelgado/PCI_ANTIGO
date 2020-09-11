package br.com.usinasantafe.pci.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.OSBaseBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.OSVarBean;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class OSDAO {

    public OSDAO() {
    }

    public OSBaseBean getOS(Long idOS){
        List<OSBaseBean> osList = osBaseList(idOS);
        OSBaseBean osBaseBean = osList.get(0);
        osList.clear();
        return osBaseBean;
    }

    public List<OSBaseBean> osBaseList(Long idOS){
        OSBaseBean osBaseBean = new OSBaseBean();
        return osBaseBean.get("idOS", idOS);
    }

    public List<OSBaseBean> osBaseList(){
        OSBaseBean osBaseBean = new OSBaseBean();
        return osBaseBean.all();
    }

    public List<OSVarBean> osVarList(){
        OSVarBean osVarBean = new OSVarBean();
        return osVarBean.all();
    }

    public List<OSVarBean> osVarList(Long idFunc){
        OSVarBean osVarBean = new OSVarBean();
        return osVarBean.get("idFunc", idFunc);
    }

    public ArrayList<Long> idPlantaOSList(){
        List osList = osBaseList();
        ArrayList<Long> idPlantaList = new ArrayList<>();
        for(int i = 0; i < osList.size(); i++){
            OSBaseBean osBaseBean = (OSBaseBean) osList.get(i);
            idPlantaList.add(osBaseBean.getIdPlantaOS());
        }
        osList.clear();
        return idPlantaList;
    }

    public void insertOSFeita(CabecBean cabecBean) {
        OSVarBean osVarBean = new OSVarBean();
        osVarBean.setIdFunc(cabecBean.getIdFuncCabec());
        List<OSBaseBean> osBaseList = osBaseList(cabecBean.getIdOSCabec());
        OSBaseBean osBaseBean = osBaseList.get(0);
        osBaseList.clear();
        osVarBean.setNroOS(osBaseBean.getNroOS());
        osVarBean.setData(Tempo.getInstance().dataSHora());
        osVarBean.insert();
    }

    public void deleteOSFeita(){
        OSVarBean osVarBean = new OSVarBean();
        List osFeitaList = osVarBean.all();
        for(int i = 0; i < osFeitaList.size(); i++){
            osVarBean = (OSVarBean) osFeitaList.get(i);
            if(!osVarBean.getData().equals(Tempo.getInstance().dataSHora())){
                osVarBean.delete();
            }
        }
    }

    public boolean verOSFunc(Long idFunc){
       List osList = osVarList(idFunc);
       boolean ret = (osList.size() > 0);
       osList.clear();
       return ret;
    }

    public void verOS(String dado, Context telaAtual, Class telaProx, ProgressDialog progressDialog){
        VerifDadosServ.getInstance().verDados(dado, "OS", telaAtual, telaProx, progressDialog);
    }

    public void recDadosOS(String result){

        try {

            if (!result.contains("exceeded")) {

                JSONObject jObj = new JSONObject(result);
                JSONArray jsonArray = jObj.getJSONArray("dados");

                if (jsonArray.length() > 0) {

                    OSBaseBean osBaseBean = new OSBaseBean();
                    osBaseBean.deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osBaseBean = gson.fromJson(objeto.toString(), OSBaseBean.class);
                        osBaseBean.insert();

                    }

                    VerifDadosServ.getInstance().pulaTelaComTerm();

                } else {
                    VerifDadosServ.getInstance().msgComTerm("NÃO EXISTE O.S. PARA ESSE COLABORADOR! POR FAVOR, ENTRE EM CONTATO COM A AREA QUE CRIA O.S. PARA APONTAMENTO.");
                }

            }
            else{
                VerifDadosServ.getInstance().msgComTerm("EXCEDEU TEMPO LIMITE DE PESQUISA! POR FAVOR, PROCURE UM PONTO MELHOR DE CONEXÃO DOS DADOS.");
            }

        } catch (Exception e) {
            VerifDadosServ.getInstance().msgComTerm("FALHA DE PESQUISA DE OS! POR FAVOR, TENTAR NOVAMENTE COM UM SINAL MELHOR.");
        }

    }

}
