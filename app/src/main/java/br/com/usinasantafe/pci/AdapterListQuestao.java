package br.com.usinasantafe.pci;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListQuestao extends BaseAdapter {

    private List itens;
    private LayoutInflater layoutInflater;

    public AdapterListQuestao(Context context, List itens) {
        this.itens = itens;
        layoutInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return itens.size();
    }

    @Override
    public Object getItem(int position) {
        return itens.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {

        view = layoutInflater.inflate(R.layout.activity_item_questao, null);
        TextView textViewPosQuestao = (TextView) view.findViewById(R.id.textViewPosQuestao);
        TextView textViewDescrQuestao = (TextView) view.findViewById(R.id.textViewDescrQuestao);
        TextView textViewStatusQuestao = (TextView) view.findViewById(R.id.textViewStatusQuestao);

        ItemTO itemTO = (ItemTO) itens.get(position);

        ServicoTO servicoTO = new ServicoTO();
        List servicoList = servicoTO.get("idServico", itemTO.getIdServicoItem());
        servicoTO = (ServicoTO) servicoList.get(0);
        servicoList.clear();

        textViewPosQuestao.setText("QUESTÃO " + itemTO.getSeqItem());

        String texto = servicoTO.getDescrServico();
        ComponenteTO componenteTO = new ComponenteTO();
        List componenteList = componenteTO.get("idComponente", itemTO.getIdComponenteItem());
        if(componenteList.size() > 0){
            componenteTO = (ComponenteTO) componenteList.get(0);
            texto = texto + "\n" + componenteTO.getCodComponente() + " - " +componenteTO.getDescrComponente();
        }
        componenteList.clear();

        textViewDescrQuestao.setText(texto);
        textViewStatusQuestao.setText("");

        if(itemTO.getOpcaoSelItem() == 2L){
            textViewStatusQuestao.setText("CONFORME");
            textViewPosQuestao.setTextColor(Color.BLUE);
            textViewDescrQuestao.setTextColor(Color.BLUE);
            textViewStatusQuestao.setTextColor(Color.BLUE);
        }
        else if(itemTO.getOpcaoSelItem() == 1L){
            textViewStatusQuestao.setText("INCONFORME");
            textViewPosQuestao.setTextColor(Color.RED);
            textViewDescrQuestao.setTextColor(Color.RED);
            textViewStatusQuestao.setTextColor(Color.RED);
        }

        return view;
    }
}
