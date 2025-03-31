package iut.dam.projetdevmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;

public class ProfilActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle toggle;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profil);

        setupCountryCodeSpinner();

        // Initialisation de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Affichage de l'icône du menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        // Initialisation du DrawerLayout et du NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Ajout du bouton hamburger
        toggle = new ActionBarDrawerToggle(
                this,
                drawerLayout,
                toolbar,
                0, // Remplace par une string dans strings.xml
                0
        );
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestion des clics sur le menu navigation
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_profil) {
                    //
                }
                else if (id == R.id.nav_mon_habitat) {
                    Intent intent = new Intent(ProfilActivity.this, MonHabitatActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_habitats) {
                    Intent intent = new Intent(ProfilActivity.this, HabitatActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_reservation) {
                    Intent intent = new Intent(ProfilActivity.this, ReservationActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_en_savoir_plus) {
                    Intent intent = new Intent(ProfilActivity.this, EnSavoirPlusActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

    private void setupCountryCodeSpinner() {
        Spinner spinnerCountryCode = findViewById(R.id.spinnerCountryCode);

        if (spinnerCountryCode != null) {
            String[] countryCodes = {"+33", "+34", "+49", "+44", "+1"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryCodes) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    ((TextView) view).setTextColor(Color.WHITE); // Texte blanc pour l'élément sélectionné
                    return view;
                }

                @Override
                public View getDropDownView(int position, View convertView, ViewGroup parent) {
                    View view = super.getDropDownView(position, convertView, parent);
                    ((TextView) view).setTextColor(Color.BLACK); // Texte noir dans la liste déroulante
                    return view;
                }
            };
            spinnerCountryCode.setAdapter(adapter);
        }
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
