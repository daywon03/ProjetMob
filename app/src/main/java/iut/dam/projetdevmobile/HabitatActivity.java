package iut.dam.projetdevmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import com.google.android.material.navigation.NavigationView;
import java.util.Arrays;
import java.util.List;

public class HabitatActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_habitat);

        // Initialiser le DrawerLayout et la NavigationView
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        // Initialiser la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_profil) {
                    Intent intent = new Intent(HabitatActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_mon_habitat) {
                    Intent intent = new Intent(HabitatActivity.this, MonHabitatActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_habitats) {
                }
                else if (id == R.id.nav_reservation) {
                    Intent intent = new Intent(HabitatActivity.this, ReservationActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_en_savoir_plus) {
                    Intent intent = new Intent(HabitatActivity.this, EnSavoirPlusActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });

        ListView listView = findViewById(R.id.habitat_list);
        List<Habitat> habitats = getHabitats();
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Habitat selectedHabitat = (Habitat) parent.getItemAtPosition(position);
            String userName = selectedHabitat.getUser();
            Toast.makeText(HabitatActivity.this, "Vous avez sélectionné " + userName, Toast.LENGTH_SHORT).show();
        });
    }

    private List<Habitat> getHabitats() {
        return Arrays.asList(
                new Habitat("Juliette Cournon", 2, Arrays.asList("Réfrigérateur", "Machine à laver")),
                new Habitat("Cyrille Lebeaupin", 5, Arrays.asList("Centrale vapeur", "Four", "Lave-vaisselle")),
                new Habitat("Etienne Labrot", 3, Arrays.asList("Cafetière", "Télévision", "Climatiseur")),
                new Habitat("Damon Ba", 1, Arrays.asList("Chauffage", "Robot de cuisine")),
                new Habitat("Aron Descarpentries", 4, Arrays.asList("Micro-ondes", "Radio", "Plaques de cuisson"))
        );
    }
}
