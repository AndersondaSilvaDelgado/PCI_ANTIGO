package br.com.usinasantafe.pci;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.model.bean.estatica.ComponenteBean;
import br.com.usinasantafe.pci.util.Tempo;
import br.com.usinasantafe.pci.model.pst.EspecificaPesquisa;

public class QuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questao);

        pciContext = (PCIContext) getApplication();

        TextView textViewItemQuestao = (TextView) findViewById(R.id.textViewItemQuestao);
        Button buttonConforme = (Button) findViewById(R.id.buttonConforme);
        Button buttonNaoConforme = (Button) findViewById(R.id.buttonNaoConforme);
        Button buttonRetQuestao = (Button) findViewById(R.id.buttonRetQuestao);

        String texto = pciContext.getCheckListCTR().getServico(pciContext.getCheckListCTR().getItemBean().getIdServicoItem()).getDescrServico();

        if(pciContext.getCheckListCTR().verComponente(pciContext.getCheckListCTR().getItemBean().getIdServicoItem())){
            ComponenteBean componenteBean = pciContext.getCheckListCTR().getComponente(pciContext.getCheckListCTR().getItemBean().getIdServicoItem());
            texto = texto + "\n" + componenteBean.getCodComponente() + " - " +componenteBean.getDescrComponente();
        }

        textViewItemQuestao.setText(texto);

        buttonRetQuestao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuestaoActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonConforme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                pciContext.getCheckListCTR().salvarAtualRespItem(2L, "null");

                Intent it = new Intent(QuestaoActivity.this, ListaQuestaoActivity.class);
                startActivity(it);
                finish();

            }
        });

        buttonNaoConforme.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent it = new Intent(QuestaoActivity.this, ObsQuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

    }

    public void onBackPressed()  {
    }

}
