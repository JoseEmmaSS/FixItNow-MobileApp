package com.example.myapplication;

// Importaciones necesarias
import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.biometric.BiometricPrompt;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

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
import java.util.concurrent.Executor;

public class MainActivity extends AppCompatActivity {
    private static final int REQUEST_CODE_BIOMETRIC = 1001;

    public static final String PREF_NAME = "LoginPrefs";
    public static final String KEY_USERID = "username";
    public static final String KEY_USERNAME = "username";
    public static final String KEY_PASSWORD = "password";
    public static final String KEY_DATA_STORED = "data_stored";

    private EditText et_correo, et_contrasena;
    private Button iniciar_sesion;
    private TextView recContrasenaTextView, crearCuentaTextView, Cambiarlogin;

    private boolean isBiometricAuthenticated = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Inicializa las vistas
        et_correo = findViewById(R.id.et_correo);
        et_contrasena = findViewById(R.id.et_contrasena);
        iniciar_sesion = findViewById(R.id.iniciar_sesion);
        recContrasenaTextView = findViewById(R.id.rec_contrasena);
        crearCuentaTextView = findViewById(R.id.crearCuenta);
        Cambiarlogin = findViewById(R.id.Empresas);
        // Verifica si el dispositivo tiene el hardware y es compatible con la autenticación biométrica
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(this, Manifest.permission.USE_BIOMETRIC) == PackageManager.PERMISSION_GRANTED) {
                // El dispositivo es compatible con la autenticación biométrica, continúa con la lógica de tu aplicación
                showBiometricPrompt();
            } else {
                // Solicita permiso para usar la autenticación biométrica
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.USE_BIOMETRIC}, REQUEST_CODE_BIOMETRIC);
            }
        } else {
            // La versión de Android es anterior a 6.0, que no admite la autenticación biométrica
            Toast.makeText(this, "La autenticación biométrica no es compatible en este dispositivo.", Toast.LENGTH_LONG).show();
        }

        // Establece listeners para el botón de inicio de sesión, recuperar contraseña y crear cuenta
        iniciar_sesion.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String correo = et_correo.getText().toString();
                String contrasena = et_contrasena.getText().toString();

                if (correo.isEmpty() || contrasena.isEmpty()) {
                    Toast.makeText(MainActivity.this, "Please enter email and password.", Toast.LENGTH_SHORT).show();
                } else {
                    // Perform the regular login authentication
                    validarUsuario("http://10.0.11.118/phpconex/validar_usuario.php", correo, contrasena);
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
        Cambiarlogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, Empresas.class);
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

    // Método para validar al usuario con el servidor
    private void validarUsuario(String URL, final String correo, final String contrasena) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                Toast.makeText(MainActivity.this, "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.optString("success");
                    String userId = jsonResponse.optString("id");
                    String username = jsonResponse.optString("name");
                    if (success.equals("true")) {
                        // Registro exitoso

                        // Guarda los datos de inicio de sesión en las SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString(KEY_USERNAME, correo);
                        editor.putString(KEY_PASSWORD, contrasena);
                        editor.putString("KEY_USER_ID", userId);
                        editor.putString("KEY_USER_NAME", username);
                        editor.putBoolean(KEY_DATA_STORED, true); // Marcar como almacenados
                        editor.apply();

                        Intent intent = new Intent(getApplicationContext(), Principal.class);
                        startActivity(intent);
                        finish();
                    } else {
                        // Error al registrar al usuario

                        // Elimina los datos de inicio de sesión guardados en las SharedPreferences
                        SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.remove(KEY_USERNAME);
                        editor.remove(KEY_PASSWORD);
                        editor.putBoolean(KEY_DATA_STORED, false); // Marcar como no almacenados
                        editor.apply();

                        Toast.makeText(MainActivity.this, "Error al registrar al usuario", Toast.LENGTH_SHORT).show();
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

    // Método para mostrar el diálogo de autenticación biométrica
    private void showBiometricPrompt() {
        Executor executor = ContextCompat.getMainExecutor(this);
        BiometricPrompt biometricPrompt = new BiometricPrompt(this, executor, new BiometricPrompt.AuthenticationCallback() {
            @Override
            public void onAuthenticationError(int errorCode, CharSequence errString) {
                //  SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
                //  SharedPreferences.Editor editor = sharedPreferences.edit();
                // editor.remove(MainActivity.KEY_USERNAME);
                // editor.remove(MainActivity.KEY_PASSWORD);
                // editor.apply();
                Toast.makeText(MainActivity.this, "Error de autenticación otro: " + errString, Toast.LENGTH_SHORT).show();

            }

            @Override
            public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
                // Utiliza los valores previamente almacenados para enviarlos al servidor PHP
                SharedPreferences sharedPreferences = getSharedPreferences(PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                String correo = sharedPreferences.getString(KEY_USERNAME, "");
                String contrasena = sharedPreferences.getString(KEY_PASSWORD, "");
                if (correo.isEmpty() || contrasena.isEmpty()) {
                    // Datos vacíos, muestra mensaje de error
                    Toast.makeText(MainActivity.this, "Error de autenticación, intente de otro modo", Toast.LENGTH_SHORT).show();
                } else {
                    // Luego utiliza los valores para enviarlos al servidor PHP
                    validarUsuario("http://10.0.11.118/phpconex/validar_usuario.php", correo, contrasena);
                }
            }

            @Override
            public void onAuthenticationFailed() {
                // Eliminar los datos de inicio de sesión guardados en las SharedPreferences del MainActivity
                // SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
                //SharedPreferences.Editor editor = sharedPreferences.edit();
                // editor.remove(MainActivity.KEY_USERNAME);
                // editor.remove(MainActivity.KEY_PASSWORD);
                //editor.apply();

                Toast.makeText(MainActivity.this, "Error se cancelo ", Toast.LENGTH_SHORT).show();
                // Opcional: Restablecer los campos del formulario
            }
        });

        BiometricPrompt.PromptInfo promptInfo = new BiometricPrompt.PromptInfo.Builder()
                .setTitle("Autenticación de usuario")
                .setNegativeButtonText("Cancelar")
                .build();

        biometricPrompt.authenticate(promptInfo);
    }
}
