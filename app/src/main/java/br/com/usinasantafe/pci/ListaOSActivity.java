package br.com.usinasantafe.pci;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;

import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class ListaOSActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ProgressDialog progressBar;
    private ArrayList<OSBean> osCabList;

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

        osCabList = pciContext.getCheckListCTR().osList();

        AdapterListOS adapterListOS = new AdapterListOS(this, osCabList);
        listViewOS.setAdapter(adapterListOS);

        listViewOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                OSBean osBean = osCabList.get(position);

                pciContext.getCheckListCTR().salvarAtualCabec(osBean);

                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Pequisando Item...");
                progressBar.show();

                VerifDadosServ.getInstance().verDados(osBean.getIdOS().toString(), "Item"
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

//    public void teste() {
//
//        Log.i("PCI", "AKI");
//
//        OSTO osto = new OSTO();
//
//        for (int i = 0; i < osCabList.size(); i++) {
//
//            osto = (OSTO) osCabList.get(i);
//            Log.i("PCI", "OSTO");
//            Log.i("PCI", "idOS = " + osto.getIdOS());
//            Log.i("PCI", "nroOS = " + osto.getNroOS());
//            Log.i("PCI", "idPlantaOS = " + osto.getIdPlantaOS());
//            Log.i("PCI", "qtdeDiaOS = " + osto.getQtdeDiaOS());
//            Log.i("PCI", "descrPeriodo = " + osto.getDescrPeriodo());
//            Log.i("PCI", "statusOS = " + osto.getStatusOS());
//
//        }
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
//    }

}
