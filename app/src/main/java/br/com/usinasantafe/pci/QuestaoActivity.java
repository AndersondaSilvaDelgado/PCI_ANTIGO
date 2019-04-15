package br.com.usinasantafe.pci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.bo.Tempo;
import br.com.usinasantafe.pci.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.to.estatica.ComponenteTO;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.estatica.ServicoTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

public class QuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ItemTO itemTO;
    private RespItemTO respItemTO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);

        pciContext = (PCIContext) getApplication();

        TextView textViewItemQuestao = (TextView) findViewById(R.id.textViewItemQuestao);
        Button buttonConforme = (Button) findViewById(R.id.buttonConforme);
        Button buttonNaoConforme = (Button) findViewById(R.id.buttonNaoConforme);
        Button buttonRetQuestao = (Button) findViewById(R.id.buttonRetQuestao);

        itemTO = pciContext.getItemTO();

        ServicoTO servicoTO = new ServicoTO();
        List servicoList = servicoTO.get("idServico", itemTO.getIdServicoItem());
        servicoTO = (ServicoTO) servicoList.get(0);
        servicoList.clear();

        String texto = servicoTO.getDescrServico();

        ComponenteTO componenteTO = new ComponenteTO();
        List componenteList = componenteTO.get("idComponente", itemTO.getIdComponenteItem());
        if(componenteList.size() > 0){
            componenteTO = (ComponenteTO) componenteList.get(0);
            texto = texto + "\n" + componenteTO.getCodComponente() + " - " +componenteTO.getDescrComponente();
        }
        componenteList.clear();

        textViewItemQuestao.setText(texto);

        buttonRetQuestao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(QuestaoActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonConforme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                RespItemTO respItemTO = new RespItemTO();
                ArrayList itemArrayList = new ArrayList();

                EspecificaPesquisa pesquisa = new EspecificaPesquisa();
                pesquisa.setCampo("idCabRespItem");
                pesquisa.setValor(pciContext.getCabecTO().getIdCabec());
                itemArrayList.add(pesquisa);

                EspecificaPesquisa pesquisa2 = new EspecificaPesquisa();
                pesquisa2.setCampo("idItOsMecanRespItem");
                pesquisa2.setValor(itemTO.getIdItem());
                itemArrayList.add(pesquisa2);

                List respItemList = respItemTO.get(itemArrayList);

                if(respItemList.size() == 0){
                    respItemTO = new RespItemTO();
                    respItemTO.setIdCabRespItem(pciContext.getCabecTO().getIdCabec());
                    respItemTO.setIdItOsMecanRespItem(itemTO.getIdItem());
                    respItemTO.setOpcaoRespItem(2L);
                    respItemTO.setObsRespItem("null");
                    respItemTO.setIdPlantaItem(itemTO.getIdPlantaItem());
                    respItemTO.setDthrRespItem(Tempo.getInstance().data());
                    respItemTO.insert();

                    Intent it = new Intent(QuestaoActivity.this, ListaQuestaoActivity.class);
                    startActivity(it);
                    finish();

                }
                else{
                    respItemTO = (RespItemTO) respItemList.get(0);

                    respItemTO.setOpcaoRespItem(2L);
                    respItemTO.setObsRespItem("null");
                    respItemTO.setDthrRespItem(Tempo.getInstance().data());
                    respItemTO.update();

                    Intent it = new Intent(QuestaoActivity.this, ListaQuestaoActivity.class);
                    startActivity(it);
                    finish();

                }

            }
        });

        buttonNaoConforme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(QuestaoActivity.this, ObsQuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}
