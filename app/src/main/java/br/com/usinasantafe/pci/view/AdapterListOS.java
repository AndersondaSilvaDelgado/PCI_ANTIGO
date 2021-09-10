package br.com.usinasantafe.pci.view;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pci.R;
import br.com.usinasantafe.pci.model.bean.estatica.OSBean;
import br.com.usinasantafe.pci.model.bean.estatica.PlantaBean;
import br.com.usinasantafe.pci.model.dao.CabecDAO;
import br.com.usinasantafe.pci.model.dao.PlantaDAO;

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

        OSBean osBean = (OSBean) itens.get(position);

        PlantaDAO plantaDAO = new PlantaDAO();
        PlantaBean plantaBean = plantaDAO.getPlanta(osBean.getIdPlantaOS());

        textViewNroOS.setText("OS: " + osBean.getNroOS());
        textViewCdPlanta.setText(plantaBean.getCodPlanta());
        textViewDescrPlanta.setText(plantaBean.getDescrPlanta());
        textViewPeriodoOS.setText(osBean.getDescrPeriodo());

        CabecDAO cabecDAO = new CabecDAO();
        if(cabecDAO.verCabecAbertoOS(osBean)){
            textViewNroOS.setTextColor(Color.RED);
            textViewCdPlanta.setTextColor(Color.RED);
            textViewDescrPlanta.setTextColor(Color.RED);
            textViewPeriodoOS.setTextColor(Color.RED);
        }

        return view;

    }
}
