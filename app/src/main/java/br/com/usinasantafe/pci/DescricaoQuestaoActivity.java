package br.com.usinasantafe.pci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.dao.ServicoDAO;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.bean.estatica.ServicoBean;
import br.com.usinasantafe.pci.model.bean.variavel.CabecBean;
import br.com.usinasantafe.pci.model.bean.variavel.RespItemBean;

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

        RespItemBean respItemBean = pciContext.getCheckListCTR().getRespItem();

        String questao = "";

        questao = "QUESTÃO " + pciContext.getCheckListCTR().getItemBean().getSeqItem() + "\n";
        questao = questao + "DESCR: " + pciContext.getCheckListCTR().getServico(pciContext.getCheckListCTR().getItemBean().getIdServicoItem()).getDescrServico() + "\n";
        if(respItemBean.getOpcaoRespItem() == 1){
            questao = questao + "INCONFORME\n";
        }
        else{
            questao = questao + "CONFORME\n";
        }

        if(respItemBean.getObsRespItem().equals("null")){
            questao = questao + "\nOBS.:";
        }
        else{
            questao = questao + "\nOBS.:" + respItemBean.getObsRespItem();
        }

        textViewDescQuestao.setText(questao);

        buttonEditarDescrQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent( DescricaoQuestaoActivity.this, QuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonCancDescrQuestao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DescricaoQuestaoActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}
