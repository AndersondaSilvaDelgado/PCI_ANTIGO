package br.com.usinasantafe.pci.view;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;

public class ListaOSFeitaActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_osfeita);

        Button buttonRetOSFeita = (Button) findViewById(R.id.buttonRetOSFeita);
        ListView listViewOSFeita = (ListView) findViewById(R.id.listViewOSFeita);

        pciContext = (PCIContext) getApplication();

        AdapterList adapterList = new AdapterList(this, pciContext.getCheckListCTR().osFeitasList(pciContext.getIdFunc()));
        listViewOSFeita.setAdapter(adapterList);

        buttonRetOSFeita.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent( ListaOSFeitaActivity.this, MenuInicialActivity.class);
                startActivity(it);
            }
        });

    }

    public void onBackPressed()  {
    }

}
