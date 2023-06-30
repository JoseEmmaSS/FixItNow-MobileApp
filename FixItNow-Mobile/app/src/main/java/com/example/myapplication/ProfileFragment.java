package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
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


import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ProfileFragment extends Fragment {

    private static final String PHP_URL = "http://192.168.0.10/phpconex/Extraer.php";
    private static final int REQUEST_IMAGE_CAPTURE = 1;
    private static final int REQUEST_IMAGE_PICK = 2;

    private ImageView profileImageView;
    private EditText usernameEditText;
    private EditText nameEditText;
    private EditText telefonoEditText;
    private EditText emailEditText;
    private Button changePhotoButton;

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

        // Obtén el nombre de usuario guardado en las SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        String correo = sharedPreferences.getString(MainActivity.KEY_USERNAME, "");
        Toast.makeText(getActivity(), "Correo del usuario: " + correo, Toast.LENGTH_SHORT).show();
        // Enviar la consulta al archivo PHP
        sendQueryToPHP(correo);

        // Asignar un listener al botón de cambio de foto
        changePhotoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openImagePicker();
            }
        });

        return view;
    }

    private void sendQueryToPHP(String correo) {
        String url = PHP_URL + "?correo=" + correo;

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

                // Carga la imagen en el ImageView utilizando Picasso
                Picasso.get().load(imageUri).into(profileImageView);

                // Guarda la URI de la imagen seleccionada para su posterior uso (por ejemplo, enviarla al servidor)
                selectedImageUri = imageUri;
            } else if (requestCode == REQUEST_IMAGE_CAPTURE) {
                // La foto ha sido tomada con la cámara
                // Aquí puedes obtener la imagen capturada desde el intent de la cámara y realizar las operaciones necesarias
            }
        }
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
                    String username = jsonObject.getString("user");
                    String name = jsonObject.getString("name");
                    String telefono = jsonObject.getString("telefono");
                    String email = jsonObject.getString("correo");
                    String foto = jsonObject.getString("foto");

                    // Establecer los valores en los campos de la interfaz de usuario
                    usernameEditText.setText(username);
                    nameEditText.setText(name);
                    telefonoEditText.setText(telefono);
                    emailEditText.setText(email);

                    // Carga la imagen de perfil utilizando Picasso
                    Picasso.get().load(foto).into(profileImageView);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}
