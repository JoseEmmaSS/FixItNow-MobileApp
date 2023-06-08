package com.example.myapplication;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.core.view.GravityCompat;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.navigation.NavigationView;
import com.google.android.material.bottomnavigation.BottomNavigationView;


public class Principal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    private DrawerLayout drawerLayout;

    private Fragment fragmentHome;
    private Fragment fragmentOrder;
    private Fragment fragmentProfile;

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

        Toolbar toolbar = findViewById(R.id.toolbar); //Ignora linea de errores
        setSupportActionBar(toolbar);

        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.view);
        navigationView.setNavigationItemSelectedListener((NavigationView.OnNavigationItemSelectedListener) this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();
        if (savedInstanceState == null) {
            switchFragment(fragmentHome);
            navigationView.setCheckedItem(R.id.nav_home);
        }

        // Inicializar fragmentos
        fragmentHome = new HomeFragment();
        fragmentOrder = new OrderFragment();
        fragmentProfile = new ProfileFragment();

        // Establecer el fragmento activo inicialmente
        activeFragment = fragmentHome;

        // Configurar el BottomNavigationView
        bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {




            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.bottom_home:
                        switchFragment(fragmentHome);
                        return true;
                    case R.id.bottom_order:
                        switchFragment(fragmentOrder);
                        return true;
                    case R.id.bottom_profile:
                        switchFragment(fragmentProfile);
                        return true;
                }
                drawerLayout.closeDrawer(GravityCompat.START);
                return false;
            }
        });

        // Mostrar el fragmento inicial
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit();
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
