package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class Principal extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;

    private Fragment fragmentHome;
    private Fragment fragmentOrder;
    private Fragment fragmentProfile;

    private Fragment activeFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);

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
                return false;
            }
        });

        // Mostrar el fragmento inicial
        getSupportFragmentManager().beginTransaction().add(R.id.fragmentContainer, fragmentHome).commit();
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
