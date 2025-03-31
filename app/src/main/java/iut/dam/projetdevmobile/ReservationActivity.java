package iut.dam.projetdevmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.RecyclerView;
import android.widget.TableLayout;
import android.widget.TableRow;
import com.google.android.material.navigation.NavigationView;
import java.util.Arrays;
import java.util.List;

public class ReservationActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;
    private ActionBarDrawerToggle toggle;
    private TableLayout tableLayout;

    private final List<String> daysList = Arrays.asList("Lun", "Mar", "Mer", "Jeu", "Ven", "Sam", "Dim");
    private final List<String> timeSlots = Arrays.asList("8h", "9h", "10h", "11h", "12h", "13h", "14h", "15h", "16h", "17h", "18h");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reservation);

        // Initialisation du menu
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, 0, 0);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        navigationView.setNavigationItemSelectedListener(item -> {
            int id = item.getItemId();
            if (id == R.id.nav_profil) {
                startActivity(new Intent(this, ProfilActivity.class));
            } else if (id == R.id.nav_mon_habitat) {
                startActivity(new Intent(this, MonHabitatActivity.class));
            } else if (id == R.id.nav_habitats) {
                startActivity(new Intent(this, HabitatActivity.class));
            } else if (id == R.id.nav_en_savoir_plus) {
                startActivity(new Intent(this, EnSavoirPlusActivity.class));
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });

        // Initialisation du tableau de réservation
        tableLayout = findViewById(R.id.table_layout);
        setupReservationTable();
    }

    private void setupReservationTable() {
        // Ajouter la première ligne : les jours de la semaine
        TableRow headerRow = new TableRow(this);
        headerRow.setBackgroundColor(Color.BLACK);

        // Première cellule vide (coin haut gauche)
        TextView emptyCell = new TextView(this);
        emptyCell.setPadding(16, 16, 16, 16);
        headerRow.addView(emptyCell);

        // Ajout des jours
        for (String day : daysList) {
            TextView dayText = new TextView(this);
            dayText.setText(day);
            dayText.setPadding(16, 16, 16, 16);
            dayText.setTextColor(Color.WHITE);
            dayText.setBackgroundColor(Color.BLACK);
            headerRow.addView(dayText);
        }

        tableLayout.addView(headerRow);

        // Ajouter les lignes horaires et cases de réservation
        for (String heure : timeSlots) {
            TableRow row = new TableRow(this);

            // Colonne des horaires (gauche)
            TextView horaireText = new TextView(this);
            horaireText.setText(heure);
            horaireText.setPadding(16, 16, 16, 16);
            horaireText.setBackgroundColor(Color.LTGRAY);
            horaireText.setTextColor(Color.BLACK);
            row.addView(horaireText);

            // Colonnes des jours
            for (int j = 0; j < daysList.size(); j++) {
                TextView caseReservation = new TextView(this);
                caseReservation.setText(" "); // Case vide
                caseReservation.setPadding(32, 32, 32, 32);
                caseReservation.setBackgroundResource(R.drawable.border_item); // Définir un style de bordure
                caseReservation.setOnClickListener(v -> {
                    Toast.makeText(this, "Réservé à " + heure, Toast.LENGTH_SHORT).show();
                });

                row.addView(caseReservation);
            }

            tableLayout.addView(row);
        }
    }
}
