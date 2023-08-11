package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;

public class EditarServicios extends AppCompatActivity {

    private EditText etTipoServicio;
    private EditText etCostoServicio;
    private EditText etNumeroTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_servicios);

        etTipoServicio = findViewById(R.id.etTipoServicio);
        etCostoServicio = findViewById(R.id.etCostoServicio);
        etNumeroTelefono = findViewById(R.id.etNumeroTelefono);
        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(EditarServicios.this, Principal.class);
                startActivity(intent);
                finish(); // Opcional: si deseas finalizar la actividad actual después de iniciar la actividad principal
            }
        });
        String servicioId = getIntent().getStringExtra("servicioId");

        String consultarServicioURL = "http://192.168.0.9/phpconex/ConsutarSerActualizar.php?servicioId=" + servicioId;

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consultarServicioURL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONObject jsonResponse = new JSONObject(response);
                            boolean success = jsonResponse.getBoolean("success");

                            if (success) {
                                JSONArray data = jsonResponse.getJSONArray("data");
                                JSONObject servicio = data.getJSONObject(0);

                                String tipoServicio = servicio.getString("tipo_servicio");
                                double costoServicio = servicio.getDouble("costo_servicio");
                                String numeroTelefono = servicio.getString("numero_telefono");

                                etTipoServicio.setText(tipoServicio);
                                etCostoServicio.setText(String.valueOf(costoServicio));
                                etNumeroTelefono.setText(numeroTelefono);
                            } else {
                                String message = jsonResponse.getString("message");
                                Toast.makeText(EditarServicios.this, message, Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(EditarServicios.this, "Error parsing JSON response", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        String errorMessage = "Error en la solicitud: " + error.getMessage();
                        Toast.makeText(EditarServicios.this, errorMessage, Toast.LENGTH_SHORT).show();
                        Log.e("Error", errorMessage);
                    }
                });

        requestQueue.add(stringRequest);

        Button btnActualizarServicio = findViewById(R.id.btnActualizarServicio);
        btnActualizarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Obtener los valores actuales de los campos de texto
                String tipoServicio = etTipoServicio.getText().toString().trim();
                String costoServicio = etCostoServicio.getText().toString().trim();
                String numeroTelefono = etNumeroTelefono.getText().toString().trim();

                // Crear los parámetros para la solicitud POST
                final String params = "tipo_servicio=" + tipoServicio +
                        "&costo_servicio=" + costoServicio +
                        "&numero_telefono=" + numeroTelefono;

                // Enviar la solicitud POST
                String actualizarServicioURL = "http://192.168.0.10/phpconex/ActualizarServicio.php";
                StringRequest stringRequest = new StringRequest(Request.Method.POST, actualizarServicioURL,
                        new Response.Listener<String>() {
                            @Override
                            public void onResponse(String response) {
                                // Procesar la respuesta del servidor
                                Toast.makeText(EditarServicios.this, "Respuesta del servidor: " + response, Toast.LENGTH_SHORT).show();
                                // Aquí puedes realizar las acciones necesarias con la respuesta obtenida
                            }
                        },
                        new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError error) {
                                // Manejar el error de la solicitud
                                String errorMessage = "Error en la solicitud: " + error.getMessage();
                                Toast.makeText(EditarServicios.this, errorMessage, Toast.LENGTH_SHORT).show();
                                Log.e("Error", errorMessage);
                            }
                        }) {
                    @Override
                    protected HashMap<String, String> getParams() {
                        HashMap<String, String> paramsMap = new HashMap<>();
                        paramsMap.put("servicioId",servicioId);
                        paramsMap.put("tipo_servicio", tipoServicio);
                        paramsMap.put("costo_servicio", costoServicio);
                        paramsMap.put("numero_telefono", numeroTelefono);
                        return paramsMap;
                    }
                };

                requestQueue.add(stringRequest);
            }
        });
    }
}
