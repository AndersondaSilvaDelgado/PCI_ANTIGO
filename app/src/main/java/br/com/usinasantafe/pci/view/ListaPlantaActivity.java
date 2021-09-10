package br.com.usinasantafe.pci.view;

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

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;
import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.util.EnvioDadosServ;

public class ListaPlantaActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ArrayList<PlantaCabecBean> plantaCabecList;
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

        OSBean osBean = pciContext.getCheckListCTR().getOS();
        textViewOS.setText("NRO OS: " + osBean.getNroOS());

        plantaCabecList = pciContext.getCheckListCTR().retSalvarPlantaCabec();

        AdapterListPlanta adapterListPlanta = new AdapterListPlanta(this, plantaCabecList);
        listPlantaCL.setAdapter(adapterListPlanta);

        listPlantaCL.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                PlantaCabecBean plantaCabecBean = (PlantaCabecBean) plantaCabecList.get(position);
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
                alerta.setMessage("RESPONDA TODAS AS QUESTÕES DE PELO MENOS UMA PLANTA PARA PODE REALIZAR O ENVIO DOS DADOS.");
                alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });

                alerta.show();

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

                        pciContext.getCheckListCTR().delCheckListApont();

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
                finish();
            }
        });


    }

    public void onBackPressed()  {
    }



}
