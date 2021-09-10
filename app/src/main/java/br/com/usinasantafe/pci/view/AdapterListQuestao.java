package br.com.usinasantafe.pci.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.model.bean.estatica.ComponenteBean;
import br.com.usinasantafe.pci.model.bean.estatica.ItemBean;
import br.com.usinasantafe.pci.model.dao.ComponenteDAO;
import br.com.usinasantafe.pci.model.dao.ServicoDAO;

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

        ItemBean itemBean = (ItemBean) itens.get(position);
        textViewPosQuestao.setText("QUESTÃO " + itemBean.getSeqItem());

        ServicoDAO servicoDAO = new ServicoDAO();
        String texto = servicoDAO.getServico(itemBean.getIdServicoItem()).getDescrServico();

        ComponenteDAO componenteDAO = new ComponenteDAO();
        if(componenteDAO.verComponente(itemBean.getIdComponenteItem())){
            ComponenteBean componenteBean = componenteDAO.getComponente(itemBean.getIdComponenteItem());
            texto = texto + "\n" + componenteBean.getCodComponente() + " - " +componenteBean.getDescrComponente();
        }

        textViewDescrQuestao.setText(texto);
        textViewStatusQuestao.setText("");

        if(itemBean.getOpcaoSelItem() == 2L){
            textViewStatusQuestao.setText("CONFORME");
            textViewPosQuestao.setTextColor(Color.BLUE);
            textViewDescrQuestao.setTextColor(Color.BLUE);
            textViewStatusQuestao.setTextColor(Color.BLUE);
        }
        else if(itemBean.getOpcaoSelItem() == 1L){
            textViewStatusQuestao.setText("INCONFORME");
            textViewPosQuestao.setTextColor(Color.RED);
            textViewDescrQuestao.setTextColor(Color.RED);
            textViewStatusQuestao.setTextColor(Color.RED);
        }

        return view;
    }
}
