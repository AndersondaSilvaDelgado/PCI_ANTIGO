package br.com.usinasantafe.pci;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.OSFeitaTO;

public class ListaOSFeitaActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_osfeita);

        Button buttonRetOSFeita = (Button) findViewById(R.id.buttonRetOSFeita);
        ListView listViewOSFeita = (ListView) findViewById(R.id.listViewOSFeita);

        pciContext = (PCIContext) getApplication();

        ArrayList<String> itens = new ArrayList<String>();

        OSFeitaTO osFeitaTO = new OSFeitaTO();
        List osFeitaList = osFeitaTO.all();

        if(osFeitaList.size() == 0){
            itens.add("FUNCIONÁRIO SEM O.S. APONTADA NO DIA ATUAL");
        }
        else{
            for(int i = 0; i < osFeitaList.size(); i++){
                osFeitaTO = (OSFeitaTO) osFeitaList.get(i);
                itens.add(osFeitaTO.getNroOS().toString());
            }
        }

        AdapterList adapterList = new AdapterList(this, itens);
        listViewOSFeita.setAdapter(adapterList);

        buttonRetOSFeita.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent( ListaOSFeitaActivity.this, PrincipalActivity.class);
                startActivity(it);
            }
        });

    }

    public void onBackPressed()  {
    }

}
