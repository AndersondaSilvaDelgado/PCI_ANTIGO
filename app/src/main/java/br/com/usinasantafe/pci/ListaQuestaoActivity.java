package br.com.usinasantafe.pci;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.bo.ManipDadosVerif;
import br.com.usinasantafe.pci.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.to.estatica.FuncTO;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.estatica.OSTO;
import br.com.usinasantafe.pci.to.estatica.PlantaTO;
import br.com.usinasantafe.pci.to.estatica.ServicoTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.PlantaCabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

public class ListaQuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private OSTO osTO;
    private ArrayList itemList;
    private ProgressDialog progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_questao);

        pciContext = (PCIContext) getApplication();

        TextView textViewDadosAuditor = (TextView) findViewById(R.id.textViewDadosAuditor);
        TextView textViewDadosOS = (TextView) findViewById(R.id.textViewDadosOS);
        ListView listViewQuestao = (ListView) findViewById(R.id.listViewQuestao);
        Button buttonRetQuestao = (Button) findViewById(R.id.buttonRetQuestao);

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("verApontCab", 1L);
        pciContext.setCabecTO((CabecTO) cabecList.get(0));
        cabecList.clear();

        FuncTO funcTO = new FuncTO();
        List funcList = funcTO.get("idFunc", pciContext.getCabecTO().getIdFuncCabec());
        funcTO = (FuncTO) funcList.get(0);

        textViewDadosAuditor.setText(funcTO.getMatricFunc() + " - " + funcTO.getNomeFunc());

        osTO = new OSTO();
        List osList = osTO.get("idOS", pciContext.getCabecTO().getOsCabec());
        osTO = (OSTO) osList.get(0);

        textViewDadosOS.setText("NRO OS: " + osTO.getNroOS());

        PlantaCabecTO plantaCabecTO = new PlantaCabecTO();
        List plantaCabecList = plantaCabecTO.get("verApontaPlanta",  1L);
        plantaCabecTO = (PlantaCabecTO) plantaCabecList.get(0);
        plantaCabecList.clear();

        ItemTO itemTO = new ItemTO();
        List iList = itemTO.getAndOrderBy("idPlantaItem", plantaCabecTO.getIdPlanta(), "idComponenteItem", true);

        itemList = new ArrayList();
        RespItemTO respItemTO = new RespItemTO();
        List respList = respItemTO.get("idCabRespItem", pciContext.getCabecTO().getIdCabec());

        for(int i = 0; i < iList.size(); i++){
            boolean ver = true;
            itemTO = (ItemTO) iList.get(i);
            for (int j = 0; j < respList.size(); j++) {
                respItemTO = (RespItemTO) respList.get(j);
                if(itemTO.getIdItem().equals(respItemTO.getIdItOsMecanRespItem())){
                    ver = false;
                }
            }
            if(ver) {
                itemTO.setOpcaoSelItem(0L);
                itemList.add(itemTO);
            }
        }

        if(itemList.size() == 0){
            plantaCabecTO.setStatusPlantaCabec(2L);
            plantaCabecTO.update();

            pciContext.getCabecTO().setStatusCabec(1L);
            pciContext.getCabecTO().update();
        }

        for(int i = 0; i < iList.size(); i++){
            boolean ver = false;
            itemTO = (ItemTO) iList.get(i);
            for (int j = 0; j < respList.size(); j++) {
                respItemTO = (RespItemTO) respList.get(j);
                if(itemTO.getIdItem().equals(respItemTO.getIdItOsMecanRespItem())){
                    ver = true;
                    if(respItemTO.getOpcaoRespItem() == 2L){
                        itemTO.setOpcaoSelItem(2L);
                    }
                    else if(respItemTO.getOpcaoRespItem() == 1L){
                        itemTO.setOpcaoSelItem(1L);
                    }
                }
            }
            if(ver) {
                itemList.add(itemTO);
            }
        }

        boolean verPlanta = false;

        for(int i = 0; i < itemList .size(); i++){
            itemTO = (ItemTO) itemList.get(i);
            PlantaTO plantaTO = new PlantaTO();
            List plantaList = plantaTO.get("idPlanta", itemTO.getIdPlantaItem());
            if(plantaList.size() == 0){
                verPlanta = true;
            }
            plantaList.clear();
        }

        if(verPlanta){
            progressBar = new ProgressDialog( ListaQuestaoActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Atualizando Plantas...");
            progressBar.show();

            ManipDadosVerif.getInstance().verDados("", "Planta"
                    , ListaQuestaoActivity.this, ListaQuestaoActivity.class, progressBar);
        }

        boolean verServico = false;

        for(int i = 0; i < itemList.size(); i++){
            itemTO = (ItemTO) itemList.get(i);
            ServicoTO servicoTO = new ServicoTO();
            List servicoList = servicoTO.get("idServico", itemTO.getIdServicoItem());
            if(servicoList.size() == 0){
                verServico = true;
            }
            servicoList.clear();
        }

        if(verServico){
            progressBar = new ProgressDialog( ListaQuestaoActivity.this);
            progressBar.setCancelable(true);
            progressBar.setMessage("Atualizando Itens...");
            progressBar.show();

            ManipDadosVerif.getInstance().verDados("", "Servico"
                    , ListaQuestaoActivity.this, ListaQuestaoActivity.class, progressBar);
        }

        AdapterListQuestao adapterListQuestao = new AdapterListQuestao(this, itemList);
        listViewQuestao.setAdapter(adapterListQuestao);

        listViewQuestao.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> l, View v, int position,
                                    long id) {
                // TODO Auto-generated method stub

                pciContext.setItemTO((ItemTO) itemList.get(position));

                RespItemTO respItemTO = new RespItemTO();
                ArrayList itemArrayList = new ArrayList();

                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("idCabRespItem");
                pesquisa.setValor(pciContext.getCabecTO().getIdCabec());
                itemArrayList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("idItOsMecanRespItem");
                pesquisa2.setValor(pciContext.getItemTO().getIdItem());
                itemArrayList.add(pesquisa2);

                List respItemList = respItemTO.get(itemArrayList);

                if(respItemList.size() == 0){
                    Intent it = new Intent(ListaQuestaoActivity.this, QuestaoActivity.class);
                    startActivity(it);
                }
                else{
                    Intent it = new Intent(ListaQuestaoActivity.this, DescricaoQuestaoActivity.class);
                    startActivity(it);
                }


            }

        });

        buttonRetQuestao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent(ListaQuestaoActivity.this, ListaPlantaActivity.class);
                startActivity(it);

            }
        });

    }

    public void onBackPressed()  {
    }

}
