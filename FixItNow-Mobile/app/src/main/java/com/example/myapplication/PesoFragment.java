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

public class PesoFragment extends Fragment {
    private Spinner spinner1, spinner2;
    private EditText Valor1;
    private TextView resultado;
    String resCalculo;
    double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_peso, container, false);

        spinner1 = view.findViewById(R.id.sp_unidad1);
        spinner2 = view.findViewById(R.id.sp_unidad2);
        Valor1 = view.findViewById(R.id.txtvalor1);
        resultado = view.findViewById(R.id.tv_resultado);
        Button button = view.findViewById(R.id.btn_calcular);

        // Asigna el OnClickListener al botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double valor = Double.parseDouble(Valor1.getText().toString());
                int posicion = spinner1.getSelectedItemPosition();

                switch (posicion) {
                    case 0: // HECTOGRAMOS
                        int posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 100;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 100000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 3.5274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 0.220462;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 1: // DECAGRAMOS
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 10;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 10000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.01;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 0.35274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 0.0220462;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 2: // GRAMOS
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 0.035274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 0.00220462;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 3: // Decigramos
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 0.1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 100;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.0001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 0.0035274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 0.000220462;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 4: // Centigramos
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 0.01;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 10;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 1e-5;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 0.00035274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * (2.20462e-5);
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 5: // Miligramos
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 1e-6;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 3.5274e-5;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * (2.20462e-6);
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 6: // Kilogramos
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 1000000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 35.274;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 2.20462;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 7: // Onzas
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 28.3495;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 28349.5;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.0283495;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 0.0625;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;
                    case 8: // Libras
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 2:
                                calculo = valor * 453.592;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Gramos"));
                                break;
                            case 5:
                                calculo = valor * 453592;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Miligramos"));
                                break;
                            case 6:
                                calculo = valor * 0.453592;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilogramos"));
                                break;
                            case 7:
                                calculo = valor * 16;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Onzas"));
                                break;
                            case 8:
                                calculo = valor * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Libras"));
                                break;
                        }
                        break;

                }
            }
        });
        return view;
    }
}
