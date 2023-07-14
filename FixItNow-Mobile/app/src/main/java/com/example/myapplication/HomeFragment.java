package com.example.myapplication;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;

import java.util.ArrayList;
import java.util.List;

public class HomeFragment extends Fragment implements SearchView.OnQueryTextListener {
    private List<String> palabrasClave;
    private RecyclerView recyclerView;
    private SugerenciasAdapter sugerenciasAdapter;
    private SearchView searchView;
    private MenuItem searchMenuItem;
    private ImageSlider imageSlider, imageSlider2;

    private boolean isSearchOpened = false;

    @SuppressLint("MissingInflatedId")
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflar el diseño del fragmento
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = view.findViewById(R.id.recyclerViewSugerencias);

        // Configurar el RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        sugerenciasAdapter = new SugerenciasAdapter();
        sugerenciasAdapter.setOnItemClickListener(new SugerenciasAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(String sugerencia) {
                realizarBusqueda(sugerencia);
            }
        });
        recyclerView.setAdapter(sugerenciasAdapter);

        // Inicializar la lista de palabras clave
        palabrasClave = new ArrayList<>();
        palabrasClave.add("Mecanico");
        palabrasClave.add("Farmacia");
        palabrasClave.add("Comida");

        // Initialize the ImageSlider
        imageSlider = view.findViewById(R.id.imageSlider);
        imageSlider2 = view.findViewById(R.id.imageSlider2);

        // List of images
        ArrayList<SlideModel> slideModels = new ArrayList<>();
        slideModels.add(new SlideModel("http://192.168.0.10/phpconex/Empresa/empres1/450_1000.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.0.10/phpconex/Empresa/empres1/download.jpg", ScaleTypes.FIT));
        slideModels.add(new SlideModel("http://192.168.0.10/phpconex/Empresa/empres1/stock-photo-happy-smile-caucasian-male-mechanic-showing-thumbs-up-while-checking-car-damage-diagnostic-and-2126609678.jpg", ScaleTypes.FIT));

        imageSlider.setImageList(slideModels, ScaleTypes.FIT);

        ArrayList<SlideModel> slideModelx = new ArrayList<>();
        slideModelx.add(new SlideModel("http://192.168.0.10/phpconex/Empresa/empresa2/download.jpg", ScaleTypes.FIT));
        slideModelx.add(new SlideModel("http://192.168.0.10/phpconex/Empresa/empresa2/placer-limpieza-k1hB--620x349@abc.jpg", ScaleTypes.FIT));

        // Set the image list to the ImageSlider
        imageSlider2.setImageList(slideModelx, ScaleTypes.FIT);

        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, @NonNull MenuInflater inflater) {
        inflater.inflate(R.menu.menu_home, menu);

        searchMenuItem = menu.findItem(R.id.action_search);
        searchView = (SearchView) searchMenuItem.getActionView();
        searchView.setOnQueryTextListener(this);

        // Mostrar el texto de búsqueda actual en el SearchView
        searchView.setQuery(searchView.getQuery(), false);

        // Ocultar la lista de sugerencias al cargar el menú
        recyclerView.setVisibility(View.GONE);

        // Agregar un listener para el cierre del campo de búsqueda
        searchView.setOnCloseListener(new SearchView.OnCloseListener() {
            @Override
            public boolean onClose() {
                // Ocultar la lista de sugerencias al cerrar el campo de búsqueda
                recyclerView.setVisibility(View.GONE);
                isSearchOpened = false;
                return false;
            }
        });

        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == R.id.action_search) {
            // Mostrar u ocultar la lista de sugerencias al seleccionar el ícono de búsqueda
            toggleSugerencias();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public boolean onQueryTextSubmit(String query) {
        // Realizar la acción correspondiente a la consulta de búsqueda
        // Por ejemplo, mostrar los resultados de búsqueda basados en el texto ingresado
        mostrarResultadosDeBusqueda(query);
        return true;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        List<String> sugerencias = new ArrayList<>();

        // Buscar las sugerencias que coincidan con el texto ingresado
        for (String palabra : palabrasClave) {
            if (palabra.toLowerCase().startsWith(newText.toLowerCase())) {
                sugerencias.add(palabra);
            }
        }

        // Actualizar las sugerencias en el adaptador
        sugerenciasAdapter.setSugerencias(sugerencias);

        return true;
    }

    private void toggleSugerencias() {
        if (!isSearchOpened) {
            // Mostrar la lista de sugerencias al abrir el campo de búsqueda
            recyclerView.setVisibility(View.VISIBLE);
            isSearchOpened = true;
        } else {
            // Ocultar la lista de sugerencias al cerrar el campo de búsqueda
            recyclerView.setVisibility(View.GONE);
            isSearchOpened = false;
        }
    }

    private void realizarBusqueda(String sugerencia) {
        // Actualizar el texto en el SearchView con la sugerencia seleccionada
        searchView.setQuery(sugerencia, false);

        // Realizar la acción correspondiente al seleccionar la sugerencia
        // Por ejemplo, realizar una búsqueda con la sugerencia seleccionada
        Toast.makeText(requireContext(), "Búsqueda: " + sugerencia, Toast.LENGTH_SHORT).show();

        // Ocultar la lista de sugerencias al seleccionar una sugerencia
        recyclerView.setVisibility(View.GONE);
        isSearchOpened = false;
    }

    private void mostrarResultadosDeBusqueda(String query) {
        // Mostrar los resultados de búsqueda basados en el texto ingresado
        Toast.makeText(requireContext(), "Resultados de búsqueda: " + query, Toast.LENGTH_SHORT).show();
    }

    private static class SugerenciasAdapter extends RecyclerView.Adapter<SugerenciasAdapter.ViewHolder> {
        private static List<String> sugerencias;
        private OnItemClickListener onItemClickListener;

        public void setSugerencias(List<String> sugerencias) {
            this.sugerencias = sugerencias;
            notifyDataSetChanged();
        }

        public void setOnItemClickListener(OnItemClickListener listener) {
            this.onItemClickListener = listener;
        }

        public interface OnItemClickListener {
            void onItemClick(String sugerencia);
        }

        @NonNull
        @Override
        public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_sugerencia, parent, false);
            return new ViewHolder(view, onItemClickListener);
        }

        @Override
        public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
            String sugerencia = sugerencias.get(position);
            holder.bind(sugerencia);
        }

        @Override
        public int getItemCount() {
            return sugerencias != null ? sugerencias.size() : 0;
        }

        static class ViewHolder extends RecyclerView.ViewHolder {
            private TextView textViewSugerencia;

            public ViewHolder(@NonNull View itemView, final OnItemClickListener listener) {
                super(itemView);
                textViewSugerencia = itemView.findViewById(R.id.textViewSugerencia);

                itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        int position = getAdapterPosition();
                        if (position != RecyclerView.NO_POSITION && listener != null) {
                            String sugerencia = sugerencias.get(position);
                            listener.onItemClick(sugerencia);
                        }
                    }
                });
            }

            public void bind(String sugerencia) {
                textViewSugerencia.setText(sugerencia);
            }
        }
    }
}
