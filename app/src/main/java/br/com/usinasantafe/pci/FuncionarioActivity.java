package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.List;

import br.com.usinasantafe.pci.bo.ConexaoWeb;
import br.com.usinasantafe.pci.bo.ManipDadosVerif;
import br.com.usinasantafe.pci.to.estatica.FuncTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;

public class FuncionarioActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_funcionario);

        pciContext = (PCIContext) getApplication();

        Button buttonOkFuncionario = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancFuncionario = (Button) findViewById(R.id.buttonCancPadrao);
        Button buttonAtualPadrao = (Button) findViewById(R.id.buttonAtualPadrao);

        buttonAtualPadrao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder alerta = new AlertDialog.Builder(  FuncionarioActivity.this);
                alerta.setTitle("ATENÇÃO");
                alerta.setMessage("DESEJA REALMENTE ATUALIZAR BASE DE DADOS?");
                alerta.setNegativeButton("SIM", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        ConexaoWeb conexaoWeb = new ConexaoWeb();

                        if (conexaoWeb.verificaConexao(FuncionarioActivity.this)) {

                            progressBar = new ProgressDialog(FuncionarioActivity.this);
                            progressBar.setCancelable(true);
                            progressBar.setMessage("Atualizando Colaboradores...");
                            progressBar.show();

                            ManipDadosVerif.getInstance().verDados("", "Funcionario"
                                    , FuncionarioActivity.this, FuncionarioActivity.class, progressBar);

                        } else {

                            AlertDialog.Builder alerta = new AlertDialog.Builder( FuncionarioActivity.this);
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

                alerta.setPositiveButton("NÃO", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

                alerta.show();

            }

        });

        buttonOkFuncionario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(!editTextPadrao.getText().toString().equals("")) {

                    Long matricFunc = Long.parseLong(editTextPadrao.getText().toString());

                    FuncTO funcTO = new FuncTO();
                    List listaFuncPesq = funcTO.get("matricFunc", matricFunc);

                    if(listaFuncPesq.size() > 0){

                        FuncTO funcTOPesq = (FuncTO) listaFuncPesq.get(0);
                        pciContext.getCabecTO().setIdFuncCabec(funcTOPesq.getIdFunc());

                        progressBar = new ProgressDialog(v.getContext());
                        progressBar.setCancelable(true);
                        progressBar.setMessage("Pequisando a OS...");
                        progressBar.show();

                        ManipDadosVerif.getInstance().verDados(funcTOPesq.getIdOficSecaoFunc().toString(), "OS"
                                ,  FuncionarioActivity.this, ListaOSActivity.class, progressBar);


                    }
                    else{

                        AlertDialog.Builder alerta = new AlertDialog.Builder(FuncionarioActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("FUNCIONÁRIO INEXISTENTE!");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                // TODO Auto-generated method stub
                                editTextPadrao.setText("");
                            }
                        });
                        alerta.show();

                    }

                }

            }
        });


        buttonCancFuncionario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed()  {
        Intent it = new Intent(FuncionarioActivity.this, PrincipalActivity.class);
        startActivity(it);
    }

}
