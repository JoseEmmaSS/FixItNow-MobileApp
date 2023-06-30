package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;


import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private static final String PHP_URL = "http://192.168.0.10/phpconex/Extraer.php";

    private static final String PHP_ACTUALIZAR = "http://192.168.0.10/phpconex/Actualizar.php";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText telefonoEditText;
    private EditText emailEditText;
    private Button changePhotoButton;
    private Button updateButton;

    String foto="A";
    String id="0";
    private Uri selectedImageUri;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        profileImageView = view.findViewById(R.id.profileImageView);
        usernameEditText = view.findViewById(R.id.usernameEditText);
        nameEditText = view.findViewById(R.id.nameEditText);
        telefonoEditText = view.findViewById(R.id.telefonoEditText);
        emailEditText = view.findViewById(R.id.emailEditText);
        changePhotoButton = view.findViewById(R.id.changePhotoButton);
        updateButton = view.findViewById(R.id.updateButton);

        // Obtén el nombre de usuario guardado en las SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        String correo = sharedPreferences.getString(MainActivity.KEY_USERNAME, "");
        Toast.makeText(getActivity(), "Correo del usuario: " + correo, Toast.LENGTH_SHORT).show();
        // Enviar la consulta al archivo PHP
        sendQueryToPHP(correo);

        usernameEditText.setText(MainActivity.KEY_USERNAME);
        //nameEditText.setText(MainActivity.KEY_);

        // Asignar un listener al botón de cambio de foto
        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });
        updateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actualizarUsuario(PHP_ACTUALIZAR,  nameEditText.getText().toString(), usernameEditText.getText().toString(), emailEditText.getText().toString(), telefonoEditText.getText().toString());
            }
        });

        return view;
    }

    private void sendQueryToPHP(String correo) {
        String url = PHP_URL + "?correo=" + correo;

        // Ejecutar la tarea asíncrona para enviar la consulta y recibir la respuesta
        new QueryTask().execute(url);
    }
    private void sendFotoToPHP(String correo) {
        String url = PHP_ACTUALIZAR + "?correo=" + correo;

        // Ejecutar la tarea asíncrona para enviar la consulta y recibir la respuesta
        new QueryTask().execute(url);
    }

    private void openImagePicker() {
        // Abre una actividad para seleccionar o tomar una foto
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(intent, REQUEST_IMAGE_PICK);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == Activity.RESULT_OK) {
            if (requestCode == REQUEST_IMAGE_PICK) {
                // La imagen ha sido seleccionada desde la galería
                Uri imageUri = data.getData();

                ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
                Bitmap bitmap = null;
                try {
                    bitmap = MediaStore.Images.Media.getBitmap(this.getContext().getContentResolver(), imageUri);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
                byte[] imageBytes = byteArrayOutputStream.toByteArray();
                foto = Base64.encodeToString(imageBytes, Base64.DEFAULT);

                imageBytes = Base64.decode(foto, Base64.DEFAULT);
                Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                profileImageView.setImageBitmap(decodedImage);

            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // La foto ha sido tomada con la cámara
                // Aquí puedes obtener la imagen capturada desde el intent de la cámara y realizar las operaciones necesarias
            }
        }
    }

    private void actualizarUsuario(String URL, final String name, final String user, final String correo, final String telefono) {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Respuesta", response); // Imprime la respuesta en el registro de Log

                // Muestra la respuesta en un Toast
                Toast.makeText(getActivity(), "Respuesta: " + response, Toast.LENGTH_SHORT).show();

                try {
                    JSONObject jsonResponse = new JSONObject(response);
                    String success = jsonResponse.optString("success");

                    if (success.equals("true")) {
                        // Registro exitoso
                        Toast.makeText(getActivity(), "Usuario actualizado correctamente", Toast.LENGTH_SHORT).show();

                    } else {
                        // Error al registrar el usuario
                        Toast.makeText(getActivity(), "Error al actualizar el usuario", Toast.LENGTH_SHORT).show();
                        // Opcional: Restablecer los campos del formulario
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    Toast.makeText(getActivity(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Ocurrió un error en la solicitud", Toast.LENGTH_SHORT).show();
            }
        }) {
            @Nullable
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parametros = new HashMap<String, String>();
                parametros.put("id", id);
                parametros.put("name", name);
                parametros.put("username", user);
                parametros.put("foto", foto);
                parametros.put("correo", correo);
                parametros.put("telefono", telefono);
                return parametros;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);
    }


    private class QueryTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... urls) {
            String result = "";
            HttpURLConnection connection = null;

            try {
                URL url = new URL(urls[0]);
                connection = (HttpURLConnection) url.openConnection();
                connection.setRequestMethod("GET");

                // Leer la respuesta del servidor
                InputStream inputStream = connection.getInputStream();
                BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                StringBuilder stringBuilder = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    stringBuilder.append(line);
                }

                result = stringBuilder.toString();

                reader.close();
                inputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            } finally {
                if (connection != null) {
                    connection.disconnect();
                }
            }

            return result;
        }

        @Override
        protected void onPostExecute(String result) {
            // Mostrar la respuesta recibida en un Toast
            Toast.makeText(getActivity(), result, Toast.LENGTH_SHORT).show();

            try {
                // Parsear la respuesta JSON
                JSONArray jsonArray = new JSONArray(result);
                if (jsonArray.length() > 0) {
                    JSONObject jsonObject = jsonArray.getJSONObject(0);

                    // Obtener los valores del perfil
                    id = jsonObject.getString("id");
                    String username = jsonObject.getString("user");
                    String name = jsonObject.getString("name");
                    String telefono = jsonObject.getString("telefono");
                    String email = jsonObject.getString("correo");
                    String fotos = jsonObject.getString("fotoPerfil");

                    byte[] imageBytes = Base64.decode(fotos, Base64.DEFAULT);
                    Bitmap decodedImage = BitmapFactory.decodeByteArray(imageBytes, 0, imageBytes.length);
                    profileImageView.setImageBitmap(decodedImage);

                    // Establecer los valores en los campos de la interfaz de usuario
                    usernameEditText.setText(username);
                    nameEditText.setText(name);
                    telefonoEditText.setText(telefono);
                    emailEditText.setText(email);

                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
