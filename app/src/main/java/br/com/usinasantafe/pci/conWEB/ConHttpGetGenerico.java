package br.com.usinasantafe.pci.conWEB;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Field;
import java.net.HttpURLConnection;
import java.net.URL;

import br.com.usinasantafe.pci.bo.ManipDadosReceb;


public class ConHttpGetGenerico extends AsyncTask<String, Void, String> {

	private static ConHttpGetGenerico instance = null;
	private String tipo = null;
	
	private UrlsConexaoHttp urlsConexaoHttp;


	public ConHttpGetGenerico() {
		// TODO Auto-generated constructor stub
		urlsConexaoHttp = new UrlsConexaoHttp();
	}
	
    public static ConHttpGetGenerico getInstance() {
        if (instance == null)
        instance = new ConHttpGetGenerico();
        return instance;
    }


	@Override
	protected String doInBackground(String... arg) {
		// TODO Auto-generated method stub
		
		String resultado = "";
		BufferedReader bufferedReader = null;
		
		tipo = arg[0];
		String url = "";
		
		try {
			
			Object o = new Object();
            Class<?> retClasse = Class.forName(urlsConexaoHttp.localUrl); 
			
            for (Field field : retClasse.getDeclaredFields()) {
                String campo = field.getName();
                if(campo.equals(tipo)){
                	url = "" + retClasse.getField(campo).get(o);
               }
                
            }
            
            Log.i("ERRO", "Chegou aki = " + url);

			URL urlCon = new URL(url);
			HttpURLConnection connection = (HttpURLConnection) urlCon.openConnection();
			connection.setRequestMethod("GET");
			connection.setDoInput(true);
			connection.setDoOutput(false);
			connection.connect();

			bufferedReader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
			StringBuffer stringBuffer = new StringBuffer("");
			String line = "";
			String LS = System.getProperty("line.separator");
			while((line = bufferedReader.readLine()) != null){
				stringBuffer.append(line + LS);
			}
			bufferedReader.close();
			resultado = stringBuffer.toString();
            
		} catch (Exception e) {
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception erro) {
					
				}
			}
		}
		finally{
			
			if(bufferedReader != null){
				try {
					bufferedReader.close();
				} catch (Exception e) {
					
				}
			}
			
		}
		
		return resultado;
	}
	
	protected void onPostExecute(String result) {

		try {
			
			ManipDadosReceb.getInstance().manipularDadosHttp(tipo, result);
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			Log.i("ECM", "Erro2 = " + e);
			ManipDadosReceb.getInstance().encerrar();
		}
		

		
    }

}
