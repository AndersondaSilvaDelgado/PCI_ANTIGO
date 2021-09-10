package br.com.usinasantafe.pci.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.model.bean.estatica.PlantaBean;
import br.com.usinasantafe.pci.model.bean.variavel.PlantaCabecBean;
import br.com.usinasantafe.pci.model.dao.PlantaDAO;

/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListPlanta extends BaseAdapter {

    private ArrayList itens;
    private LayoutInflater layoutInflater;
    private List resp;

    public AdapterListPlanta(Context context, ArrayList itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_planta, null);
        TextView textViewPlantaCD = (TextView) view.findViewById(R.id.textViewPlantaCD);
        TextView textViewPlantaDescr = (TextView) view.findViewById(R.id.textViewPlantaDescr);

        PlantaCabecBean plantaCabecBean = (PlantaCabecBean) itens.get(position);

        PlantaDAO plantaDAO = new PlantaDAO();
        PlantaBean plantaBean = plantaDAO.getPlanta(plantaCabecBean.getIdPlanta());

        textViewPlantaCD.setText("PLANTA: " + plantaBean.getCodPlanta());
        textViewPlantaDescr.setText(plantaBean.getDescrPlanta());

        if(plantaCabecBean.getStatusPlantaCabec() == 1L) {
            textViewPlantaCD.setTextColor(Color.RED);
            textViewPlantaDescr.setTextColor(Color.RED);
        }
        else if(plantaCabecBean.getStatusPlantaCabec() == 2L) {
            textViewPlantaCD.setTextColor(Color.BLUE);
            textViewPlantaDescr.setTextColor(Color.BLUE);
        }

        return view;
    }
}
