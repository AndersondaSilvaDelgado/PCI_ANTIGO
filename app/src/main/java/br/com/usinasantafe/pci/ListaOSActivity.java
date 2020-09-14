package br.com.usinasantafe.pci;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.OSBaseBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.util.VerifDadosServ;

public class ListaOSActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ProgressDialog progressBar;
    private ArrayList<OSBaseBean> osCabList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_os);

        ListView listViewOS = (ListView) findViewById(R.id.listViewOS);
        Button buttonRetOS = (Button) findViewById(R.id.buttonRetOS);

        pciContext = (PCIContext) getApplication();

        if(pciContext.getCheckListCTR().verPlanta()){

            progressBar = new ProgressDialog(ListaOSActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Atualizando Plantas...");
            progressBar.show();

            pciContext.getCheckListCTR().atualDadosPlanta(ListaOSActivity.this, ListaOSActivity.class, progressBar);

        }

        osCabList = pciContext.getCheckListCTR().osList();

        AdapterListOS adapterListOS = new AdapterListOS(this, osCabList);
        listViewOS.setAdapter(adapterListOS);

        listViewOS.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {

                OSBaseBean osBaseBean = osCabList.get(position);

                pciContext.getCheckListCTR().salvarAtualCabec(osBaseBean);

                progressBar = new ProgressDialog(v.getContext());
                progressBar.setCancelable(true);
                progressBar.setMessage("Pequisando Item...");
                progressBar.show();

                VerifDadosServ.getInstance().verDados(osBaseBean.getIdOS().toString(), "Item"
                        ,   ListaOSActivity.this, ListaPlantaActivity.class, progressBar);

                osCabList.clear();

            }

        });

        buttonRetOS.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(ListaOSActivity.this, FuncionarioActivity.class);
                startActivity(it);
                osCabList.clear();
            }
        });

    }

    public void onBackPressed()  {
    }


}
