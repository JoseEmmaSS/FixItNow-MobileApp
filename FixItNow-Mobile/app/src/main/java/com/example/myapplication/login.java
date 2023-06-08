package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.Principal;
import com.example.myapplication.R;
import com.example.myapplication.Recuperar;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class login extends AppCompatActivity {
    EditText et_correo, et_contrasena;
    Button iniciar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        et_correo = findViewById(R.id.et_correo);
        et_contrasena = findViewById(R.id.et_contrasena);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);

        TextView recContrasenaTextView = findViewById(R.id.rec_contrasena);
        TextView crearCuentaTextView = findViewById(R.id.crearCuenta);


        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = et_correo.getText().toString();
                String contrasena = et_contrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(login.this, "Por favor, ingresa correo y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    validarUsuario("http://192.168.137.1/phpconex/validar_usuario.php", correo, contrasena);
                }
            }
        });

        recContrasenaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Recuperar.class);
                startActivity(intent);
            }
        });

        crearCuentaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(login.this, Registrar.class);
                startActivity(intent);
            }
        });
    }



    private void validarUsuario(String URL, final String correo, final String contrasena) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                // Muestra la respuesta en un Toast
                Toast.makeText(login.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    boolean success = jsonResponse.getBoolean("success");

                    if (success) {
                        // Los datos de inicio de sesión son correctos
                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                        finish(); // Opcional: Finaliza la actividad actual para evitar que el usuario pueda volver atrás con el botón de retroceso
                    } else  {
                        // La respuesta indica que los datos de inicio de sesión son incorrectos
                        Toast.makeText(login.this, "Usuario o contraseña incorrectos", Toast.LENGTH_SHORT).show();
                        // Opcional: Restablece los campos de correo y contraseña
                        et_correo.setText("");
                        et_contrasena.setText("");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(login.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(login.this, "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("correo", correo);
                parametros.put("psw", contrasena);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }

    private boolean isValidResponse(String response) {
        // Aquí puedes definir tu lógica para validar la respuesta del servidor
        // Por ejemplo, si la respuesta es "OK", se considera válida; de lo contrario, se considera inválida
        return response.equals("OK");
    }
}
