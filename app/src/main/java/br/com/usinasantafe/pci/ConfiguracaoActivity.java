package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pci.bo.ConexaoWeb;
import br.com.usinasantafe.pci.bo.ManipDadosReceb;
import br.com.usinasantafe.pci.to.variavel.ConfiguracaoTO;

public class ConfiguracaoActivity extends ActivityGeneric {

    private ProgressDialog progressBar;
    private EditText editTextNLinhaConfig;
    private ConfiguracaoTO configuracaoTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_configuracao);

        Button btAtualBDConfig = (Button) findViewById(R.id.buttonAtualizarDados);
        Button buttonCancConfig = (Button) findViewById(R.id.buttonCancConfig);
        Button buttonSalvarConfig = (Button) findViewById(R.id.buttonSalvarConfig);
        editTextNLinhaConfig = (EditText) findViewById(R.id.editTextNLinhaConfig);

        configuracaoTO = new ConfiguracaoTO();

        if(configuracaoTO.hasElements()) {
            List configList = configuracaoTO.all();
            configuracaoTO = (ConfiguracaoTO) configList.get(0);
            editTextNLinhaConfig.setText(String.valueOf(configuracaoTO.getNumLinha()));
        }

        btAtualBDConfig.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                ConexaoWeb conexaoWeb = new ConexaoWeb();

                if(conexaoWeb.verificaConexao(ConfiguracaoActivity.this)){

                    progressBar = new ProgressDialog(v.getContext());
                    progressBar.setCancelable(true);
                    progressBar.setMessage("ATUALIZANDO...");
                    progressBar.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                    progressBar.setProgress(0);
                    progressBar.setMax(100);
                    progressBar.show();

                    ManipDadosReceb.getInstance().atualizarBD(progressBar);
                    ManipDadosReceb.getInstance().setContext(ConfiguracaoActivity.this);

                }
                else{

                    AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracaoActivity.this);
                    alerta.setTitle("ATENÇÃO");
                    alerta.setMessage("FALHA NA CONEXÃO DE DADOS. O CELULAR ESTA SEM SINAL. POR FAVOR, TENTE NOVAMENTE QUANDO O CELULAR ESTIVE COM SINAL.");

                    alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // TODO Auto-generated method stub

                        }
                    });
                    alerta.show();

                }

            }
        });

        buttonSalvarConfig.setOnClickListener(new View.OnClickListener() {
              @Override
              public void onClick(View v) {
                  // TODO Auto-generated method stub

                  if (!editTextNLinhaConfig.getText().toString().equals("")) {
                      configuracaoTO.deleteAll();
                      configuracaoTO.setNumLinha(Long.valueOf(editTextNLinhaConfig.getText().toString()));
                      configuracaoTO.insert();

                      Intent it = new Intent( ConfiguracaoActivity.this, PrincipalActivity.class);
                      startActivity(it);

                  }
                  else{

                      AlertDialog.Builder alerta = new AlertDialog.Builder(ConfiguracaoActivity.this);
                      alerta.setTitle("ATENÇÃO");
                      alerta.setMessage("POR FAVOR! DIGITE O NUMERO DA LINHA.");
                      alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                          @Override
                          public void onClick(DialogInterface dialog, int which) {
                              // TODO Auto-generated method stub

                          }
                      });
                      alerta.show();

                  }

              }
          });

        buttonCancConfig.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(ConfiguracaoActivity.this, PrincipalActivity.class);
                startActivity(it);
            }

        });

    }

    public void onBackPressed()  {
    }

}
