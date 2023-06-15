package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Herramientas extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;

    private Fragment fragmentCalculate;
    private Fragment fragmentConversor;
    private Fragment fragmentNotas;

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_herramientas);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.navigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            switchFragment(fragmentConversor);
        }

        // Inicializar fragmentos
        fragmentCalculate = new CalculateFragment();
        fragmentConversor = new ConversorFragment();
        fragmentNotas = new NotasFragment();

        // Establecer el fragmento activo inicialmente
        activeFragment = fragmentConversor;

        // Configurar el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_calculate:
                        switchFragment(fragmentCalculate);
                        return true;
                    case R.id.bottom_conversor:
                        switchFragment(fragmentConversor);
                        return true;
                    case R.id.bottom_notas:
                        switchFragment(fragmentNotas);
                        return true;
                }
                return false;
            }
        });

        // Mostrar el fragmento inicial
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentConversor).commit();
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            if (!fragment.isAdded()) {
                fragmentTransaction.hide(activeFragment).add(R.id.fragmentContainer, fragment).commit();
            } else {
                fragmentTransaction.hide(activeFragment).show(fragment).commit();
            }
            activeFragment = fragment;
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.home:
                Toast.makeText(this, "Herramientas seleccionada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Principal.class);
                startActivity(intent);
                return true;
            case R.id.configuracion:
                // Acciones a realizar cuando se selecciona la opción "Configuración"
                Toast.makeText(this, "Configuración seleccionada", Toast.LENGTH_SHORT).show();
                break;
            case R.id.ayuda:
                // Acciones a realizar cuando se selecciona la opción "Ayuda"
                Toast.makeText(this, "Ayuda seleccionada", Toast.LENGTH_SHORT).show();
                break;
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}
