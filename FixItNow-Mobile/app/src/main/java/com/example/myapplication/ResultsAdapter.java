package com.example.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

public class ResultsAdapter extends RecyclerView.Adapter<ResultsAdapter.ViewHolder> {

    private Context context;
    private JSONArray resultsArray;

    public ResultsAdapter(Context context, JSONArray resultsArray) {
        this.context = context;
        this.resultsArray = resultsArray;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_result, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        try {
            JSONObject result = resultsArray.getJSONObject(holder.getAdapterPosition());
            String item =
                    result.getString("id") + " : " +
                            result.getString("empresa") + " : " +
                            result.getString("tipo_servicio") + " - " +
                            result.getString("costo_servicio") + " - " +
                            result.getString("numero_telefono") + " - " +
                            result.getString("idusuario");

            holder.textView.setText(item);

            // Obtener el servicioId correspondiente a la posición actual
            String servicioItem = item;
            String[] parts = servicioItem.split(" - ");
            final String servicioId = parts[2];

// ...

            // Inside the onBindViewHolder method
            holder.btnAgendar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    try {
                        SharedPreferences sharedPreferences = context.getSharedPreferences(MainActivity.PREF_NAME, Context.MODE_PRIVATE);
                        String userId = sharedPreferences.getString("KEY_USER_ID", "");
                        JSONObject result = resultsArray.getJSONObject(holder.getAdapterPosition());
                        String empresa = result.getString("empresa");
                        String tipoServicio = result.getString("tipo_servicio");
                        String costoServicio = result.getString("costo_servicio");
                        String numeroTelefono = result.getString("numero_telefono");

                        sendAgendaData(userId, empresa, tipoServicio, costoServicio, numeroTelefono);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });


// ...


        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return resultsArray.length();
    }

    private void sendAgendaData(String userId, String empresa, String tipoServicio, String costoServicio, String numeroTelefono) {
        sendDataToServer(userId, empresa, tipoServicio, costoServicio, numeroTelefono);
    }



    private void sendDataToServer(final String userId, final String empresa, final String tipoServicio, final String costoServicio, final String numeroTelefono) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    // URL del servidor
                    URL url = new URL("http://192.168.0.9/phpconex/agendar.php");

                    // Abrir una conexión HTTP
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Escribir los datos en el flujo de salida
                    String data = URLEncoder.encode("userId", "UTF-8") + "=" + URLEncoder.encode(userId, "UTF-8") +
                            "&" + URLEncoder.encode("empresa", "UTF-8") + "=" + URLEncoder.encode(empresa, "UTF-8") +
                            "&" + URLEncoder.encode("tipoServicio", "UTF-8") + "=" + URLEncoder.encode(tipoServicio, "UTF-8") +
                            "&" + URLEncoder.encode("costoServicio", "UTF-8") + "=" + URLEncoder.encode(costoServicio, "UTF-8") +
                            "&" + URLEncoder.encode("numeroTelefono", "UTF-8") + "=" + URLEncoder.encode(numeroTelefono, "UTF-8");

                    OutputStream outputStream = connection.getOutputStream();
                    BufferedWriter writer = new BufferedWriter(new OutputStreamWriter(outputStream, "UTF-8"));
                    writer.write(data);
                    writer.flush();
                    writer.close();
                    outputStream.close();


                    int responseCode = connection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        // Leer la respuesta del servidor
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        StringBuilder response = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            response.append(line);
                        }
                        reader.close();
                        inputStream.close();

                        // Mostrar la respuesta del servidor en un Toast
                        final String serverResponse = response.toString();
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, serverResponse, Toast.LENGTH_SHORT).show();
                            }
                        });
                    } else {
                        // Mostrar un Toast en caso de error
                        ((Activity) context).runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toast.makeText(context, "Error al enviar la información", Toast.LENGTH_SHORT).show();
                            }
                        });
                    }

                    // Cerrar la conexión
                    connection.disconnect();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        public TextView textView;
        public Button btnAgendar;

        public ViewHolder(View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnAgendar = itemView.findViewById(R.id.btnAgendar);
        }
    }
}