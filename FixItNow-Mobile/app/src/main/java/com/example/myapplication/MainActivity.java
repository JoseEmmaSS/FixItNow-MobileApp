package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.content.SharedPreferences;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class MainActivity extends AppCompatActivity {
    EditText et_correo, et_contrasena;
    Button iniciar_sesion;

    public static final String PREF_NAME = "LoginPrefs";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_correo = findViewById(R.id.et_correo);
        et_contrasena = findViewById(R.id.et_contrasena);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);

        TextView recContrasenaTextView = findViewById(R.id.rec_contrasena);
        TextView crearCuentaTextView = findViewById(R.id.crearCuenta);

        // Verificar automáticamente el inicio de sesión
        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
        String savedUsername = sharedPreferences.getString(KEY_USERNAME, "");
        String savedPassword = sharedPreferences.getString(KEY_PASSWORD, "");

        if (!savedUsername.isEmpty() && !savedPassword.isEmpty()) {
            validarUsuario("http://192.168.0.7/phpconex/validar_usuario.php", savedUsername, savedPassword);
        }

        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = et_correo.getText().toString();
                String contrasena = et_contrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Por favor, ingresa correo y contraseña", Toast.LENGTH_SHORT).show();
                } else {
                    validarUsuario("http://192.168.0.7/phpconex/validar_usuario.php", correo, contrasena);
                }
            }
        });

        recContrasenaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Recuperar.class);
                startActivity(intent);
            }
        });

        crearCuentaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Registrar.class);
                startActivity(intent);
            }
        });
    }

    private void validarUsuario(String URL, final String correo, final String contrasena) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                Toast.makeText(MainActivity.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.optString("success");

                    if (success.equals("true")) {
                        // Registro exitoso

                        // Guarda los datos de inicio de sesión en las SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME, correo);
                        editor.putString(KEY_PASSWORD, contrasena);
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error al registrar el usuario

                        // Elimina los datos de inicio de sesión guardados en las SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(KEY_USERNAME);
                        editor.remove(KEY_PASSWORD);
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                        // Opcional: Restablecer los campos del formulario
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(MainActivity.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(MainActivity.this, "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
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
}
