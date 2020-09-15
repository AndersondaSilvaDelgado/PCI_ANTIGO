package br.com.usinasantafe.pci;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pci.model.bean.estatica.FuncBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.util.ConexaoWeb;

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

                            pciContext.getCheckListCTR().atualDadosFunc(FuncionarioActivity.this, FuncionarioActivity.class, progressBar);

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

                if(!editTextPadrao.getText().toString().equals("")) {

                    Long matricFunc = Long.parseLong(editTextPadrao.getText().toString());

                    if(pciContext.getCheckListCTR().verFunc(matricFunc)){

                        FuncBean funcBean = pciContext.getCheckListCTR().getFunc(matricFunc);
                        pciContext.getCheckListCTR().setCabecBean(new CabecBean());
                        pciContext.getCheckListCTR().getCabecBean().setIdFuncCabec(funcBean.getIdFunc());
                        pciContext.getCheckListCTR().getCabecBean().setIdOficSecaoCabec(funcBean.getIdOficSecaoFunc());

                        progressBar = new ProgressDialog(v.getContext());
                        progressBar.setCancelable(true);
                        progressBar.setMessage("Pequisando a OS...");
                        progressBar.show();

                        pciContext.getCheckListCTR().verOS(funcBean.getIdOficSecaoFunc().toString()
                                ,  FuncionarioActivity.this, ListaOSActivity.class, progressBar);


                    }
                    else{

                        AlertDialog.Builder alerta = new AlertDialog.Builder(FuncionarioActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("FUNCIONÁRIO INEXISTENTE!");

                        alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
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
                if (editTextPadrao.getText().toString().length() > 0) {
                    editTextPadrao.setText(editTextPadrao.getText().toString().substring(0, editTextPadrao.getText().toString().length() - 1));
                }
            }
        });

    }

    public void onBackPressed()  {
        Intent it = new Intent(FuncionarioActivity.this, MenuInicialActivity.class);
        startActivity(it);
    }

}
