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

public class TemperaturaFragment extends Fragment {
    private Spinner spinner1, spinner2;
    private EditText Valor1;
    private TextView resultado;
    private String resCalculo;
    private double calculo;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_temperatura, container, false);
        spinner1 = rootView.findViewById(R.id.sp_unidadTemp);
        spinner2 = rootView.findViewById(R.id.sp_unidadTemp2);
        Valor1 = rootView.findViewById(R.id.txt_valorTemp);
        resultado = rootView.findViewById(R.id.tv_resultadoTemp);
        Button button = rootView.findViewById(R.id.btn_calcularTemp);

        // Asigna el OnClickListener al botón
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                double valortemp = Float.parseFloat(Valor1.getText().toString());
                int posiciontemp = spinner1.getSelectedItemPosition();
                switch (posiciontemp) {
                    case 0:
                        int posiciontem2 = spinner2.getSelectedItemPosition();
                        switch (posiciontem2) {
                            case 0:
                                calculo = valortemp * 1;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Fahrenheit");
                                break;
                            case 1:
                                calculo = (valortemp - 32) * 5 / 9;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Celsius");
                                break;
                            case 2:
                                calculo = (valortemp - 32) * 5 / 9 + 273.15;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Kelvin");
                                break;
                        }
                        break;
                    case 1:
                        posiciontem2 = spinner2.getSelectedItemPosition();
                        switch (posiciontem2) {
                            case 0:
                                calculo = (valortemp * 9 / 5) + 32;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Fahrenheit");
                                break;
                            case 1:
                                calculo = valortemp;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Celsius");
                                break;
                            case 2:
                                calculo = valortemp + 273.15;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Kelvin");
                                break;
                        }
                        break;
                    case 2:
                        posiciontem2 = spinner2.getSelectedItemPosition();
                        switch (posiciontem2) {
                            case 0:
                                calculo = (valortemp - 273.15) * 9 / 5 + 32;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Fahrenheit");
                                break;
                            case 1:
                                calculo = valortemp - 273.15;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Celsius");
                                break;
                            case 2:
                                calculo = valortemp;
                                resCalculo = String.valueOf(calculo);
                                resultado.setText(resCalculo + " Kelvin");
                                break;
                        }
                        break;
                }
            }
        });
        return rootView;
    }
}
