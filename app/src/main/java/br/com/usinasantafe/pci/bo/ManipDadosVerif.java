package br.com.usinasantafe.pci.bo;

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
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pci.ListaOSActivity;
import br.com.usinasantafe.pci.PrincipalActivity;
import br.com.usinasantafe.pci.conWEB.ConHttpPostVerGenerico;
import br.com.usinasantafe.pci.conWEB.UrlsConexaoHttp;
import br.com.usinasantafe.pci.pst.GenericRecordable;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.estatica.OSTO;
import br.com.usinasantafe.pci.to.variavel.AtualizaTO;
import br.com.usinasantafe.pci.to.variavel.PlantaCabecTO;

/**
 * Created by anderson on 16/11/2015.
 */
public class ManipDadosVerif {

    private static ManipDadosVerif instance = null;
    private GenericRecordable genericRecordable;
    private UrlsConexaoHttp urlsConexaoHttp;
    private ProgressDialog progressDialog;
    private Context telaAtual;
    private Class telaProx;
    private boolean verTerm;
    private String dado;
    private String tipo;
    private ConHttpPostVerGenerico conHttpPostVerGenerico;
    private AtualizaTO atualizaTO;
    private PrincipalActivity principalActivity;

    public ManipDadosVerif() {
        //genericRecordable = new GenericRecordable();
    }

    public static ManipDadosVerif getInstance() {
        if (instance == null)
            instance = new ManipDadosVerif();
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

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "OSTO");

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
                        alerta.setMessage("NÃO EXISTE O.S. PARA ESSE COLABORADOR! POR FAVOR, ENTRE EM CONTATO COM A AREA QUE CRIA O.S. PARA APONTAMENTO.");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub

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
                    alerta.setMessage("CONEXÃO ESTA MUITO LENTA! POR FAVOR, INSIRA NOVAMENTE A O.S. NUM PONTO COM MAIS SINAL.");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    alerta.show();

                }

            }
            else if(this.tipo.equals("Item")) {

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "ItemTO");

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
                                // TODO Auto-generated method stub

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
                            // TODO Auto-generated method stub

                        }
                    });
                    alerta.show();

                }

            }
            else if(this.tipo.equals("Planta")) {

                if (!result.contains("exceeded")) {

                    JSONObject jObj = new JSONObject(result);
                    JSONArray jsonArray = jObj.getJSONArray("dados");
                    Class classe = Class.forName(urlsConexaoHttp.localPSTEstatica + "PlantaTO");

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
                    atualizarAplicativo.setContext(this.principalActivity);
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
            // TODO Auto-generated catch block
            Log.i("PMM", "Erro Manip atualizar = " + e);
        }

    }

    public String manipLocalClasse(String classe){
        if(classe.contains("TO")){
            classe = urlsConexaoHttp.localPSTEstatica + classe;
        }
        return classe;
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

    public void verAtualizacao(AtualizaTO atualizaTO, PrincipalActivity principalActivity, ProgressDialog progressDialog) {

        urlsConexaoHttp = new UrlsConexaoHttp();
        this.atualizaTO = atualizaTO;
        this.progressDialog = progressDialog;
        this.tipo = "Atualiza";
        this.principalActivity = principalActivity;

        envioAtualizacao();

    }

    public void envioAtualizacao(){

        JsonArray jsonArray = new JsonArray();

        Gson gson = new Gson();
        jsonArray.add(gson.toJsonTree(atualizaTO, atualizaTO.getClass()));

        JsonObject json = new JsonObject();
        json.add("dados", jsonArray);

        Log.i("PCI", "LISTA = " + json.toString());

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", json.toString());

        ConHttpPostVerGenerico conHttpPostVerGenerico = new ConHttpPostVerGenerico();
        conHttpPostVerGenerico.setParametrosPost(parametrosPost);
        conHttpPostVerGenerico.execute(url);

    }

    public void envioDados() {

        String[] url = {urlsConexaoHttp.urlVerifica(tipo)};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", String.valueOf(dado));

        Log.i("PMM", "VERIFICA = " + String.valueOf(dado));

        conHttpPostVerGenerico = new ConHttpPostVerGenerico();
        conHttpPostVerGenerico.setParametrosPost(parametrosPost);
        conHttpPostVerGenerico.execute(url);

    }


    public void setTelaAtual(Context telaAtual) {
        this.telaAtual = telaAtual;
    }

    public void setProgressDialog(ProgressDialog progressDialog) {
        this.progressDialog = progressDialog;
    }
}
