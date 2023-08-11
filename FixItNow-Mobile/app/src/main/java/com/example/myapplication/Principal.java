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
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Principal extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;

    private Fragment fragmentHome;
    private Fragment fragmentSolicitud;


    private Fragment fragmentProfile;
    private Fragment resultsFragment;

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.NavigationView);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        if (savedInstanceState == null) {
            switchFragment(fragmentHome);
        }

        // Inicializar fragmentos
        fragmentHome = new HomeFragment();
        fragmentSolicitud = new SolicitudFragment();
        fragmentProfile = new ProfileFragment();
        resultsFragment = new ResultsFragment();

        // Establecer el fragmento activo inicialmente
        activeFragment = fragmentHome;


// Configurar el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        switchFragment(fragmentHome);
                        return true;
                    case R.id.bottom_order:
                        removeFragmentFromContainer(resultsFragment); // Llama al método para remover ResultsFragment del container
                        switchFragment(fragmentSolicitud);
                        return true;
                    case R.id.bottom_profile:
                        removeFragmentFromContainer(resultsFragment); // Llama al método para remover ResultsFragment del container
                        switchFragment(fragmentProfile);
                        return true;
                }
                return false;
            }
        });

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit();
            activeFragment = fragmentHome;
        }

    } // Agrega este corchete de cierre



    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    private void switchFragment(Fragment fragment) {
        if (fragment != null && fragment != activeFragment) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();

            // Remover el fragmento activo actual
            fragmentTransaction.remove(activeFragment);

            if (!fragment.isAdded()) {
                fragmentTransaction.add(R.id.fragmentContainer, fragment);
            }

            fragmentTransaction.addToBackStack(null);  // Limpia la pila de retroceso
            fragmentTransaction.commit();
            activeFragment = fragment;
        }
    }

    private void removeFragmentFromContainer(Fragment fragment) {
        if (fragment != null) {
            FragmentManager fragmentManager = getSupportFragmentManager();
            FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            fragmentTransaction.replace(R.id.fragmentContainer, new EmptyFragment());
            fragmentTransaction.commit();
        }
    }



    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.herramientas:
                Toast.makeText(this, "Herramientas seleccionada", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(this, Herramientas.class);
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
            case R.id.nav_logout:
                // Eliminar los datos de inicio de sesión guardados en las SharedPreferences del MainActivity
                SharedPreferences sharedPreferences = getSharedPreferences(MainActivity.PREF_NAME, MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.remove(MainActivity.KEY_USERNAME);
                 editor.remove(MainActivity.KEY_PASSWORD);
                 editor.apply();

                // Regresar al MainActivity
                intent = new Intent(Principal.this, MainActivity.class);
                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP); // Borrar todas las actividades anteriores
                startActivity(intent);
                finish();
                return true;

        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }
}