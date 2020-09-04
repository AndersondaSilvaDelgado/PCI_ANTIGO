package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.util.ConexaoWeb;
import br.com.usinasantafe.pci.util.EnvioDadosServ;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class EnvioDadosActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private int qtde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_dados);

        TextView textViewEnvioDados = (TextView) findViewById(R.id.textViewEnvioDados);
        Button buttonSimEnvioDados = (Button) findViewById(R.id.buttonSimEnvioDados);
        Button buttonNaoEnvioDados = (Button) findViewById(R.id.buttonNaoEnvioDados);

        qtde = verEnvio();

        if(qtde == 0){
            textViewEnvioDados.setText("NÃO CONTÉM CHECKLIST(S) PARA SEREM(S) REENVIADO(S).");
        }
        else{
            textViewEnvioDados.setText("CONTÉM " + qtde + " CHECKLIST(S) PARA SEREM(S) REENVIAD0(S).");
        }

        buttonSimEnvioDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(qtde > 0) {

                    ConexaoWeb conexaoWeb = new ConexaoWeb();

                    if (conexaoWeb.verificaConexao(EnvioDadosActivity.this)) {

                        progressBar = new ProgressDialog(v.getContext());
                        progressBar.setCancelable(true);
                        progressBar.setMessage("ENVIANDO DADOS...");
                        progressBar.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                        progressBar.show();

                        EnvioDadosServ.getInstance().envioDadosPrinc(EnvioDadosActivity.this, EnvioDadosActivity.class, progressBar);

                    } else {

                        AlertDialog.Builder alerta = new AlertDialog.Builder(EnvioDadosActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");
                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                            }
                        });

                        alerta.show();
                    }

                }

            }
        });


        buttonNaoEnvioDados.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(EnvioDadosActivity.this, MenuInicialActivity.class);
                startActivity(it);
            }
        });

    }

    public void onBackPressed()  {
    }


    public int verEnvio(){

        int qtde = 0;

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 1L);

        for (int i = 0; i < cabecList.size(); i++) {

            boolean ver = false;
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
                ver = true;
            }

            if(ver){
                qtde = qtde + 1;
            }


        }

        cabecTO = new CabecTO();
        qtde = qtde + cabecTO.get("statusCabec", 2L).size();

        return qtde;

    }

}
