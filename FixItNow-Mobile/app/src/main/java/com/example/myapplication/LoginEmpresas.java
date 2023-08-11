package com.example.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginEmpresas extends AppCompatActivity {
    EditText et_nombre, et_usuario, et_contrasena,et_confirmarcontra, et_correo, et_telefono;
    Button crear_cuenta;
    private TextView crearCuentaEmpTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_empresas);

        et_nombre = findViewById(R.id.et_nombre);
        et_usuario = findViewById(R.id.et_usuario);
        et_contrasena = findViewById(R.id.et_contrasena);
        et_correo = findViewById(R.id.et_correo);
        et_telefono = findViewById(R.id.et_telefono);
        crear_cuenta = findViewById(R.id.crear_cuenta);
        et_confirmarcontra = findViewById(R.id.et_confirmarcontra);

        crear_cuenta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String nombre = et_nombre.getText().toString();
                String usuario = et_usuario.getText().toString();
                String contrasena = et_contrasena.getText().toString();
                String correo = et_correo.getText().toString();
                String telefono = et_telefono.getText().toString();
                String confirmarContrasena = et_confirmarcontra.getText().toString();
                if (nombre.isEmpty() || usuario.isEmpty() || contrasena.isEmpty() || correo.isEmpty() || telefono.isEmpty()) {
                    Toast.makeText(LoginEmpresas.this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show();
                } else if (validarContrasena(contrasena) && validarCorreo(correo)) {
                    if (contrasena.equals(confirmarContrasena)) {
                        registrarUsuario("http://192.168.0.9/phpconex/IntertarEmpresa.php", nombre, usuario, contrasena, correo, telefono);
                    } else {
                        Toast.makeText(LoginEmpresas.this, "La contraseña y la confirmación de contraseña no coinciden", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    private boolean validarContrasena(String contrasena) {
        // Verificar si la contraseña contiene al menos una letra minúscula
        if (!contrasena.matches(".*[a-z].*")) {
            Toast.makeText(LoginEmpresas.this, "La contraseña debe contener al menos una letra minúscula", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar si la contraseña contiene al menos una letra mayúscula
        if (!contrasena.matches(".*[A-Z].*")) {
            Toast.makeText(LoginEmpresas.this, "La contraseña debe contener al menos una letra mayúscula", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar si la contraseña contiene al menos un símbolo especial
        if (!contrasena.matches(".*[^a-zA-Z0-9].*")) {
            Toast.makeText(LoginEmpresas.this, "La contraseña debe contener al menos un símbolo especial", Toast.LENGTH_SHORT).show();
            return false;
        }

        return true;
    }

    private boolean validarCorreo(String correo) {
        // Verificar si el correo tiene la estructura adecuada
        if (!correo.matches("^\\w+([.-]?\\w+)*@\\w+([.-]?\\w+)*(\\.\\w{2,3})+$")) {
            Toast.makeText(LoginEmpresas.this, "El correo electrónico no tiene la estructura correcta", Toast.LENGTH_SHORT).show();
            return false;
        }

        // Verificar si el correo es de Gmail
        if (correo.matches("^\\w+([.-]?\\w+)*@gmail\\.com$")) {
            Toast.makeText(LoginEmpresas.this, "Correo Gmail válido", Toast.LENGTH_SHORT).show();
            return true;
        }

        // Verificar si el correo es de Outlook
        if (correo.matches("^\\w+([.-]?\\w+)*@outlook\\.com$")) {
            Toast.makeText(LoginEmpresas.this, "Correo Outlook válido", Toast.LENGTH_SHORT).show();
            return true;
        }

        // Verificar si el correo es de un dominio específico (micorreo.upp.edu.mx)
        if (correo.matches("^\\w+([.-]?\\w+)*@micorreo\\.upp\\.edu\\.mx$")) {
            Toast.makeText(LoginEmpresas.this, "Correo con dominio específico válido", Toast.LENGTH_SHORT).show();
            return true;
        }

        Toast.makeText(LoginEmpresas.this, "Correo electrónico no válido", Toast.LENGTH_SHORT).show();
        return false;
    }


    private void registrarUsuario(String URL, final String name, final String user, final String psw, final String correo, final String telefono) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                // Muestra la respuesta en un Toast
                Toast.makeText(LoginEmpresas.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.optString("success");

                    if (success.equals("true")) {
                        // Registro exitoso
                        Toast.makeText(LoginEmpresas.this, "Usuario registrado correctamente", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                        startActivity(intent);
                        finish();

                    } else {
                        // Error al registrar el usuario
                        Toast.makeText(LoginEmpresas.this, "Error al registrar el usuario", Toast.LENGTH_SHORT).show();
                        // Opcional: Restablecer los campos del formulario
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(LoginEmpresas.this, "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(LoginEmpresas.this, "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("name", name);
                parametros.put("user", user);
                parametros.put("psw", psw);
                parametros.put("correo", correo);
                parametros.put("telefono", telefono);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);
    }
}
