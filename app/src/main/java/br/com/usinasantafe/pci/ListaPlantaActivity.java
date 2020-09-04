package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.util.ConexaoWeb;
import br.com.usinasantafe.pci.util.EnvioDadosServ;
import br.com.usinasantafe.pci.util.Tempo;

public class ListaPlantaActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private OSTO osTO;
    private ArrayList plantaList;
    private ProgressDialog progressBar;
    private List plantaCabecList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_planta);

        TextView textViewAuditor = (TextView) findViewById(R.id.textViewAuditor);
        TextView textViewOS = (TextView) findViewById(R.id.textViewOS);
        ListView listPlantaCL = (ListView) findViewById(R.id.listPlantaCL);
        Button buttonEnviarChecklist = (Button) findViewById(R.id.buttonEnviarChecklist);
        Button buttonExcluirChecklist = (Button) findViewById(R.id.buttonExcluirChecklist);
        Button buttonRetornarChecklist = (Button) findViewById(R.id.buttonRetornarChecklist);

        pciContext = (PCIContext) getApplication();

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("verApontCab", 1L);
        pciContext.setCabecTO((CabecTO) cabecList.get(0));
        cabecList.clear();

        FuncTO funcTO = new FuncTO();
        List funcList = funcTO.get("idFunc", pciContext.getCabecTO().getIdFuncCabec());
        funcTO = (FuncTO) funcList.get(0);

        textViewAuditor.setText(funcTO.getMatricFunc() + " - " + funcTO.getNomeFunc());

        osTO = new OSTO();
        List osList = osTO.get("idOS", pciContext.getCabecTO().getOsCabec());
        osTO = (OSTO) osList.get(0);

        textViewOS.setText("NRO OS: " + osTO.getNroOS());

        ItemTO itemTO = new ItemTO();
        List itemList = itemTO.orderBy("idPlantaItem", true);

        PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
        plantaCabecList = plantaCabecTO.get("idCabec", pciContext.getCabecTO().getIdCabec());

        plantaList = new ArrayList();

        if(plantaCabecList.size() == 0){
            Long idPlanta = 0L;
            for (int i = 0; i < itemList.size(); i++) {
                itemTO = (ItemTO) itemList.get(i);
                if(!idPlanta.equals(itemTO.getIdPlantaItem())){
                    plantaCabecTO = new PlantaCabecTO();
                    plantaCabecTO.setIdPlanta(itemTO.getIdPlantaItem());
                    plantaCabecTO.setIdCabec(pciContext.getCabecTO().getIdCabec());
                    plantaCabecTO.setStatusPlantaCabec(1L);
                    plantaCabecTO.setVerApontaPlanta(0L);
                    plantaCabecTO.insert();
                    plantaList.add(plantaCabecTO);
                    idPlanta = itemTO.getIdPlantaItem();
                }
            }
        }
        else{
            for (int i = 0; i < plantaCabecList.size(); i++) {
                plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
                if(plantaCabecTO.getStatusPlantaCabec() == 1L) {
                    plantaList.add(plantaCabecTO);
                }
            }
            for (int i = 0; i < plantaCabecList.size(); i++) {
                plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
                if(plantaCabecTO.getStatusPlantaCabec() == 2L) {
                    plantaList.add(plantaCabecTO);
                }
            }
        }

        AdapterListPlanta adapterListPlanta = new AdapterListPlanta(this, plantaList);
        listPlantaCL.setAdapter(adapterListPlanta);

        listPlantaCL.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaList.get(position);

                PlantaCabecTO plantaCabec = new PlantaCabecTO();
                List plantaCabecList = plantaCabec.dif("idPlanta", plantaCabecTO.getIdPlanta());
                for (int i = 0; i < plantaCabecList.size(); i++) {
                    plantaCabec = (PlantaCabecTO) plantaCabecList.get(i);
                    plantaCabec.setVerApontaPlanta(0L);
                    plantaCabec.update();
                }
                plantaCabecList.clear();

                plantaCabecTO.setVerApontaPlanta(1L);
                plantaCabecTO.update();

                Intent it = new Intent(ListaPlantaActivity.this, ListaQuestaoActivity.class);
                startActivity(it);

            }

        });

        buttonEnviarChecklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if (pciContext.getCabecTO().getStatusCabec() == 0) {

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPlantaActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("RESPONDA A(S) QUESTAO(OES) DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                        }
                    });

                    alerta.show();

                } else {

                    boolean verPlantaEnvio = false;
                    boolean verBoletimTerm = true;
                    for (int i = 0; i < plantaCabecList.size(); i++) {
                        PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
                        if(plantaCabecTO.getStatusPlantaCabec() == 2L){
                            verPlantaEnvio = true;
                        }
                        if(plantaCabecTO.getStatusPlantaCabec() == 1L){
                            verBoletimTerm = false;
                        }
                    }

                    if(verBoletimTerm){
                        for (int i = 0; i < plantaCabecList.size(); i++) {
                            PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
                            if(plantaCabecTO.getStatusPlantaCabec() == 2L){
                                plantaCabecTO.setStatusPlantaCabec(3L);
                                plantaCabecTO.update();
                            }
                        }
                        pciContext.getCabecTO().setStatusCabec(2L);
                        pciContext.getCabecTO().update();

                        OSFeitaTO osFeitaTO = new OSFeitaTO();
                        List osFeitaList = osFeitaTO.get("nroOS", pciContext.getCabecTO().getNroOSCabec());
                        for (int i = 0; i < osFeitaList.size(); i++) {
                            osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
                            osFeitaTO.delete();
                        }
                        osFeitaTO.setNroOS(pciContext.getCabecTO().getNroOSCabec());
                        osFeitaTO.setDataReal(Tempo.getInstance().dataSHora());
                        osFeitaTO.insert();

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(ListaPlantaActivity.this)) {

                            progressBar = new ProgressDialog(ListaPlantaActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("ENVIANDO DADOS...");
                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                            progressBar.show();
                            EnvioDadosServ.getInstance().envioDadosPrinc(ListaPlantaActivity.this, MenuInicialActivity.class, progressBar);

                        }
                        else{
                            Intent it = new Intent(ListaPlantaActivity.this, MenuInicialActivity.class);
                            startActivity(it);
                        }

                    }
                    else {
                        if (verPlantaEnvio) {
                            for (int i = 0; i < plantaCabecList.size(); i++) {
                                PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
                                if(plantaCabecTO.getStatusPlantaCabec() == 2L){
                                    plantaCabecTO.setStatusPlantaCabec(3L);
                                    plantaCabecTO.update();
                                }
                            }

                            OSFeitaTO osFeitaTO = new OSFeitaTO();
                            List osFeitaList = osFeitaTO.get("nroOS", pciContext.getCabecTO().getNroOSCabec());
                            for (int i = 0; i < osFeitaList.size(); i++) {
                                osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
                                osFeitaTO.delete();
                            }
                            osFeitaTO.setNroOS(pciContext.getCabecTO().getNroOSCabec());
                            osFeitaTO.setDataReal(Tempo.getInstance().dataSHora());
                            osFeitaTO.insert();

                            ConexaoWeb conexaoWeb = new ConexaoWeb();

                            if (conexaoWeb.verificaConexao(ListaPlantaActivity.this)) {

                                progressBar = new ProgressDialog(ListaPlantaActivity.this);
                                progressBar.setCancelable(true);
                                progressBar.setMessage("ENVIANDO DADOS...");
                                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                                progressBar.show();
                                EnvioDadosServ.getInstance().envioDadosPrinc(ListaPlantaActivity.this, ListaPlantaActivity.class, progressBar);

                            }
                            else{
                                Intent it = new Intent(ListaPlantaActivity.this, ListaPlantaActivity.class);
                                startActivity(it);
                            }

                        } else {
                            AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPlantaActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("RESPONDA PELO A(S) QUESTAO(OES) DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });

                            alerta.show();
                        }
                    }

                }

            }
        });

        buttonExcluirChecklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(  ListaPlantaActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE EXCLUIR TODO CHECKLIST?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
                        List plantaCabecList = plantaCabecTO.get("idCabec",  pciContext.getCabecTO().getIdCabec());

                        for (int j = 0; j < plantaCabecList.size(); j++) {
                            plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(j);
                            plantaCabecTO.delete();
                        }

                        RespItemTO respItemTO = new RespItemTO();
                        List listaItem = respItemTO.get("idCabRespItem", pciContext.getCabecTO().getIdCabec());

                        for(int j = 0; j < listaItem.size(); j++){
                            respItemTO = (RespItemTO) listaItem.get(j);
                            respItemTO.delete();
                        }

                        pciContext.getCabecTO().delete();
                        Intent it = new Intent(ListaPlantaActivity.this, MenuInicialActivity.class);
                        startActivity(it);

                    }
                });

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }
        });

        buttonRetornarChecklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaPlantaActivity.this, ListaOSActivity.class);
                startActivity(it);
            }
        });


    }

    public void onBackPressed()  {
    }

}
