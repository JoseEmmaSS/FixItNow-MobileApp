package com.example.myapplication;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> items;

    public CustomAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.list_item, parent, false);
        }

        // Obtén referencias a los elementos del diseño
        TextView txtItemName = convertView.findViewById(R.id.txtItemName);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        // Establece los valores de los elementos según los datos de la lista
        String item = items.get(position);
        txtItemName.setText(item);

        // Agrega la lógica para modificar y eliminar el elemento según sea necesario

        return convertView;
    }
}
