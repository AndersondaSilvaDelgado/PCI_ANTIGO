package br.com.usinasantafe.pci.util;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;

import java.util.HashMap;
import java.util.Map;

import br.com.usinasantafe.pci.control.CheckListCTR;
import br.com.usinasantafe.pci.util.conHttp.PostCadGenerico;
import br.com.usinasantafe.pci.util.conHttp.UrlsConexaoHttp;

public class EnvioDadosServ {

	private static EnvioDadosServ instance = null;
	private Context telaAtual;
	private Class telaProx;
	private ProgressDialog progressDialog;
	
	public EnvioDadosServ() {
	}

    public static EnvioDadosServ getInstance() {
        if (instance == null){
        	instance = new EnvioDadosServ();
        }
        return instance;
    }

	public void enviarDados() {

		ConexaoWeb conexaoWeb = new ConexaoWeb();
		if (conexaoWeb.verificaConexao(this.telaAtual)) {

			CheckListCTR checkListCTR = new CheckListCTR();
			String dados = checkListCTR.dadosEnvio();

			Log.i("PMM", "CHECKLIST = " + dados);

			UrlsConexaoHttp urlsConexaoHttp = new UrlsConexaoHttp();

			String[] url = {urlsConexaoHttp.getsInsertChecklist()};
			Map<String, Object> parametrosPost = new HashMap<String, Object>();
			parametrosPost.put("dado", dados);

			PostCadGenerico conHttpPostGenerico = new PostCadGenerico();
			conHttpPostGenerico.setParametrosPost(parametrosPost);
			conHttpPostGenerico.execute(url);
		}
		else{
			falhaEnvio();
		}

	}

    public void envioDadosPrinc(Context telaAtual, Class telaProx, ProgressDialog progressDialog) {
        this.telaAtual = telaAtual;
        this.telaProx = telaProx;
        this.progressDialog = progressDialog;
		enviarDados();
    }

	public void recDados(String result){
		CheckListCTR checkListCTR = new CheckListCTR();
		checkListCTR.recDados(result);
		msgEnvio();
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
		AlertDialog.Builder alerta = new AlertDialog.Builder(this.telaAtual);
		alerta.setTitle("ATENCAO");
		alerta.setMessage("FALHA NO ENVIO DE DADOS! POR FAVOR, TENTE REENVIAR NOVAMENTE OS DADOS.");
		alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
			@Override
			public void onClick(DialogInterface dialog, int which) {
				Intent it = new Intent(telaAtual, telaProx);
				telaAtual.startActivity(it);
			}
		});

		alerta.show();

	}

}
