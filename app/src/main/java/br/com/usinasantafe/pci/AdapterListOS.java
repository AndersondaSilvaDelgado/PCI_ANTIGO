package br.com.usinasantafe.pci;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import br.com.usinasantafe.pci.to.estatica.OSTO;
import br.com.usinasantafe.pci.to.estatica.PlantaTO;

public class AdapterListOS extends BaseAdapter {

    private ArrayList itens;
    private LayoutInflater layoutInflater;

    public AdapterListOS(Context context, ArrayList itens) {
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

        view = layoutInflater.inflate(R.layout.activity_item_os, null);
        TextView textViewNroOS = (TextView) view.findViewById(R.id.textViewNroOS);
        TextView textViewCdPlanta = (TextView) view.findViewById(R.id.textViewCdPlanta);
        TextView textViewDescrPlanta = (TextView) view.findViewById(R.id.textViewDescrPlanta);
        TextView textViewPeriodoOS = (TextView) view.findViewById(R.id.textViewPeriodoOS);

        OSTO osTO = (OSTO) itens.get(position);

        PlantaTO plantaTO = new PlantaTO();
        List plantaList = plantaTO.get("idPlanta", osTO.getIdPlantaOS());
        plantaTO = (PlantaTO) plantaList.get(0);
        plantaList.clear();

        textViewNroOS.setText("OS: " + osTO.getNroOS());
        textViewCdPlanta.setText(plantaTO.getCodPlanta());
        textViewDescrPlanta.setText(plantaTO.getDescrPlanta());
        textViewPeriodoOS.setText(osTO.getDescrPeriodo());

        if(osTO.getStatusOS() == 1L){
            textViewNroOS.setTextColor(Color.RED);
            textViewCdPlanta.setTextColor(Color.RED);
            textViewDescrPlanta.setTextColor(Color.RED);
            textViewPeriodoOS.setTextColor(Color.RED);
        }

        return view;

    }
}
