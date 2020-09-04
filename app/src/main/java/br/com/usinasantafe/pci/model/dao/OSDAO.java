package br.com.usinasantafe.pci.model.dao;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.control.ConfigCTR;
import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.OSFeitaBean;
import br.com.usinasantafe.pci.model.pst.GenericRecordable;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class OSDAO {

    public OSDAO() {
    }

    public List osList(){
        OSBean osBean = new OSBean();
        return osBean.all();
    }

    public ArrayList<Long> idPlantaOSList(){
        List osList = osList();
        ArrayList<Long> idPlantaList = new ArrayList<>();
        for(int i = 0; i < osList.size(); i++){
            OSBean osBean = (OSBean) osList.get(i);
            idPlantaList.add(osBean.getIdPlantaOS());
        }
        osList.clear();
        return idPlantaList;
    }

    public void deleteOSFeita(){
        OSFeitaBean osFeitaBean = new OSFeitaBean();
        List osFeitaList = osFeitaBean.all();
        for(int i = 0; i < osFeitaList.size(); i++){
            osFeitaBean = (OSFeitaBean) osFeitaList.get(i);
            if(!osFeitaBean.getDataReal().equals(Tempo.getInstance().dataSHora())){
                osFeitaBean.delete();
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

                    OSBean osBean = new OSBean();
                    osBean.deleteAll();

                    for (int i = 0; i < jsonArray.length(); i++) {

                        JSONObject objeto = jsonArray.getJSONObject(i);
                        Gson gson = new Gson();
                        osBean = gson.fromJson(objeto.toString(), OSBean.class);
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
