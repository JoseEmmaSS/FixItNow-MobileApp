package com.example.myapplication;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class SolicitudFragment extends Fragment implements PedidoAdapter.OnDeleteClickListener {

    private RecyclerView recyclerView;
    private PedidoAdapter pedidoAdapter;
    private String consulta_URL;

    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_solicitud, container, false);

        recyclerView = view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        pedidoAdapter = new PedidoAdapter(requireContext());
        pedidoAdapter.setOnDeleteClickListener(this);
        recyclerView.setAdapter(pedidoAdapter);

        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("KEY_USER_ID", "");
        consulta_URL = "http://192.168.0.9/phpconex/consultarpedidos.php?usuarioid=" + userId;

        new ConsultarPedidosTask().execute(consulta_URL);

        return view;
    }

    private class ConsultarPedidosTask extends AsyncTask<String, Void, List<Pedido>> {
        @Override
        protected List<Pedido> doInBackground(String... urls) {
            List<Pedido> pedidos = new ArrayList<>();
            try {
                URL url = new URL(urls[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                String line;
                StringBuilder result = new StringBuilder();
                while ((line = reader.readLine()) != null) {
                    result.append(line);
                }
                reader.close();
                inputStream.close();
                connection.disconnect();

                JSONArray jsonArray = new JSONArray(result.toString());
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String id = jsonObject.getString("id");
                    String nombreEmpresa = jsonObject.getString("empresa");
                    String telefono = jsonObject.getString("telefono");
                    String costo = jsonObject.getString("costo_servicio");

                    Pedido pedido = new Pedido(id, nombreEmpresa, telefono, costo);
                    pedidos.add(pedido);
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return pedidos;
        }

        @Override
        protected void onPostExecute(List<Pedido> pedidos) {
            pedidoAdapter.setPedidos(pedidos);
        }
    }
    @Override
    public void onDeleteClick(int pedidoId) {
        showDeleteConfirmationDialog(pedidoId);
    }
    @Override
    public void onItemDeleted() {
        new ConsultarPedidosTask().execute(consulta_URL);
    }
    private void showDeleteConfirmationDialog(int pedidoId) {
        // ... (existing code)
    }
}
