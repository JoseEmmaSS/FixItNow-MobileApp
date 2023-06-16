package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

public class LongitudFragment extends Fragment {

    private Spinner spinner1, spinner2;
    private EditText Valor1;
    private TextView resultado;
    private String resCalculo;
    private double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_longitud, container, false);

        spinner1 = view.findViewById(R.id.sp_unidadlong1);
        spinner2 = view.findViewById(R.id.sp_unidadlong2);
        Valor1 = view.findViewById(R.id.txt_valorLong);
        resultado = view.findViewById(R.id.tv_resultadolong);
        Button button = view.findViewById(R.id.btn_calcularLong);

        // Asigna el OnClickListener al botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double valor = Double.parseDouble(Valor1.getText().toString());
                int posicion = spinner1.getSelectedItemPosition();
                switch (posicion) {
                    case 0:
                        int posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 100;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 1:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 100;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 2:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 0.01;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 10;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 1e-5;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 3:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 0.001;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 0.1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 1e-6;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 4:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 10;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 10000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 0.01;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 5:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 100;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 10000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 100000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor * 0.1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                    case 6:
                        posicion2 = spinner2.getSelectedItemPosition();
                        switch (posicion2) {
                            case 0:
                                calculo = valor * 1000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Metros"));
                                break;
                            case 1:
                                calculo = valor * 100000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Centimetros"));
                                break;
                            case 2:
                                calculo = valor * 1000000;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Milimetros"));
                                break;
                            case 3:
                                calculo = valor;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText((resCalculo + " Kilómetros"));
                                break;
                        }
                        break;
                }
            }
        });

        return view;
    }


}
