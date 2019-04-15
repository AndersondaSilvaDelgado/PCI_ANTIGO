package br.com.usinasantafe.pci;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.j256.ormlite.field.DatabaseField;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.bo.ConexaoWeb;
import br.com.usinasantafe.pci.bo.ManipDadosEnvio;
import br.com.usinasantafe.pci.bo.ManipDadosVerif;
import br.com.usinasantafe.pci.bo.Tempo;
import br.com.usinasantafe.pci.to.estatica.FuncTO;
import br.com.usinasantafe.pci.to.variavel.AtualizaTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.ConfiguracaoTO;
import br.com.usinasantafe.pci.to.variavel.OSFeitaTO;
import br.com.usinasantafe.pci.to.variavel.PlantaCabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

public class PrincipalActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        pciContext = (PCIContext) getApplication();

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        teste();


        progressBar = new ProgressDialog(this);

        ConexaoWeb conexaoWeb = new ConexaoWeb();
        if(conexaoWeb.verificaConexao(this))
        {

            progressBar.setCancelable(true);
            progressBar.setMessage("Buscando Atualização...");
            progressBar.show();

            ConfiguracaoTO configuracaoTO = new ConfiguracaoTO();
            List configList = configuracaoTO.all();
            if(configList.size() > 0){
                configuracaoTO = (ConfiguracaoTO) configList.get(0);
                AtualizaTO atualizaTO = new AtualizaTO();
                atualizaTO.setIdCelularAtual(configuracaoTO.getNumLinha());
                atualizaTO.setVersaoAtual(pciContext.versaoAplic);
                ManipDadosVerif.getInstance().verAtualizacao(atualizaTO, this, progressBar);
            }
            else{
                progressBar.dismiss();
            }

        }

        OSFeitaTO osFeitaTO = new OSFeitaTO();
        List osFeitaList = osFeitaTO.all();
        for(int i = 0; i < osFeitaList.size(); i++){
            osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
            if(!osFeitaTO.getDataReal().equals(Tempo.getInstance().dataSHora())){
                osFeitaTO.delete();
            }
        }

        CabecTO cabecVerTO = new CabecTO();
        List listCabecVer = cabecVerTO.get("statusCabec", 3L);
        if(listCabecVer.size() > 0) {
            for(int i = 0; i < listCabecVer.size(); i++){
                cabecVerTO = (CabecTO) listCabecVer.get(i);
                if(!cabecVerTO.getDataCabec().substring(0,10).equals(Tempo.getInstance().dataSHora())){
                    RespItemTO respItemTO = new RespItemTO();
                    List listaItem = respItemTO.get("idCabRespItem", cabecVerTO.getIdCabec());
                    for(int j = 0; j < listaItem.size(); j++){
                        respItemTO = (RespItemTO) listaItem.get(j);
                        respItemTO.delete();
                    }
                    cabecVerTO.delete();

                }
            }
        }
        listCabecVer.clear();

        if(verEnvio()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(PrincipalActivity.this);
            alerta.setTitle("ATENCAO");
            alerta.setMessage("EXISTE DADOS PARA SERENS ENVIADOS. POR FAVOR, REENVIOS OS DADOS.");
            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                }
            });
            alerta.show();
        }
        listarMenuInicial();

    }

    public void listarMenuInicial() {

        ArrayList<String> itens = new ArrayList<String>();
        itens.add("CHECKLIST");
        itens.add("REENVIO DE DADOS");
        itens.add("CONFIGURAÇÃO");
        itens.add("RELAÇÃO DE OS");
        itens.add("SAIR");

        AdapterList adapterList = new AdapterList(this, itens);
        ListView listView = (ListView) findViewById(R.id.listaMenuInicial);
        listView.setAdapter(adapterList);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                // TODO Auto-generated method stub
                if (position == 0) {

                    FuncTO funcTO = new FuncTO();

                    if(funcTO.hasElements()) {

                        Intent it = new Intent(PrincipalActivity.this, FuncionarioActivity.class);
                        startActivity(it);

                    }

                } else if (position == 1) {

                    Intent it = new Intent(PrincipalActivity.this, EnvioDadosActivity.class);
                    startActivity(it);

                } else if (position == 2) {

                    Intent it = new Intent(PrincipalActivity.this, ConfiguracaoActivity.class);
                    startActivity(it);

                } else if (position == 3) {

                    Intent it = new Intent(PrincipalActivity.this, FuncVerOSActivity.class);
                    startActivity(it);

                } else if (position == 4) {

                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);

                }

            }

        });

    }

    public boolean checkPermission(String permission){
        int check = ContextCompat.checkSelfPermission(this, permission);
        return (check == PackageManager.PERMISSION_GRANTED);
    }

    public void onBackPressed()  {
    }

    public boolean verEnvio() {

        if (!ManipDadosEnvio.getInstance().verBolFechado()
                && !ManipDadosEnvio.getInstance().verBolAberto()) {
            return false;
        } else {
            return true;
        }

    }

    public void teste(){

        Log.i("PCI", "AKI");

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.all();

        for (int i = 0; i < cabecList.size(); i++) {

            cabecTO = (CabecTO) cabecList.get(i);
            Log.i("PCI", "CABEC");
            Log.i("PCI", "idCabec = " + cabecTO.getIdCabec());
            Log.i("PCI", "idExtCabec = " + cabecTO.getIdExtCabec());
            Log.i("PCI", "osCabec = " + cabecTO.getOsCabec());
            Log.i("PCI", "idFuncCabec = " + cabecTO.getIdFuncCabec());
            Log.i("PCI", "dataCabec = " + cabecTO.getDataCabec());
            Log.i("PCI", "statusCabec = " + cabecTO.getStatusCabec());
            Log.i("PCI", "nroOSCabec = " + cabecTO.getNroOSCabec());
            Log.i("PCI", "qtdeDiaOS = " + cabecTO.getQtdeDiaOS());
            Log.i("PCI", "verApontCab = " + cabecTO.getVerApontCab());


        }

        PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
        List plantaCabecList = plantaCabecTO.all();

        for (int i = 0; i < plantaCabecList.size(); i++) {

            plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
            Log.i("PCI", "PLANTA CABEC");
            Log.i("PCI", "idPlantaCabec= " + plantaCabecTO.getIdPlantaCabec());
            Log.i("PCI", "idPlanta = " + plantaCabecTO.getIdPlanta());
            Log.i("PCI", "idCabec = " + plantaCabecTO.getIdCabec());
            Log.i("PCI", "statusPlantaCabec = " + plantaCabecTO.getStatusPlantaCabec());
            Log.i("PCI", "verApontaPlanta = " + plantaCabecTO.getVerApontaPlanta());

        }

        RespItemTO respItemTO = new RespItemTO();
        List respItemList = respItemTO.all();

        for (int i = 0; i < respItemList.size(); i++) {

            respItemTO = (RespItemTO) respItemList.get(i);
            Log.i("PCI", "RESP ITEM");
            Log.i("PCI", "idRespItem = " + respItemTO.getIdRespItem());
            Log.i("PCI", "idCabRespItem = " + respItemTO.getIdCabRespItem());
            Log.i("PCI", "idItOsMecanRespItem = " + respItemTO.getIdItOsMecanRespItem());
            Log.i("PCI", "opcaoRespItem = " + respItemTO.getOpcaoRespItem());
            Log.i("PCI", "obsRespItem = " + respItemTO.getObsRespItem());
            Log.i("PCI", "idPlantaItem = " + respItemTO.getIdPlantaItem());
            Log.i("PCI", "dthrRespItem = " + respItemTO.getDthrRespItem());

        }

        OSFeitaTO osFeitaTO = new OSFeitaTO();
        List osFeitaList = osFeitaTO.all();

        for(int i = 0; i < osFeitaList.size(); i++){

            osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
            Log.i("PCI", "OS FEITA");
            Log.i("PCI", "nroOS = " + osFeitaTO.getNroOS());
            Log.i("PCI", "dataReal = " + osFeitaTO.getDataReal());

        }


    }

}
