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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import br.com.usinasantafe.pci.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pci.util.conHttp.UrlsConexaoHttp;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class EnvioDadosServ {

	private static EnvioDadosServ instance = null;
	private Context telaAtual;
	private Class telaProx;
	private ProgressDialog progressDialog;
	private int tipoEnvio;
	private PostCadGenerico postCadGenerico;
	
	public EnvioDadosServ() {

	}

    public static EnvioDadosServ getInstance() {
        if (instance == null){
        	instance = new EnvioDadosServ();
        }
        return instance;
    }

    public boolean verBolAberto(){

	    boolean retorno = false;
        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 1L);

        for (int i = 0; i < cabecList.size(); i++) {

            cabecTO = (CabecTO) cabecList.get(i);

            PlantaCabecTO plantaCabecTO = new PlantaCabecTO();

            ArrayList plantaPesqList = new ArrayList();
            EspecificaPesquisa pesquisa = new EspecificaPesquisa();
            pesquisa.setCampo("idCabec");
            pesquisa.setValor(cabecTO.getIdCabec());
            plantaPesqList.add(pesquisa);

            EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
            pesquisa2.setCampo("statusPlantaCabec");
            pesquisa2.setValor(3L);
            plantaPesqList.add(pesquisa2);

            List plantaCabecList = plantaCabecTO.get(plantaPesqList);
            if(plantaCabecList.size() > 0){
                retorno = true;
            }

        }

        return retorno;

    }

	public void enviarBolAberto() {

		CabecTO cabecTO = new CabecTO();
		List cabecList = cabecTO.get("statusCabec", 1L);

		JsonArray jsonArrayCabec = new JsonArray();
		JsonArray jsonArrayResp = new JsonArray();

		for (int i = 0; i < cabecList.size(); i++) {

			cabecTO = (CabecTO) cabecList.get(i);
			Gson gsonCabec = new Gson();
			jsonArrayCabec.add(gsonCabec.toJsonTree(cabecTO, cabecTO.getClass()));

			PlantaCabecTO plantaCabecTO = new PlantaCabecTO();

			ArrayList plantaPesqList = new ArrayList();
			EspecificaPesquisa pesquisa = new EspecificaPesquisa();
			pesquisa.setCampo("idCabec");
			pesquisa.setValor(cabecTO.getIdCabec());
			plantaPesqList.add(pesquisa);

			EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
			pesquisa2.setCampo("statusPlantaCabec");
			pesquisa2.setValor(3L);
			plantaPesqList.add(pesquisa2);

			List plantaCabecList = plantaCabecTO.get(plantaPesqList);

			for (int j = 0; j < plantaCabecList.size(); j++) {

				plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
				RespItemTO respItemTO = new RespItemTO();

				ArrayList respItemPesqList = new ArrayList();
				EspecificaPesquisa pesq1 = new EspecificaPesquisa();
				pesq1.setCampo("idCabRespItem");
				pesq1.setValor(cabecTO.getIdCabec());
				respItemPesqList.add(pesq1);

				EspecificaPesquisa pesq2 = new EspecificaPesquisa();
				pesq2.setCampo("idPlantaItem");
				pesq2.setValor(plantaCabecTO.getIdPlanta());
				respItemPesqList.add(pesq2);

				List respItemList = respItemTO.get(respItemPesqList);

				for (int l = 0; l < respItemList.size(); l++) {
					respItemTO = (RespItemTO) respItemList.get(l);
					Gson gsonItem = new Gson();
					jsonArrayResp.add(gsonItem.toJsonTree(respItemTO, respItemTO.getClass()));
				}


			}

		}

		JsonObject jsonCabec = new JsonObject();
		jsonCabec.add("cabecalho", jsonArrayCabec);

		JsonObject jsonResp = new JsonObject();
		jsonResp.add("item", jsonArrayResp);

		String dados = jsonCabec.toString() + "_" + jsonResp.toString();

		Log.i("PMM", "ABERTO = " + dados);

		UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

		String[] url = {urlsConexaoHttp.getsApontChecklist()};
		Map<String, Object> parametrosPost = new HashMap<String, Object>();
		parametrosPost.put("dado", dados);

		postCadGenerico = new PostCadGenerico();
		postCadGenerico.setParametrosPost(parametrosPost);
		postCadGenerico.execute(url);

	}

	public void atualPlantaEnviada(){

		CabecTO cabecTO = new CabecTO();
		List cabecList = cabecTO.get("statusCabec", 1L);

		for (int i = 0; i < cabecList.size(); i++) {

			cabecTO = (CabecTO) cabecList.get(i);
			PlantaCabecTO plantaCabecTO = new PlantaCabecTO();

			ArrayList plantaPesqList = new ArrayList();
			EspecificaPesquisa pesquisa = new EspecificaPesquisa();
			pesquisa.setCampo("idCabec");
			pesquisa.setValor(cabecTO.getIdCabec());
			plantaPesqList.add(pesquisa);

			EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
			pesquisa2.setCampo("statusPlantaCabec");
			pesquisa2.setValor(3L);
			plantaPesqList.add(pesquisa2);

			List plantaCabecList = plantaCabecTO.get(plantaPesqList);

			for (int j = 0; j < plantaCabecList.size(); j++) {
				plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
				plantaCabecTO.setStatusPlantaCabec(4L);
				plantaCabecTO.update();
			}
		}

		msgEnvio();

	}

	public boolean verBolFechado(){
        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 2L);
        if(cabecList.size() == 0){
            return false;
        }
        else{
            return true;
        }
    }

    public void enviarBolFechado() {

		// EXCLUIR CABECALHOS ABERTOS SEM OS

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 2L);

        JsonArray jsonArrayCabec = new JsonArray();
        JsonArray jsonArrayResp = new JsonArray();

        for (int i = 0; i < cabecList.size(); i++) {

            cabecTO = (CabecTO) cabecList.get(i);
            Gson gsonCabec = new Gson();
            jsonArrayCabec.add(gsonCabec.toJsonTree(cabecTO, cabecTO.getClass()));

			cabecTO = (CabecTO) cabecList.get(i);
			PlantaCabecTO plantaCabecTO = new PlantaCabecTO();

			ArrayList plantaPesqList = new ArrayList();
			EspecificaPesquisa pesquisa = new EspecificaPesquisa();
			pesquisa.setCampo("idCabec");
			pesquisa.setValor(cabecTO.getIdCabec());
			plantaPesqList.add(pesquisa);

			EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
			pesquisa2.setCampo("statusPlantaCabec");
			pesquisa2.setValor(3L);
			plantaPesqList.add(pesquisa2);

			List plantaCabecList = plantaCabecTO.get(plantaPesqList);

            for (int j = 0; j < plantaCabecList.size(); j++) {

                plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
                RespItemTO respItemTO = new RespItemTO();

                ArrayList respItemPesqList = new ArrayList();
                EspecificaPesquisa pesq1 = new EspecificaPesquisa();
                pesq1.setCampo("idCabRespItem");
                pesq1.setValor(cabecTO.getIdCabec());
                respItemPesqList.add(pesq1);

                EspecificaPesquisa pesq2 = new EspecificaPesquisa();
                pesq2.setCampo("idPlantaItem");
                pesq2.setValor(plantaCabecTO.getIdPlanta());
                respItemPesqList.add(pesq2);

                List respItemList = respItemTO.get(respItemPesqList);

                for (int l = 0; l < respItemList.size(); l++) {
                    respItemTO = (RespItemTO) respItemList.get(l);
                    Gson gsonItem = new Gson();
                    jsonArrayResp.add(gsonItem.toJsonTree(respItemTO, respItemTO.getClass()));
                }


            }

        }

        JsonObject jsonCabec = new JsonObject();
        jsonCabec.add("cabecalho", jsonArrayCabec);

        JsonObject jsonResp = new JsonObject();
        jsonResp.add("item", jsonArrayResp);

        String dados = jsonCabec.toString() + "_" + jsonResp.toString();

        Log.i("PMM", "FECHADO = " + dados);

        UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

        String[] url = {urlsConexaoHttp.getsApontChecklist()};
        Map<String, Object> parametrosPost = new HashMap<String, Object>();
        parametrosPost.put("dado", dados);

        postCadGenerico = new PostCadGenerico();
        postCadGenerico.setParametrosPost(parametrosPost);
        postCadGenerico.execute(url);

    }

    public void atualOSTermEnviada(){

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 2L);

		for (int i = 0; i < cabecList.size(); i++) {
			cabecTO = (CabecTO) cabecList.get(i);
			PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
			List plantaCabecList = plantaCabecTO.get("idCabec", cabecTO.getIdCabec());
			for (int j = 0; j < plantaCabecList.size(); j++) {
				plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
				plantaCabecTO.delete();
			}

			RespItemTO respItemTO = new RespItemTO();
			List respItemList = respItemTO.get("idCabRespItem", cabecTO.getIdCabec());
			for (int j = 0; j < respItemList.size(); j++) {
				respItemTO = (RespItemTO) respItemList.get(j);
				respItemTO.delete();
			}

		}

        for (int i = 0; i < cabecList.size(); i++) {
			cabecTO = (CabecTO) cabecList.get(i);
			cabecTO.delete();
        }

        if (verBolAberto()) {
            enviarBolAberto();
        }
        else {
			msgEnvio();
        }

    }


    public void envioDadosPrinc(Context telaAtual, Class telaProx, ProgressDialog progressDialog) {
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
        if (verBolFechado()) {
            enviarBolFechado();
        } else {
            if (verBolAberto()) {
                enviarBolAberto();
            }
        }

    }

    public void msgEnvio(){

		progressDialog.dismiss();

		AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
		alerta.setTitle("ATENCAO");
		alerta.setMessage("ENVIADO DADOS COM SUCESSO.");
		alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(telaAtual, telaProx);
				telaAtual.startActivity(it);
			}
		});

		alerta.show();
	}

	public void falhaEnvio() {

		progressDialog.dismiss();
		Intent it = new Intent(telaAtual, telaProx);
		telaAtual.startActivity(it);

	}

}
