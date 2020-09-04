package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pci.util.ConexaoWeb;
import br.com.usinasantafe.pci.util.AtualDadosServ;

public class ConfigActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextNLinhaConfig;
    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_config);

        pciContext = (PCIContext) getApplication();

        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarDados);
        Button buttonCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button buttonSalvarConfig = (Button) findViewById(R.id.buttonSalvarConfig);
        editTextNLinhaConfig = (EditText) findViewById(R.id.editTextNLinhaConfig);

        if(pciContext.getConfigCTR().hasElements()) {
            editTextNLinhaConfig.setText(String.valueOf(pciContext.getConfigCTR().getConfig().getNumLinhaConfig()));
        }

        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if(conexaoWeb.verificaConexao(ConfigActivity.this)){

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    pciContext.getConfigCTR().atualTodasTabelas(ConfigActivity.this, progressBar);

                }
                else{

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
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
        });

        buttonSalvarConfig.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {

                  if (!editTextNLinhaConfig.getText().toString().equals("")) {

                      pciContext.getConfigCTR().salvarConfig(Long.valueOf(editTextNLinhaConfig.getText().toString()));

                      Intent it = new Intent( ConfigActivity.this, MenuInicialActivity.class);
                      startActivity(it);

                  }
                  else{

                      AlertDialog.Builder alerta = new AlertDialog.Builder(ConfigActivity.this);
                      alerta.setTitle("ATENÇÃO");
                      alerta.setMessage("POR FAVOR! DIGITE O NUMERO DA LINHA.");
                      alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {

                          }
                      });
                      alerta.show();

                  }

              }
          });

        buttonCancConfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(ConfigActivity.this, MenuInicialActivity.class);
                startActivity(it);
            }

        });

    }

    public void onBackPressed()  {
    }

}
