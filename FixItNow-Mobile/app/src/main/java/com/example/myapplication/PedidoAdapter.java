package com.example.myapplication;

import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import java.io.IOException;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class PedidoAdapter extends RecyclerView.Adapter<PedidoAdapter.PedidoViewHolder> {

    private List<Pedido> pedidos = new ArrayList<>();
    private Context context;
    private OnDeleteClickListener onDeleteClickListener;

    public PedidoAdapter(Context context) {
        this.context = context;
    }

    public interface OnDeleteClickListener {
        void onDeleteClick(int pedidoId);
        void onItemDeleted();
    }

    @NonNull
    @Override
    public PedidoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_pedido, parent, false);
        return new PedidoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull PedidoViewHolder holder, int position) {
        Pedido currentPedido = pedidos.get(position);

        try {
            int pedidoId = Integer.parseInt(currentPedido.getId());

            String combinedInfo = pedidoId + " - " +
                    currentPedido.getNombreEmpresa() + " - " +
                    currentPedido.getTelefono() + " - " +
                    currentPedido.getCosto();

            holder.textView.setText(combinedInfo);

            // Set the click listener for the delete button
            holder.btnDelete.setOnClickListener(v -> {
                showDeleteConfirmationDialog(pedidoId);
            });
        } catch (NumberFormatException e) {
            // Handle the case where the String cannot be converted to an integer
            e.printStackTrace();
        }
    }

    @Override
    public int getItemCount() {
        return pedidos.size();
    }

    public void setPedidos(List<Pedido> pedidos) {
        this.pedidos = pedidos;
        notifyDataSetChanged();
    }

    public void setOnDeleteClickListener(OnDeleteClickListener listener) {
        this.onDeleteClickListener = listener;
    }

    private void sendDeleteRequest(int pedidoId) {
        String deleteUrl = "http://10.0.11.118/phpconex/Eliminarpedido.php";

        new AsyncTask<Void, Void, Boolean>() {
            @Override
            protected Boolean doInBackground(Void... voids) {
                try {
                    URL url = new URL(deleteUrl);
                    HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                    connection.setRequestMethod("POST");
                    connection.setDoOutput(true);

                    // Write the pedidoId to the request body
                    OutputStream os = connection.getOutputStream();
                    os.write(("id=" + pedidoId).getBytes());
                    os.flush();
                    os.close();

                    int responseCode = connection.getResponseCode();
                    return responseCode == HttpURLConnection.HTTP_OK;
                } catch (IOException e) {
                    e.printStackTrace();
                }
                return false;
            }

            @Override
            protected void onPostExecute(Boolean success) {
                if (success) {
                    Toast.makeText(context, "Pedido eliminado con éxito", Toast.LENGTH_SHORT).show();
                    if (onDeleteClickListener != null) {
                        onDeleteClickListener.onItemDeleted();
                    }
                } else {
                    Toast.makeText(context, "Error al eliminar el pedido", Toast.LENGTH_SHORT).show();
                }
            }
        }.execute();
    }

    private void showDeleteConfirmationDialog(int pedidoId) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("Confirmar Eliminación");
        builder.setMessage("¿Estás seguro de que deseas eliminar este pedido?");
        builder.setPositiveButton("Sí", (dialog, which) -> {
            sendDeleteRequest(pedidoId);
        });
        builder.setNegativeButton("No", (dialog, which) -> {
            // No hacer nada, simplemente cerrar el cuadro de diálogo
        });
        AlertDialog dialog = builder.create();
        dialog.show();
    }

    static class PedidoViewHolder extends RecyclerView.ViewHolder {
        private TextView textView;
        private Button btnDelete;

        public PedidoViewHolder(@NonNull View itemView) {
            super(itemView);
            textView = itemView.findViewById(R.id.textView);
            btnDelete = itemView.findViewById(R.id.btnDelete);
        }
    }
}
