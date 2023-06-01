package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class Recuperar extends AppCompatActivity {

    EditText et_correo, et_contrasena;
    Button iniciar_sesion;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recuperar2);

        TextView recContrasenaTextView = findViewById(R.id.iniciar_sesion);
        recContrasenaTextView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Aquí puedes realizar el cambio de pantalla o actividad
                Intent intent = new Intent(Recuperar.this, login.class);
                startActivity(intent);
            }
        });
    }
}
