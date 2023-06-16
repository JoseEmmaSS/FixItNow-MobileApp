package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

public class VolumenFragment extends Fragment {

    private Spinner spinner1_vol, spinner2_vol;
    private EditText Valor1;
    private TextView resultado;
    private String resCalculo;
    private double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_volumen, container, false);
        spinner1_vol = rootView.findViewById(R.id.spinner);
        spinner2_vol = rootView.findViewById(R.id.spinner2);
        Valor1 = rootView.findViewById(R.id.txtValorVol);
        resultado = rootView.findViewById(R.id.txtResultadoVol);
        Button button = rootView.findViewById(R.id.button);

        // Asigna el OnClickListener al botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double valor = Float.parseFloat(Valor1.getText().toString());
                int posicion = spinner1_vol.getSelectedItemPosition();
                switch (posicion) {
                    case 0:
                        int posicion2 = spinner2_vol.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                resCalculo = String.valueOf(valor);
                                resultado.setText(resCalculo + " Litros");
                                break;
                            case 1:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Mililitros");
                                break;
                        }
                        break;
                    case 1:
                        posicion2 = spinner2_vol.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Litros");
                                break;
                            case 1:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Mililitros");
                                break;
                        }
                        break;
                }
            }
        });
        return rootView;
    }
}
