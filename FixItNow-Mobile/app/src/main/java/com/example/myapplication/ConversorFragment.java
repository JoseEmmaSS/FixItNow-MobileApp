package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

public class ConversorFragment extends Fragment {

    public ConversorFragment() {
        // Constructor público vacío requerido
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_conversor, container, false);

        // Obtén las referencias a los botones
        Button pesoButton = view.findViewById(R.id.pesoButton);
        Button longitudButton = view.findViewById(R.id.longitudButton);
        Button temperaturaButton = view.findViewById(R.id.temperaturaButton);
        Button volumenButton = view.findViewById(R.id.volumenButton);

        // Configura los listeners de los botones
        pesoButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFragment(new PesoFragment());
            }
        });

        longitudButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFragment(new LongitudFragment());
            }
        });

        temperaturaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                cambiarFragment(new TemperaturaFragment());
            }
        });

        volumenButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {cambiarFragment(new VolumenFragment());}
        });



        return view;
    }

    private void cambiarFragment(Fragment fragment) {
        if (getActivity() != null) {
            getActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.fragmentContainerview, fragment)
                    .commit();
        }
    }
}
