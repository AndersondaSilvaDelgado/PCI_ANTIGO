package br.com.usinasantafe.pci;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.estatica.ServicoTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

public class DescricaoQuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_descricao_questao);

        pciContext = (PCIContext) getApplication();

        TextView textViewDescQuestao = (TextView) findViewById(R.id.textViewDescQuestao);
        Button buttonCancDescrQuestao = (Button) findViewById(R.id.buttonCancDescrQuestao);
        Button buttonEditarDescrQuestao = (Button) findViewById(R.id.buttonEditarDescrQuestao);

        CabecTO cabecTO = new CabecTO();
        List cabecList = cabecTO.get("statusCabec", 1L);
        cabecTO = (CabecTO) cabecList.get(0);

        RespItemTO respItemTO = new RespItemTO();
        ArrayList itemArrayList = new ArrayList();

        EspecificaPesquisa pesquisa = new EspecificaPesquisa();
        pesquisa.setCampo("idCabRespItem");
        pesquisa.setValor(cabecTO.getIdCabec());
        itemArrayList.add(pesquisa);

        EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
        pesquisa2.setCampo("idItOsMecanRespItem");
        pesquisa2.setValor(pciContext.getItemTO().getIdItem());
        itemArrayList.add(pesquisa2);

        List respItemList = respItemTO.get(itemArrayList);
        respItemTO = (RespItemTO) respItemList.get(0);

        ItemTO itemTO = new ItemTO();
        List itemList = itemTO.get("idItem", respItemTO.getIdItOsMecanRespItem());
        itemTO = (ItemTO) itemList.get(0);

        String questao = "";

        ServicoTO servicoTO = new ServicoTO();
        List servicoList = servicoTO.get("idServico", itemTO.getIdServicoItem());
        servicoTO = (ServicoTO) servicoList.get(0);
        servicoList.clear();

        questao = "QUESTÃO " + itemTO.getSeqItem() + "\n";
        questao = questao + "DESCR: " + servicoTO.getDescrServico() + "\n";
        if(respItemTO.getOpcaoRespItem() == 1){
            questao = questao + "INCONFORME\n";
        }
        else{
            questao = questao + "CONFORME\n";
        }

        if(respItemTO.getObsRespItem().equals("null")){
            questao = questao + "\nOBS.:";
        }
        else{
            questao = questao + "\nOBS.:" + respItemTO.getObsRespItem();
        }

        textViewDescQuestao.setText(questao);

        buttonEditarDescrQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                Intent it = new Intent( DescricaoQuestaoActivity.this, QuestaoActivity.class);
                startActivity(it);

            }
        });

        buttonCancDescrQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(DescricaoQuestaoActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
            }
        });

    }

    public void onBackPressed()  {
    }

}
