package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Recuperar extends AppCompatActivity {
    EditText et_correo;
    Button btnrecuperar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar2);
        et_correo = findViewById(R.id.et_correo);
        btnrecuperar = findViewById(R.id.btnRecuperar);

        btnrecuperar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = et_correo.getText().toString();

                if (TextUtils.isEmpty(correo)) {
                    Toast.makeText(Recuperar.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else {
                    registrarUsuario("http://192.168.0.9/phpconex/Recuperarcontrasena.php", correo);
                }
            }
        });
    }

    private void registrarUsuario(String URL, String correo) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                Toast.makeText(Recuperar.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);

                    String success = jsonResponse.getString("success");

                    if (success.equals("true")) {
                        // Registro exitoso
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error al registrar el usuario
                        Toast.makeText(Recuperar.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                        // Opcional: Restablecer los campos del formulario
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(Recuperar.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(Recuperar.this, "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<>();
                parametros.put("correo", correo);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
