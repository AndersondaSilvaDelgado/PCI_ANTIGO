package br.com.usinasantafe.pci.view;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;

public class FuncVerOSActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_func_ver_os);

        pciContext = (PCIContext) getApplication();

        Button buttonOkFuncionario = (Button) findViewById(R.id.buttonOkPadrao);
        Button buttonCancFuncionario = (Button) findViewById(R.id.buttonCancPadrao);

        buttonOkFuncionario.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!editTextPadrao.getText().toString().equals("")) {

                    Long matricFunc = Long.parseLong(editTextPadrao.getText().toString());

                    if(pciContext.getCheckListCTR().verFunc(matricFunc)){

                        if(pciContext.getCheckListCTR().verCabecFechado(pciContext.getCheckListCTR().getFunc(matricFunc).getIdFunc())){

                            pciContext.setIdFunc(pciContext.getCheckListCTR().getFunc(matricFunc).getIdFunc());

                            Intent it = new Intent(  FuncVerOSActivity.this, ListaOSFeitaActivity.class);
                            startActivity(it);
                            finish();

                        }
                        else{

                            AlertDialog.Builder alerta = new AlertDialog.Builder(FuncVerOSActivity.this);
                            alerta.setTitle("ATENÇÃO");
                            alerta.setMessage("FUNCIONÁRIO SEM O.S. APONTADA NO DIA ATUAL");

                            alerta.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {

                                }
                            });
                            alerta.show();

                        }

                    }
                    else{

                        AlertDialog.Builder alerta = new AlertDialog.Builder(FuncVerOSActivity.this);
                        alerta.setTitle("ATENÇÃO");
                        alerta.setMessage("FUNCIONÁRIO INEXISTENTE!");

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
        Intent it = new Intent(  FuncVerOSActivity.this, MenuInicialActivity.class);
        startActivity(it);
    }

}
