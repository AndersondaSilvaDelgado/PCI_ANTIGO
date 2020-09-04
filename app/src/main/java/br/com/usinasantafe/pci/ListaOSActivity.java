package br.com.usinasantafe.pci;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.util.VerifDadosServ;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class ListaOSActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ProgressDialog progressBar;
    private ArrayList osCabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os);

        ListView listViewOS = (ListView) findViewById(R.id.listViewOS);
        Button buttonRetOS = (Button) findViewById(R.id.buttonRetOS);

        pciContext = (PCIContext) getApplication();

        if(pciContext.getCheckListCTR().verPlanta()){
            progressBar = new ProgressDialog(ListaOSActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Atualizando Plantas...");
            progressBar.show();

            pciContext.getCheckListCTR().atualDadosPlanta(ListaOSActivity.this, ListaOSActivity.class, progressBar);
        }

        CabecTO cabecTO = new CabecTO();
        List listCabec = cabecTO.all();
        osCabList = new ArrayList();
        if(listCabec.size() > 0) {
            ArrayList<Long> qtDiasList = new ArrayList<Long>();
            ArrayList<Long> osFeitaList = new ArrayList<Long>();
            for (int j = 0; j < listCabec.size(); j++) {
                cabecTO = (CabecTO) listCabec.get(j);
                if(cabecTO.getStatusCabec() < 2L){
                    boolean verDelCabec = true;
                    for(int i = 0; i < osList.size(); i++) {
                        osTO = (OSTO) osList.get(i);
                        if(cabecTO.getOsCabec().equals(osTO.getIdOS())){
                            osTO.setStatusOS(1L);
                            osCabList.add(osTO);
                            qtDiasList.add(osTO.getQtdeDiaOS());
                            verDelCabec = false;
                        }
                    }
                    if(verDelCabec){
                        if(cabecTO.getStatusCabec() == 0L){
                            PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
                            List plantaCabecList = plantaCabecTO.get("idCabec", cabecTO.getIdCabec());
                            for (int l = 0; l < plantaCabecList.size(); l++) {
                                plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
                                plantaCabecTO.delete();
                            }

                            RespItemTO respItemTO = new RespItemTO();
                            List respItemList = respItemTO.get("idCabRespItem", cabecTO.getIdCabec());
                            for (int l = 0; l < respItemList.size(); l++) {
                                respItemTO = (RespItemTO) respItemList.get(j);
                                respItemTO.delete();
                            }
                            cabecTO.delete();
                        }
                        else if(cabecTO.getStatusCabec() == 1L){
                            PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
                            List plantaCabecList = plantaCabecTO.get("idCabec", cabecTO.getIdCabec());
                            boolean vDelCabec = true;
                            for (int l = 0; l < plantaCabecList.size(); l++) {
                                plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
                                if(plantaCabecTO.getStatusPlantaCabec() != 3L){
                                    plantaCabecTO.delete();
                                    RespItemTO respItemTO = new RespItemTO();

                                    ArrayList respItemPesqList = new ArrayList();
                                    EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                                    pesquisa.setCampo("idCabRespItem");
                                    pesquisa.setValor(cabecTO.getIdCabec());
                                    respItemPesqList.add(pesquisa);

                                    EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                                    pesquisa2.setCampo("idPlantaItem");
                                    pesquisa2.setValor(plantaCabecTO.getIdPlantaCabec());
                                    respItemPesqList.add(pesquisa2);

                                    List respItemList = respItemTO.get(respItemPesqList);
                                    for (int m = 0; m < respItemList.size(); m++) {
                                        respItemTO = (RespItemTO) respItemList.get(j);
                                        respItemTO.delete();
                                    }
                                }else{
                                    vDelCabec = false;
                                }
                            }
                            if(vDelCabec){
                                cabecTO.delete();
                            }
                        }
                    }
                }
                else{
                    for(int i = 0; i < osList.size(); i++) {
                        osTO = (OSTO) osList.get(i);
                        if(cabecTO.getOsCabec().equals(osTO.getIdOS())){
                            osFeitaList.add(osTO.getIdOS());
                        }
                    }
                }
            }

//            VERIFICAR OS QUE NÃO ENVIOU
            for(int i = 0; i < osList.size(); i++) {
                osTO = (OSTO) osList.get(i);
                boolean verOS = true;
                for (int j = 0; j < qtDiasList.size(); j++) {
                    Long qtdedias = qtDiasList.get(j);
                    if(osTO.getQtdeDiaOS() == qtdedias){
                        verOS = false;
                    }
                }
                for (int j = 0; j < osFeitaList.size(); j++) {
                    Long osFeita = osFeitaList.get(j);
                    if(osTO.getIdOS() == osFeita){
                        verOS = false;
                    }
                }
                if(verOS){
                    osCabList.add(osTO);
                }
            }
        }
        else{
            for(int i = 0; i < osList.size(); i++) {
                osTO = (OSTO) osList.get(i);
                osCabList.add(osTO);
            }
        }

//        teste();

        AdapterListOS adapterListOS = new AdapterListOS(this, osCabList);
        listViewOS.setAdapter(adapterListOS);

        listViewOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                OSTO osTO = new OSTO();
                osTO = (OSTO) osCabList.get(position);

                if(osTO.getStatusOS() == 0L){

                    pciContext.getCabecTO().setOsCabec(osTO.getIdOS());
                    pciContext.getCabecTO().setNroOSCabec(osTO.getNroOS());
                    pciContext.getCabecTO().setStatusCabec(0L);
                    pciContext.getCabecTO().setQtdeDiaOS(osTO.getQtdeDiaOS());
                    pciContext.getCabecTO().setIdExtCabec(0L);
                    pciContext.getCabecTO().setDataCabec(Tempo.getInstance().data());
                    pciContext.getCabecTO().setVerApontCab(1L);
                    pciContext.getCabecTO().insert();

                }

                CabecTO cabecTO = new CabecTO();
                List cabecList = cabecTO.get("osCabec", osTO.getIdOS());
                cabecTO = (CabecTO) cabecList.get(0);
                cabecTO.setVerApontCab(1L);
                cabecTO.update();
                cabecList.clear();

                cabecTO = new CabecTO();
                cabecList = cabecTO.dif("osCabec", osTO.getIdOS());
                for(int i = 0; i < cabecList.size(); i++) {
                    cabecTO = (CabecTO) cabecList.get(i);
                    cabecTO.setVerApontCab(0L);
                    cabecTO.update();
                }
                cabecList.clear();

                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Pequisando Item...");
                progressBar.show();

                VerifDadosServ.getInstance().verDados(osTO.getIdOS().toString(), "Item"
                        ,   ListaOSActivity.this, ListaPlantaActivity.class, progressBar);

                osCabList.clear();

            }

        });

        buttonRetOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaOSActivity.this, FuncionarioActivity.class);
                startActivity(it);
                osCabList.clear();
            }
        });

    }

    public void onBackPressed()  {
    }


    public void teste() {

        Log.i("PCI", "AKI");

        OSTO osto = new OSTO();

        for (int i = 0; i < osCabList.size(); i++) {

            osto = (OSTO) osCabList.get(i);
            Log.i("PCI", "OSTO");
            Log.i("PCI", "idOS = " + osto.getIdOS());
            Log.i("PCI", "nroOS = " + osto.getNroOS());
            Log.i("PCI", "idPlantaOS = " + osto.getIdPlantaOS());
            Log.i("PCI", "qtdeDiaOS = " + osto.getQtdeDiaOS());
            Log.i("PCI", "descrPeriodo = " + osto.getDescrPeriodo());
            Log.i("PCI", "statusOS = " + osto.getStatusOS());

        }

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

    }



}
