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

        // Ajouter l'icône hamburger
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Gestion des clics sur les éléments du menu
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_profil) {
                    // Rediriger vers l'Activity Profil
                    Intent intent = new Intent(HabitatActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_mon_habitat) {
                    Intent intent = new Intent(HabitatActivity.this, MonHabitatActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_habitats) {
                    //On ferme
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

        // Initialiser la liste des habitats
        ListView listView = findViewById(R.id.habitat_list);
        List<Habitat> habitats = getHabitats();
        HabitatAdapter adapter = new HabitatAdapter(this, habitats);
        listView.setAdapter(adapter);

        // Ajouter un OnItemClickListener pour afficher un Toast
        listView.setOnItemClickListener((parent, view, position, id) -> {
            Habitat selectedHabitat = (Habitat) parent.getItemAtPosition(position);
            String userName = selectedHabitat.getUser();
            Toast.makeText(HabitatActivity.this, "Vous avez sélectionné " + userName, Toast.LENGTH_SHORT).show();
        });
    }

    private List<Habitat> getHabitats() {
        return Arrays.asList(
                new Habitat("Alice Dutronc", 2, Arrays.asList("Fer à repasser", "Machine à laver")),
                new Habitat("Marc Dupont", 5, Arrays.asList("Réfrigérateur", "Four", "Lave-vaisselle")),
                new Habitat("Sophie Martin", 3, Arrays.asList("Aspirateur", "Télévision", "Climatiseur")),
                new Habitat("Jean Lefevre", 1, Arrays.asList("Chauffage", "Robot de cuisine")),
                new Habitat("Laura Dubois", 4, Arrays.asList("Micro-ondes", "Radio", "Éclairage connecté"))
        );
    }
}
