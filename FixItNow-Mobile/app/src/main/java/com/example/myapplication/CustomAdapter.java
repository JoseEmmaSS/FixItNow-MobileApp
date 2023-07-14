package com.example.myapplication;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.ArrayList;

public class CustomAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<String> items;
    private String servicioId;

    public CustomAdapter(Context context) {
        this.context = context;
        this.items = new ArrayList<>();
    }

    public void addItem(String item) {
        items.add(item);
        notifyDataSetChanged();
    }

    public void removeItem(int position) {
        items.remove(position);
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

        TextView txtItemName = convertView.findViewById(R.id.txtItemName);
        Button btnEdit = convertView.findViewById(R.id.btnEdit);
        Button btnDelete = convertView.findViewById(R.id.btnDelete);

        String item = items.get(position);
        txtItemName.setText(item);

        // Obtener el servicioId correspondiente a la posición actual
        String servicioItem = items.get(position);
        String[] parts = servicioItem.split(" - ");
        String servicioId = parts[2];

        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Utilizar el servicioId en la función de eliminación
                AlertDialog.Builder builder = new AlertDialog.Builder(context);
                builder.setTitle("Confirmar eliminación");
                builder.setMessage("¿Estás seguro de que deseas eliminar este registro?");

                builder.setPositiveButton("Sí", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // Realizar la solicitud de eliminación al servidor
                        String eliminarServicioURL = "http://192.168.0.10/phpconex/eliminarservicio.php?id=" + servicioId;

                        RequestQueue requestQueue = Volley.newRequestQueue(context);
                        StringRequest stringRequest = new StringRequest(Request.Method.GET, eliminarServicioURL,
                                new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(String response) {
                                        Toast.makeText(context, "Servicio eliminado correctamente", Toast.LENGTH_SHORT).show();
                                        removeItem(position);
                                    }

                                },
                                new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                                        Toast.makeText(context, errorMessage, Toast.LENGTH_SHORT).show();
                                        Log.e("Error", errorMessage);
                                    }
                                });

                        requestQueue.add(stringRequest);
                    }
                });

                builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        // No hacer nada, simplemente cerrar el diálogo
                    }
                });

                AlertDialog dialog = builder.create();
                dialog.show();
            }
        });

        btnEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, EditarServicios.class);
                intent.putExtra("servicioId", servicioId);
                context.startActivity(intent);
            }
        });

        return convertView;
    }

}
