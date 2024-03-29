package com.example.myapplication;
import com.squareup.picasso.Picasso;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.myapplication.MainActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class ProfileFragment extends Fragment {

    private Button btnBuscar, btnSubir;
    private ImageView iv;
    private EditText etNombrefoto, etNombre, etUsuario, etTelefono, etCorreo;

    private Bitmap bitmap;
    private int PICK_IMAGE_REQUEST = 1;
    private String UPLOAD_URL = "http://10.0.11.118/phpconex/Actualizar.php";

    private static final String KEY_IMAGE = "foto";
    private static final String KEY_NOMBRE_FOTO = "nombrefoto";
    private static final String KEY_NOMBRE = "name";
    private static final String KEY_USUARIO = "user";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_CORREO = "correo";
    private static final String KEY_id = "userId";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_profile, container, false);

        btnBuscar = view.findViewById(R.id.btnBuscar);
        btnSubir = view.findViewById(R.id.btnSubir);

        etNombrefoto = view.findViewById(R.id.Nombrefoto);
        etNombre = view.findViewById(R.id.Nombre);
        etUsuario = view.findViewById(R.id.Usuario);
        etTelefono = view.findViewById(R.id.Telefono);
        etCorreo = view.findViewById(R.id.Correo);
        iv = view.findViewById(R.id.imageView);

        // Obtén los datos del perfil del usuario
        obtenerPerfilUsuario();

        btnBuscar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showFileChooser();
            }
        });

        btnSubir.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });

        return view;
    }

    public String getStringImagen(Bitmap bmp) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] imageBytes = baos.toByteArray();
        String encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        return encodedImage;
    }

    public void uploadImage() {
        final ProgressDialog loading = ProgressDialog.show(getContext(), "Subiendo...", "Espere por favor");
        StringRequest stringRequest = new StringRequest(Request.Method.POST, UPLOAD_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        loading.dismiss();
                        Toast.makeText(getContext(), response, Toast.LENGTH_LONG).show();
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                loading.dismiss();
                Toast.makeText(getContext(), error.getMessage(), Toast.LENGTH_LONG).show();
            }
        }) {
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                String foto = getStringImagen(bitmap);
                String nombrefoto = etNombrefoto.getText().toString().trim();
                String name = etNombre.getText().toString().trim();
                String user = etUsuario.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();
                Log.d("CAMPOS", "Nombre: " + name + ", Usuario: " + user);

                // Obtén el ID de usuario guardado en las SharedPreferences
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                String userId = sharedPreferences.getString("KEY_USER_ID", "");
                if (!userId.isEmpty()) {
                    final String toastMessage = "User ID: " + userId;
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), toastMessage, Toast.LENGTH_SHORT).show();
                        }
                    });
                } else {
                    getActivity().runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "User ID not found.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
                //mandar archivos
                Map<String, String> params = new HashMap<>();
                params.put(KEY_IMAGE, foto);
                params.put(KEY_NOMBRE_FOTO, nombrefoto);
                params.put(KEY_NOMBRE, name);
                params.put(KEY_USUARIO, user);
                params.put(KEY_TELEFONO, telefono);
                params.put(KEY_CORREO, correo);
                params.put(KEY_id, userId);
                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleciona imagen"), PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE_REQUEST && resultCode == getActivity().RESULT_OK && data != null && data.getData() != null) {
            Uri filePath = data.getData();
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), filePath);
                iv.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void obtenerPerfilUsuario() {
        // Obtén el ID de usuario guardado en las SharedPreferences
        SharedPreferences sharedPreferences = getActivity().getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
        String userId = sharedPreferences.getString("KEY_USER_ID", "");

        // Construye la URL completa con el ID
        String consulta_URL = "http://10.0.11.118/phpconex/Extraer.php?id=" + userId;
        Toast.makeText(getActivity(), "URL de consulta: " + consulta_URL, Toast.LENGTH_SHORT).show();

        // Realiza la solicitud al servidor
        StringRequest stringRequest = new StringRequest(Request.Method.GET, consulta_URL,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            JSONArray jsonArray = new JSONArray(response);

                            if (jsonArray.length() > 0) {
                                JSONObject jsonObject = jsonArray.getJSONObject(0);
                                String fotoUrl = jsonObject.optString(KEY_IMAGE); // Reemplaza con la clave correcta

// Obtén una referencia al ImageView en tu diseño
                                 // Reemplaza con el ID correcto

// Carga y muestra la imagen utilizando Picasso
                                Picasso.get().load(fotoUrl).into(iv);
                                String name = jsonObject.optString(KEY_NOMBRE);
                                String usuario = jsonObject.optString(KEY_USUARIO);
                                String telefono = jsonObject.optString(KEY_TELEFONO);
                                String correo = jsonObject.optString(KEY_CORREO);


                                etNombre.setText(name);
                                etUsuario.setText(usuario);
                                etTelefono.setText(telefono);
                                etCorreo.setText(correo);
                                // Mostrar la respuesta del servidor en un Toast
                                Toast.makeText(getContext(), "Respuesta del servidor: " + response, Toast.LENGTH_SHORT).show();

                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(getContext(), "Error al procesar la respuesta del servidor", Toast.LENGTH_SHORT).show();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getContext(), "Error al obtener los datos del perfil", Toast.LENGTH_SHORT).show();
                    }
                });

        RequestQueue requestQueue = Volley.newRequestQueue(getContext());
        requestQueue.add(stringRequest);
    }

}
