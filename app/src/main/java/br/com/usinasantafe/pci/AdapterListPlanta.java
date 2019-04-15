package br.com.usinasantafe.pci;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.to.estatica.PlantaTO;
import br.com.usinasantafe.pci.to.variavel.PlantaCabecTO;

/**
 * Created by anderson on 08/03/2018.
 */

public class AdapterListPlanta extends BaseAdapter {

    private ArrayList itens;
    private LayoutInflater layoutInflater;
    private List resp;

    public AdapterListPlanta(Context context, ArrayList itens) {
        // TODO Auto-generated constructor stub
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

        PlantaCabecTO plantaCabecTO = (PlantaCabecTO) itens.get(position);

        PlantaTO plantaTO = new PlantaTO();
        List plantaList = plantaTO.get("idPlanta", plantaCabecTO.getIdPlanta());
        plantaTO = (PlantaTO) plantaList.get(0);
        plantaList.clear();

        textViewPlantaCD.setText("PLANTA: " + plantaTO.getCodPlanta());
        textViewPlantaDescr.setText(plantaTO.getDescrPlanta());

        if(plantaCabecTO.getStatusPlantaCabec() == 1L) {
            textViewPlantaCD.setTextColor(Color.RED);
            textViewPlantaDescr.setTextColor(Color.RED);
        }
        else if(plantaCabecTO.getStatusPlantaCabec() == 2L) {
            textViewPlantaCD.setTextColor(Color.BLUE);
            textViewPlantaDescr.setTextColor(Color.BLUE);
        }

        return view;
    }
}