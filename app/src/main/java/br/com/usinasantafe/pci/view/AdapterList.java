package br.com.usinasantafe.pci.view;

import android.content.Context;
import android.graphics.Typeface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

import br.com.usinasantafe.pci.R;

/**
 * Created by anderson on 19/10/2015.
 */
public class AdapterList extends BaseAdapter {

    private ArrayList<String> itens;
    private LayoutInflater layoutInflater;

    public AdapterList(Context context, ArrayList<String> itens) {
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
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = layoutInflater.inflate(R.layout.activity_item_lista, null);
        TextView textView = (TextView) convertView.findViewById(R.id.textViewItemList);
        textView.setText(itens.get(position));
        return convertView;
    }
}
