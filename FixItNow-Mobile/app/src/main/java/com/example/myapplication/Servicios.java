package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.HashMap;
import java.util.Map;

public class Servicios extends AppCompatActivity {
    private EditText etTipoServicio, etCostoServicio, etNumeroTelefono;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_servicios);

        etTipoServicio = findViewById(R.id.etTipoServicio);
        etCostoServicio = findViewById(R.id.etCostoServicio);
        etNumeroTelefono = findViewById(R.id.etNumeroTelefono);

        Button btnRegresar = findViewById(R.id.btnRegresar);
        btnRegresar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Servicios.this, Principal.class);
                startActivity(intent);
                finish(); // Opcional: si deseas finalizar la actividad actual después de iniciar la actividad principal
            }
        });

        Button btnAgregarServicio = findViewById(R.id.btnAgregarServicio);
        btnAgregarServicio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                guardarServicio();
            }
        });
    }

    private void guardarServicio() {
        // Obtener los valores de los campos de entrada
        String tipoServicio = etTipoServicio.getText().toString();
        String costoServicio = etCostoServicio.getText().toString();
        String numeroTelefono = etNumeroTelefono.getText().toString();

        // URL del archivo PHP en el servidor
        String url = "http://192.168.0.9/phpconex/AgregarServicio.php";

        // Crear una solicitud HTTP POST utilizando Volley
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Procesar la respuesta del servidor
                        Toast.makeText(Servicios.this, response, Toast.LENGTH_SHORT).show();
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // Manejar el error de la solicitud
                        Toast.makeText(Servicios.this, "Error al guardar el servicio", Toast.LENGTH_SHORT).show();
                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("KEY_USER_ID", "");
                String username = sharedPreferences.getString("KEY_USER_NAME", "");
                // Parámetros para enviar al archivo PHP
                Map<String, String> params = new HashMap<>();
                params.put("tipo_servicio", tipoServicio);
                params.put("costo_servicio", costoServicio);
                params.put("numero_telefono", numeroTelefono);
                params.put("idusuario", userId);
                params.put("empresa", username);
                return params;
            }
        };

        // Agregar la solicitud a la cola de solicitudes de Volley
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
