package com.example.myapplication;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import android.os.Bundle;
import android.util.Base64;
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
    private String UPLOAD_URL = "http://192.168.0.10/phpconex/Actualizar.php";

    private static final String KEY_IMAGE = "foto";
    private static final String KEY_NOMBRE_FOTO = "nombrefoto";
    private static final String KEY_NOMBRE = "nombre";
    private static final String KEY_USUARIO = "usuario";
    private static final String KEY_TELEFONO = "telefono";
    private static final String KEY_CORREO = "correo";

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
                String imagen = getStringImagen(bitmap);
                String nombreFoto = etNombrefoto.getText().toString().trim();
                String nombre = etNombre.getText().toString().trim();
                String usuario = etUsuario.getText().toString().trim();
                String telefono = etTelefono.getText().toString().trim();
                String correo = etCorreo.getText().toString().trim();

                Map<String, String> params = new HashMap<>();
                params.put(KEY_IMAGE, imagen);
                params.put(KEY_NOMBRE_FOTO, nombreFoto);
                params.put(KEY_NOMBRE, nombre);
                params.put(KEY_USUARIO, usuario);
                params.put(KEY_TELEFONO, telefono);
                params.put(KEY_CORREO, correo);

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
        String consulta_URL = "http://192.168.0.10/phpconex/Extraer.php?id=" + userId;
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
                                String nombreFoto = jsonObject.optString(KEY_NOMBRE_FOTO);
                                String nombre = jsonObject.optString(KEY_NOMBRE);
                                String usuario = jsonObject.optString(KEY_USUARIO);
                                String telefono = jsonObject.optString(KEY_TELEFONO);
                                String correo = jsonObject.optString(KEY_CORREO);

                                etNombrefoto.setText(nombreFoto);
                                etNombre.setText(nombre);
                                etUsuario.setText(usuario);
                                etTelefono.setText(telefono);
                                etCorreo.setText(correo);
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
