package br.com.usinasantafe.pci.view;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import java.util.List;

import br.com.usinasantafe.pci.PCIContext;
import br.com.usinasantafe.pci.R;

public class ObsQuestaoActivity extends ActivityGeneric {

    private PCIContext pciContext;
    private EditText editTextObservacao;
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

        if(!pciContext.getCheckListCTR().verRespItem()){
            editTextObservacao.setText(pciContext.getCheckListCTR().getRespItem().getObsRespItem());
        }
        else{
            editTextObservacao.setText("");
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
                Intent it = new Intent(ObsQuestaoActivity.this, QuestaoActivity.class);
                startActivity(it);
                finish();
            }
        });

        buttonOkObservacao.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                if(!editTextObservacao.getText().toString().equals("")) {

                    pciContext.getCheckListCTR().salvarAtualRespItem(1L, editTextObservacao.getText().toString());

                    Intent it = new Intent(ObsQuestaoActivity.this, ListaQuestaoActivity.class);
                    startActivity(it);
                    finish();

                }
            }
        });

    }

    public void onBackPressed()  {
    }

}
