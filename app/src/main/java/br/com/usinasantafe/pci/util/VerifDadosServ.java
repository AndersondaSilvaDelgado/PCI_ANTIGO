package br.com.usinasantafe.pci.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pci.view.MenuInicialActivity;
import br.com.usinasantafe.pci.control.ConfigCTR;
import br.com.usinasantafe.pci.model.dao.OSDAO;
import br.com.usinasantafe.pci.util.conHttp.PostVerGenerico;
import br.com.usinasantafe.pci.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pci.model.pst.GenericRecordable;
import br.com.usinasantafe.pci.model.bean.AtualAplicBean;

/**
 * Created by anderson on 16/11/2015.
 */
public class VerifDadosServ {

    private static VerifDadosServ instance = null;
    private GenericRecordable genericRecordable;
    private UrlsConexaoHttp urlsConexaoHttp;
    private ProgressDialog progressDialog;
    private Context telaAtual;
    private Class telaProx;
    private boolean verTerm;
    private String dado;
    private String tipo;
    private PostVerGenerico postVerGenerico;
    private AtualAplicBean atualAplicBean;
    private MenuInicialActivity menuInicialActivity;

    public VerifDadosServ() {
        //genericRecordable = new GenericRecordable();
    }

    public static VerifDadosServ getInstance() {
        if (instance == null)
            instance = new VerifDadosServ();
        return instance;
    }

    public void manipularDadosHttp(String result) {

        if (!result.equals("")) {
            retornoVerifNormal(result);
        }

    }

    public void retornoVerifNormal(String result) {

        try {

            if(this.tipo.equals("OS")) {
                OSDAO osDAO = new OSDAO();
                osDAO.recDadosOS(result);
            }
            else if(this.tipo.equals("Item")) {

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "ItemBean");

                    if (jsonArray.length() > 0) {

                        genericRecordable = new GenericRecordable();
                        genericRecordable.deleteAll(classe);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject objeto = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);

                        }

                        this.progressDialog.dismiss();
                        Intent it = new Intent(telaAtual, telaProx);
                        telaAtual.startActivity(it);

                    } else {

                        verTerm = true;
                        this.progressDialog.dismiss();

                        AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("NÃO EXISTE ITENS PARA ESSA O.S.! POR FAVOR, ENTRE EM CONTATO COM A AREA QUE CRIA O.S. PARA APONTAMENTO.");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });
                        alerta.show();

                    }

                }
                else{

                    verTerm = true;
                    this.progressDialog.dismiss();

                    AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("CONEXÃO ESTA MUITO LENTA! POR FAVOR, SELECIONE NOVAMENTE A O.S. NUM PONTO COM MAIS SINAL.");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });
                    alerta.show();

                }

            }
            else if(this.tipo.equals("Servico")) {

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "ServicoTO");

                    if (jsonArray.length() > 0) {

                        genericRecordable = new GenericRecordable();
                        genericRecordable.deleteAll(classe);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject objeto = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);

                        }

                        this.progressDialog.dismiss();
                        Intent it = new Intent(telaAtual, telaProx);
                        telaAtual.startActivity(it);

                    }

                }
                else{

                    this.progressDialog.dismiss();
                    Intent it = new Intent(telaAtual, telaProx);
                    telaAtual.startActivity(it);

                }

            }
            else if(this.tipo.equals("Atualiza")) {

                String verAtualizacao = result.trim();

                if(verAtualizacao.equals("S")){

                    AtualizarAplicativo atualizarAplicativo = new AtualizarAplicativo();
                    atualizarAplicativo.setContext(this.menuInicialActivity);
                    atualizarAplicativo.execute();

                }
                else{

                    this.progressDialog.dismiss();

                }

            }
            else if(this.tipo.equals("Funcionario")) {

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "FuncTO");

                    if (jsonArray.length() > 0) {

                        genericRecordable = new GenericRecordable();
                        genericRecordable.deleteAll(classe);

                        for (int i = 0; i < jsonArray.length(); i++) {

                            JSONObject objeto = jsonArray.getJSONObject(i);
                            Gson gson = new Gson();
                            genericRecordable.insert(gson.fromJson(objeto.toString(), classe), classe);

                        }

                        this.progressDialog.dismiss();

                    }

                }
                else{

                    this.progressDialog.dismiss();

                }


            }

        } catch (Exception e) {
            Log.i("PMM", "Erro Manip atualizar = " + e);
        }

    }

    public void verDados(String dado, String tipo, Context telaAtual, Class telaProx, ProgressDialog progressDialog) {

        verTerm = false;
        urlsConexaoHttp = new UrlsConexaoHttp();
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        this.dado = dado;
        this.tipo = tipo;

        envioDados();

    }

    public void verAtualAplic(String versaoAplic, MenuInicialActivity menuInicialActivity, ProgressDialog progressDialog) {

        AtualAplicBean atualAplicBean = new AtualAplicBean();
        ConfigCTR configCTR = new ConfigCTR();
        atualAplicBean.setIdCelularAtual(configCTR.getConfig().getNumLinhaConfig());
        atualAplicBean.setVersaoAtual(versaoAplic);

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.progressDialog = progressDialog;
        this.tipo = "Atualiza";
        this.menuInicialActivity = menuInicialActivity;

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualAplicBean, atualAplicBean.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PMM", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.execute(url);

    }

    public void envioDados() {

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", String.valueOf(dado));

        Log.i("PMM", "VERIFICA = " + String.valueOf(dado));

        postVerGenerico = new PostVerGenerico();
        postVerGenerico.setParametrosPost(parametrosPost);
        postVerGenerico.execute(url);

    }

    public void pulaTelaComTerm(){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void msgComTerm(String texto){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            AlertDialog.Builder alerta = new AlertDialog.Builder(telaAtual);
            alerta.setTitle("ATENÇÃO");
            alerta.setMessage(texto);
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                }
            });
            alerta.show();
        }
    }

    public void pulaTelaComTermSemBarra(){
        if(!verTerm){
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void pulaTelaDadosInfor(Class telaProx){
        if(!verTerm){
            this.progressDialog.dismiss();
            this.verTerm = true;
            Intent it = new Intent(telaAtual, telaProx);
            telaAtual.startActivity(it);
        }
    }

    public void setTelaAtual(Context telaAtual) {
        this.telaAtual = telaAtual;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
