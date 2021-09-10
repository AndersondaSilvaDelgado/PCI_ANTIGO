package br.com.usinasantafe.pci.view;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.util.ConexaoWeb;
import br.com.usinasantafe.pci.util.EnvioDadosServ;

public class EnvioDadosActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ProgressDialog progressBar;
    private int qtde;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_envio_dados);

        pciContext = (PCIContext) getApplication();

        TextView textViewEnvioDados = (TextView) findViewById(R.id.textViewEnvioDados);
        Button buttonSimEnvioDados = (Button) findViewById(R.id.buttonSimEnvioDados);
        Button buttonNaoEnvioDados = (Button) findViewById(R.id.buttonNaoEnvioDados);

        qtde = 0;

        if(!pciContext.getCheckListCTR().verDadosEnvio()){
            textViewEnvioDados.setText("NÃO CONTÉM CHECKLIST(S) PARA SEREM(S) REENVIADO(S).");
        }
        else{
            qtde = pciContext.getCheckListCTR().qtdeCabecEnvio();
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

}
