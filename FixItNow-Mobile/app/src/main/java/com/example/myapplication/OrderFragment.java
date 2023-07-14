package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class OrderFragment extends Fragment {
    private ListView listView;
    private CustomAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_order, container, false);
        SharedPreferences sharedPreferences = requireActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("KEY_USER_ID", "");
        String consulta_URL = "http://192.168.0.10/phpconex/consultarservicio.php?id=" + userId;

        listView = view.findViewById(R.id.listView);
        adapter = new CustomAdapter(requireContext());
        listView.setAdapter(adapter);

        // Configurar el botón "Agregar"
        Button btnAgregar = view.findViewById(R.id.btnAgregar);
        btnAgregar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireActivity(), Servicios.class);
                startActivity(intent);
            }
        });

        // Realizar la solicitud al servidor
        RequestQueue requestQueue = Volley.newRequestQueue(requireActivity());
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, consulta_URL, null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            // Procesar la respuesta del servidor
                            boolean success = response.getBoolean("success");
                            String jsonResponse = response.toString();
                            Toast.makeText(requireContext(), jsonResponse, Toast.LENGTH_SHORT).show();
                            Log.d("Server Response", jsonResponse);

                            if (success) {
                                Toast.makeText(requireContext(), "Entro al if correctamente", Toast.LENGTH_SHORT).show();
                                // Obtener los datos del JSON y agregarlos al adaptador
                                JSONArray dataArray = response.getJSONArray("data");
// Dentro del bucle for que agrega los elementos al adaptador
                                String servicioId = null;
                                for (int i = 0; i < dataArray.length(); i++) {
                                    JSONObject data = dataArray.getJSONObject(i);
                                    servicioId = data.getString("id");

                                    String tipoServicio = data.getString("tipo_servicio");
                                    int costoServicio = data.getInt("costo_servicio");

                                    // Llenar los campos con los valores obtenidos
                                    String servicio = tipoServicio + " - " + costoServicio + " - " + servicioId;

                                    adapter.addItem(servicio);
                                }

                            } else {
                                String message = response.getString("message");
                                Toast.makeText(requireContext(), message, Toast.LENGTH_SHORT).show();
                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        Toast.makeText(requireContext(), errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("Error", errorMessage);
                    }
                });

        // Agregar la solicitud a la cola de solicitudes de Volley
        requestQueue.add(jsonObjectRequest);

        return view;
    }






}
