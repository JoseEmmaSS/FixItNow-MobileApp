    package com.example.myapplication;

    import android.os.Bundle;
    import android.view.LayoutInflater;
    import android.view.MenuItem;
    import android.view.View;
    import android.view.ViewGroup;
    import android.widget.SearchView;
    import android.widget.Toast;

    import androidx.annotation.NonNull;
    import androidx.annotation.Nullable;
    import androidx.fragment.app.Fragment;
    import androidx.recyclerview.widget.RecyclerView;

    import com.android.volley.Request;
    import com.android.volley.RequestQueue;
    import com.android.volley.Response;
    import com.android.volley.VolleyError;
    import com.android.volley.toolbox.JsonObjectRequest;
    import com.android.volley.toolbox.Volley;
    import com.denzcoskun.imageslider.ImageSlider;
    import com.denzcoskun.imageslider.constants.ScaleTypes;
    import com.denzcoskun.imageslider.models.SlideModel;

    import org.json.JSONArray;
    import org.json.JSONException;
    import org.json.JSONObject;

    import java.util.ArrayList;
    import java.util.List;

    public class HomeFragment extends Fragment {


        private ImageSlider imageSlider, imageSlider2;

        private static final String BASE_URL = "http://10.0.11.118/phpconex/consultahome.php?q=";

        @Nullable
        @Override
        public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_home, container, false);

            // Initialize your image sliders and other UI elements here
            ArrayList<SlideModel> slideModels = new ArrayList<>();
            slideModels.add(new SlideModel("http://10.0.11.118/phpconex/Empresa/empres1/450_1000.jpg", ScaleTypes.FIT));
            slideModels.add(new SlideModel("http://10.0.11.118/phpconex/Empresa/empres1/download.jpg", ScaleTypes.FIT));
            slideModels.add(new SlideModel("http://10.0.11.118/phpconex/Empresa/empres1/stock-photo-happy-smile-caucasian-male-mechanic-showing-thumbs-up-while-checking-car-damage-diagnostic-and-2126609678.jpg", ScaleTypes.FIT));

            ImageSlider imageSlider = view.findViewById(R.id.imageSlider); // Replace with the actual ID
            imageSlider.setImageList(slideModels, ScaleTypes.FIT);

            ArrayList<SlideModel> slideModelx = new ArrayList<>();
            slideModelx.add(new SlideModel("http://10.0.11.118/phpconex/Empresa/empresa2/download.jpg", ScaleTypes.FIT));
            slideModelx.add(new SlideModel("http://10.0.11.118/phpconex/Empresa/empresa2/placer-limpieza-k1hB--620x349@abc.jpg", ScaleTypes.FIT));

            ImageSlider imageSlider2 = view.findViewById(R.id.imageSlider2); // Replace with the actual ID
            imageSlider2.setImageList(slideModelx, ScaleTypes.FIT);

            // Return the inflated view
            return view;
        }



        @Override
        public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);

            SearchView searchView = view.findViewById(R.id.searchView);
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    performDatabaseSearch(query);
                    return true;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // Perform actions while user is typing, if needed
                    return false;
                }
            });
        }

        private void performDatabaseSearch(String query) {
            String url = BASE_URL + query;

            JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject response) {
                            try {
                                boolean success = response.getBoolean("success");

                                if (success) {
                                    try {
                                        JSONArray resultsArray = response.getJSONArray("results"); // Obtener el array de resultados

                                        // Crear un Bundle para pasar los datos al nuevo fragmento
                                        Bundle bundle = new Bundle();
                                        bundle.putString("results", resultsArray.toString());

                                        // Crear una instancia del nuevo fragmento y establecer los argumentos
                                        ResultsFragment resultsFragment = new ResultsFragment();
                                        resultsFragment.setArguments(bundle);

                                        // Realizar la transición al nuevo fragmento
                                        requireActivity().getSupportFragmentManager()
                                                .beginTransaction()
                                                .replace(R.id.fragmentContainer, resultsFragment)  // Reemplazar fragment_container con el ID de tu contenedor de fragmentos
                                                .addToBackStack(null)  // Agregar a la pila de retroceso para que se pueda volver al fragmento anterior
                                                .commit();
                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                    // Mostrar un mensaje de búsqueda exitosa
                                    Toast.makeText(requireContext(), "Búsqueda exitosa", Toast.LENGTH_SHORT).show();
                                } else {
                                    // Mostrar un mensaje de error
                                    String message = response.getString("message");
                                    Toast.makeText(requireContext(), "Error: " + message, Toast.LENGTH_SHORT).show();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                    },
                    new Response.ErrorListener() {
                        // Dentro del método onErrorResponse
                        @Override
                        public void onErrorResponse(VolleyError error) {
                            // Handle error here
                            Toast.makeText(requireContext(), "Error: " + error.getMessage(), Toast.LENGTH_SHORT).show();
                        }

                    });

            RequestQueue requestQueue = Volley.newRequestQueue(requireContext());
            requestQueue.add(jsonObjectRequest);
        }

    }
