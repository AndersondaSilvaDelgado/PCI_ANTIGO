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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.usinasantafe.pci.util.ConexaoWeb;
import br.com.usinasantafe.pci.util.EnvioDadosServ;

public class MenuInicialActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_inicial);

        pciContext = (PCIContext) getApplication();

        if(!checkPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE)){
            String[] PERMISSIONS = {android.Manifest.permission.WRITE_EXTERNAL_STORAGE};
            ActivityCompat.requestPermissions((Activity) this, PERMISSIONS, 112);
        }

        teste();

        ConexaoWeb conexaoWeb = new ConexaoWeb();

        if(conexaoWeb.verificaConexao(this))
        {
            progressBar = new ProgressDialog(this);
            if(pciContext.getConfigCTR().hasElements()){
                progressBar.setCancelable(true);
                progressBar.setMessage("Buscando Atualização...");
                progressBar.show();
                pciContext.getConfigCTR().verAtualAplic(pciContext.versaoAplic, this, progressBar);
            }
            else{
                progressBar.dismiss();
            }

        }

        pciContext.getCheckListCTR().deleteOSFeita();

        pciContext.getCheckListCTR().deleteCabecRespAntiga();

        if(verEnvio()){
            AlertDialog.Builder alerta = new AlertDialog.Builder(MenuInicialActivity.this);
            alerta.setTitle("ATENCAO");
            alerta.setMessage("EXISTE DADOS PARA SERENS ENVIADOS. POR FAVOR, REENVIE OS DADOS.");
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

                if (position == 0) {

                    if(pciContext.getCheckListCTR().hasElementFunc()) {
                        Intent it = new Intent(MenuInicialActivity.this, FuncionarioActivity.class);
                        startActivity(it);
                    }

                } else if (position == 1) {

                    Intent it = new Intent(MenuInicialActivity.this, EnvioDadosActivity.class);
                    startActivity(it);

                } else if (position == 2) {

                    Intent it = new Intent(MenuInicialActivity.this, ConfigActivity.class);
                    startActivity(it);

                } else if (position == 3) {

                    Intent it = new Intent(MenuInicialActivity.this, FuncVerOSActivity.class);
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

        if (!EnvioDadosServ.getInstance().verBolFechado()
                && !EnvioDadosServ.getInstance().verBolAberto()) {
            return false;
        } else {
            return true;
        }

    }

    public void teste(){
//
//        Log.i("PCI", "AKI");
//
//        CabecTO cabecTO = new CabecTO();
//        List cabecList = cabecTO.all();
//
//        for (int i = 0; i < cabecList.size(); i++) {
//
//            cabecTO = (CabecTO) cabecList.get(i);
//            Log.i("PCI", "CABEC");
//            Log.i("PCI", "idCabec = " + cabecTO.getIdCabec());
//            Log.i("PCI", "idExtCabec = " + cabecTO.getIdExtCabec());
//            Log.i("PCI", "osCabec = " + cabecTO.getOsCabec());
//            Log.i("PCI", "idFuncCabec = " + cabecTO.getIdFuncCabec());
//            Log.i("PCI", "dataCabec = " + cabecTO.getDataCabec());
//            Log.i("PCI", "statusCabec = " + cabecTO.getStatusCabec());
//            Log.i("PCI", "nroOSCabec = " + cabecTO.getNroOSCabec());
//            Log.i("PCI", "qtdeDiaOS = " + cabecTO.getQtdeDiaOS());
//            Log.i("PCI", "verApontCab = " + cabecTO.getVerApontCab());
//
//
//        }
//
//        PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
//        List plantaCabecList = plantaCabecTO.all();
//
//        for (int i = 0; i < plantaCabecList.size(); i++) {
//
//            plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
//            Log.i("PCI", "PLANTA CABEC");
//            Log.i("PCI", "idPlantaCabec= " + plantaCabecTO.getIdPlantaCabec());
//            Log.i("PCI", "idPlanta = " + plantaCabecTO.getIdPlanta());
//            Log.i("PCI", "idCabec = " + plantaCabecTO.getIdCabec());
//            Log.i("PCI", "statusPlantaCabec = " + plantaCabecTO.getStatusPlantaCabec());
//            Log.i("PCI", "verApontaPlanta = " + plantaCabecTO.getVerApontaPlanta());
//
//        }
//
//        RespItemTO respItemTO = new RespItemTO();
//        List respItemList = respItemTO.all();
//
//        for (int i = 0; i < respItemList.size(); i++) {
//
//            respItemTO = (RespItemTO) respItemList.get(i);
//            Log.i("PCI", "RESP ITEM");
//            Log.i("PCI", "idRespItem = " + respItemTO.getIdRespItem());
//            Log.i("PCI", "idCabRespItem = " + respItemTO.getIdCabRespItem());
//            Log.i("PCI", "idItOsMecanRespItem = " + respItemTO.getIdItOsMecanRespItem());
//            Log.i("PCI", "opcaoRespItem = " + respItemTO.getOpcaoRespItem());
//            Log.i("PCI", "obsRespItem = " + respItemTO.getObsRespItem());
//            Log.i("PCI", "idPlantaItem = " + respItemTO.getIdPlantaItem());
//            Log.i("PCI", "dthrRespItem = " + respItemTO.getDthrRespItem());
//
//        }
//
//        OSFeitaTO osFeitaTO = new OSFeitaTO();
//        List osFeitaList = osFeitaTO.all();
//
//        for(int i = 0; i < osFeitaList.size(); i++){
//
//            osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
//            Log.i("PCI", "OS FEITA");
//            Log.i("PCI", "nroOS = " + osFeitaTO.getNroOS());
//            Log.i("PCI", "dataReal = " + osFeitaTO.getDataReal());
//
//        }


    }

}
