package br.com.usinasantafe.pci;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.bo.Tempo;
import br.com.usinasantafe.pci.pst.EspecificaPesquisa;
import br.com.usinasantafe.pci.to.estatica.ItemTO;
import br.com.usinasantafe.pci.to.variavel.CabecTO;
import br.com.usinasantafe.pci.to.variavel.RespItemTO;

public class ObsQuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private ItemTO itemTO;
    private EditText editTextObservacao;
    private RespItemTO respItemTO;
    private List respItemList;
    private Button buttonOkObservacao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_obs_questao);

        pciContext = (PCIContext) getApplication();

        editTextObservacao = (EditText) findViewById(R.id.editTextObservacao);
        buttonOkObservacao = (Button) findViewById(R.id.buttonOkObservacao);
        Button buttonCancObservacao = (Button) findViewById(R.id.buttonCancObservacao);

        editTextObservacao.setText("");

        itemTO = pciContext.getItemTO();

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

        respItemList = respItemTO.get(itemArrayList);

        if(respItemList.size() > 0){
            respItemTO = (RespItemTO) respItemList.get(0);
            editTextObservacao.setText(respItemTO.getObsRespItem());
        }

        editTextObservacao.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()>0 && s.subSequence(s.length()-1, s.length()).toString().equalsIgnoreCase("\n")) {
                    editTextObservacao.clearFocus();
                    InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                    imm.hideSoftInputFromWindow(editTextObservacao.getWindowToken(), 0);
                    editTextObservacao.setText(editTextObservacao.getText().toString().replace("\n", ""));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        buttonCancObservacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub
                Intent it = new Intent(ObsQuestaoActivity.this, QuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonOkObservacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                // TODO Auto-generated method stub

                if(!editTextObservacao.getText().toString().equals("")) {

                    if (respItemList.size() == 0) {
                        RespItemTO respItemTO = new RespItemTO();
                        respItemTO.setIdCabRespItem(pciContext.getCabecTO().getIdCabec());
                        respItemTO.setIdItOsMecanRespItem(itemTO.getIdItem());
                        respItemTO.setOpcaoRespItem(1L);
                        respItemTO.setObsRespItem(editTextObservacao.getText().toString());
                        respItemTO.setIdPlantaItem(itemTO.getIdPlantaItem());
                        respItemTO.setDthrRespItem(Tempo.getInstance().data());
                        respItemTO.insert();

                        Intent it = new Intent(ObsQuestaoActivity.this, ListaQuestaoActivity.class);
                        startActivity(it);
                        finish();

                    } else {
                        RespItemTO respItemTO = (RespItemTO) respItemList.get(0);

                        respItemTO.setOpcaoRespItem(1L);
                        respItemTO.setObsRespItem(editTextObservacao.getText().toString());
                        respItemTO.setDthrRespItem(Tempo.getInstance().data());
                        respItemTO.update();

                        Intent it = new Intent(ObsQuestaoActivity.this, ListaQuestaoActivity.class);
                        startActivity(it);
                        finish();

                    }
                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
