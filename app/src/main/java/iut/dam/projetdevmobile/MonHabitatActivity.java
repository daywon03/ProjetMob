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

public class MonHabitatActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mon_habitat);

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
                    Intent intent = new Intent(MonHabitatActivity.this, ProfilActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_mon_habitat) {
                    //
                }
                else if (id == R.id.nav_habitats) {
                    Intent intent = new Intent(MonHabitatActivity.this, HabitatActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_reservation) {
                    Intent intent = new Intent(MonHabitatActivity.this, ReservationActivity.class);
                    startActivity(intent);
                }
                else if (id == R.id.nav_en_savoir_plus) {
                    Intent intent = new Intent(MonHabitatActivity.this, EnSavoirPlusActivity.class);
                    startActivity(intent);
                }

                drawerLayout.closeDrawer(GravityCompat.START);
                return true;
            }
        });
    }

}
