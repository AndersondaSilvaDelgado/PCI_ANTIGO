package br.com.usinasantafe.pci.model.dao;

import android.app.ProgressDialog;
import android.content.Context;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.variavel.OSBean;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class OSDAO {

    public OSDAO() {
    }

    public br.com.usinasantafe.pci.model.bean.estatica.OSBean getOS(Long idOS){
        List<br.com.usinasantafe.pci.model.bean.estatica.OSBean> osList = osBaseList(idOS);
        br.com.usinasantafe.pci.model.bean.estatica.OSBean osBean = osList.get(0);
        osList.clear();
        return osBean;
    }

    public List<br.com.usinasantafe.pci.model.bean.estatica.OSBean> osBaseList(Long idOS){
        br.com.usinasantafe.pci.model.bean.estatica.OSBean osBean = new br.com.usinasantafe.pci.model.bean.estatica.OSBean();
        return osBean.get("idOS", idOS);
    }

    public List<br.com.usinasantafe.pci.model.bean.estatica.OSBean> osBaseList(){
        br.com.usinasantafe.pci.model.bean.estatica.OSBean osBean = new br.com.usinasantafe.pci.model.bean.estatica.OSBean();
        return osBean.all();
    }

    public List<br.com.usinasantafe.pci.model.bean.variavel.OSBean> osVarList(){
        br.com.usinasantafe.pci.model.bean.variavel.OSBean osBean = new br.com.usinasantafe.pci.model.bean.variavel.OSBean();
        return osBean.all();
    }

    public ArrayList<Long> idPlantaOSList(){
        List osList = osBaseList();
        ArrayList<Long> idPlantaList = new ArrayList<>();
        for(int i = 0; i < osList.size(); i++){
            br.com.usinasantafe.pci.model.bean.estatica.OSBean osBean = (br.com.usinasantafe.pci.model.bean.estatica.OSBean) osList.get(i);
            idPlantaList.add(osBean.getIdPlantaOS());
        }
        osList.clear();
        return idPlantaList;
    }

    public void deleteOSFeita(){
        OSBean osBean = new OSBean();
        List osFeitaList = osBean.all();
        for(int i = 0; i < osFeitaList.size(); i++){
            osBean = (OSBean) osFeitaList.get(i);
            if(!osBean.getDataReal().equals(Tempo.getInstance().dataSHora())){
                osBean.delete();
            }
        }
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

                    br.com.usinasantafe.pci.model.bean.estatica.OSBean osBean = new br.com.usinasantafe.pci.model.bean.estatica.OSBean();
                    osBean.deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osBean = gson.fromJson(objeto.toString(), br.com.usinasantafe.pci.model.bean.estatica.OSBean.class);
                        osBean.insert();

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
