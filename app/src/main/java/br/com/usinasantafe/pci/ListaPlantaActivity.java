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

import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;
import br.com.usinasantafe.pci.model.bean.estatica.OSBaseBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.util.EnvioDadosServ;

public class ListaPlantaActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ArrayList plantaList;
    private ProgressDialog progressBar;

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

        FuncBean funcBean = pciContext.getCheckListCTR().getFunc();
        textViewAuditor.setText(funcBean.getMatricFunc() + " - " + funcBean.getNomeFunc());

        OSBaseBean osBaseBean = pciContext.getCheckListCTR().getOS();
        textViewOS.setText("NRO OS: " + osBaseBean.getNroOS());

        plantaList = pciContext.getCheckListCTR().retSalvarPlantaCabec();

        AdapterListPlanta adapterListPlanta = new AdapterListPlanta(this, plantaList);
        listPlantaCL.setAdapter(adapterListPlanta);

        listPlantaCL.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaList.get(position);
                pciContext.getCheckListCTR().atualStatusApontPlanta(plantaCabecBean);

                Intent it = new Intent(ListaPlantaActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
                finish();

            }

        });

        buttonEnviarChecklist.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

            if(pciContext.getCheckListCTR().verPlantaEnvio()){

                pciContext.getCheckListCTR().atualStatusEnvio();

                progressBar = new ProgressDialog(ListaPlantaActivity.this);
                progressBar.setCancelable(true);
                progressBar.setMessage("ENVIANDO DADOS...");
                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                progressBar.show();
                EnvioDadosServ.getInstance().envioDadosPrinc(ListaPlantaActivity.this, MenuInicialActivity.class, progressBar);


            }
            else{

                AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPlantaActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("RESPONDA A(S) QUESTAO(OES) DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }



//                if (pciContext.getCabecTO().getStatusCabec() == 0) {
//
//                    AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPlantaActivity.this);
//                    alerta.setTitle("ATENÇÃO");
//                    alerta.setMessage("RESPONDA A(S) QUESTAO(OES) DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
//                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                        @Override
//                        public void onClick(DialogInterface dialog, int which) {
//
//                        }
//                    });
//
//                    alerta.show();
//
//                } else {
//
//                    boolean verPlantaEnvio = false;
//                    boolean verBoletimTerm = true;
//                    for (int i = 0; i < plantaCabecList.size(); i++) {
//                        PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
//                        if(plantaCabecTO.getStatusPlantaCabec() == 2L){
//                            verPlantaEnvio = true;
//                        }
//                        if(plantaCabecTO.getStatusPlantaCabec() == 1L){
//                            verBoletimTerm = false;
//                        }
//                    }
//
//                    if(verBoletimTerm){
//                        for (int i = 0; i < plantaCabecList.size(); i++) {
//                            PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
//                            if(plantaCabecTO.getStatusPlantaCabec() == 2L){
//                                plantaCabecTO.setStatusPlantaCabec(3L);
//                                plantaCabecTO.update();
//                            }
//                        }
//                        pciContext.getCabecTO().setStatusCabec(2L);
//                        pciContext.getCabecTO().update();
//
//                        OSFeitaTO osFeitaTO = new OSFeitaTO();
//                        List osFeitaList = osFeitaTO.get("nroOS", pciContext.getCabecTO().getNroOSCabec());
//                        for (int i = 0; i < osFeitaList.size(); i++) {
//                            osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
//                            osFeitaTO.delete();
//                        }
//                        osFeitaTO.setNroOS(pciContext.getCabecTO().getNroOSCabec());
//                        osFeitaTO.setDataReal(Tempo.getInstance().dataSHora());
//                        osFeitaTO.insert();
//
//                        ConexaoWeb conexaoWeb = new ConexaoWeb();
//
//                        if (conexaoWeb.verificaConexao(ListaPlantaActivity.this)) {
//
//                            progressBar = new ProgressDialog(ListaPlantaActivity.this);
//                            progressBar.setCancelable(true);
//                            progressBar.setMessage("ENVIANDO DADOS...");
//                            progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                            progressBar.show();
//                            EnvioDadosServ.getInstance().envioDadosPrinc(ListaPlantaActivity.this, MenuInicialActivity.class, progressBar);
//
//                        }
//                        else{
//                            Intent it = new Intent(ListaPlantaActivity.this, MenuInicialActivity.class);
//                            startActivity(it);
//                        }
//
//                    }
//                    else {
//                        if (verPlantaEnvio) {
//                            for (int i = 0; i < plantaCabecList.size(); i++) {
//                                PlantaCabecTO plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(i);
//                                if(plantaCabecTO.getStatusPlantaCabec() == 2L){
//                                    plantaCabecTO.setStatusPlantaCabec(3L);
//                                    plantaCabecTO.update();
//                                }
//                            }
//
//                            OSFeitaTO osFeitaTO = new OSFeitaTO();
//                            List osFeitaList = osFeitaTO.get("nroOS", pciContext.getCabecTO().getNroOSCabec());
//                            for (int i = 0; i < osFeitaList.size(); i++) {
//                                osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
//                                osFeitaTO.delete();
//                            }
//                            osFeitaTO.setNroOS(pciContext.getCabecTO().getNroOSCabec());
//                            osFeitaTO.setDataReal(Tempo.getInstance().dataSHora());
//                            osFeitaTO.insert();
//
//                            ConexaoWeb conexaoWeb = new ConexaoWeb();
//
//                            if (conexaoWeb.verificaConexao(ListaPlantaActivity.this)) {
//
//                                progressBar = new ProgressDialog(ListaPlantaActivity.this);
//                                progressBar.setCancelable(true);
//                                progressBar.setMessage("ENVIANDO DADOS...");
//                                progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
//                                progressBar.show();
//                                EnvioDadosServ.getInstance().envioDadosPrinc(ListaPlantaActivity.this, ListaPlantaActivity.class, progressBar);
//
//                            }
//                            else{
//                                Intent it = new Intent(ListaPlantaActivity.this, ListaPlantaActivity.class);
//                                startActivity(it);
//                            }
//
//                        } else {
//                            AlertDialog.Builder alerta = new AlertDialog.Builder(ListaPlantaActivity.this);
//                            alerta.setTitle("ATENÇÃO");
//                            alerta.setMessage("RESPONDA PELO A(S) QUESTAO(OES) DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
//                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//
//                                }
//                            });
//
//                            alerta.show();
//                        }
//                    }
//
//                }

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

                        pciContext.getCheckListCTR().deleteCheckListApont();

                        Intent it = new Intent(ListaPlantaActivity.this, MenuInicialActivity.class);
                        startActivity(it);
                        finish();

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
