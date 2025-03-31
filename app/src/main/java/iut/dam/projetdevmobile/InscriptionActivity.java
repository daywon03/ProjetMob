package iut.dam.projetdevmobile;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class InscriptionActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        Button btnRetour = findViewById(R.id.button2);
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        setupCountryCodeSpinner(); // Configurer le Spinner pour le code du pays

        // Validation du formulaire lors du clic sur le bouton "S'inscrire"
        Button btnInscription = findViewById(R.id.button3);
        btnInscription.setOnClickListener(v -> {
            EditText editTextName = findViewById(R.id.editTextName);
            EditText editTextSurname = findViewById(R.id.editTextSurname);
            EditText editTextMail = findViewById(R.id.editTextTextEmailAddress);
            EditText editTextPassword = findViewById(R.id.editTextTextPassword);

            // Récupération des informations
            String name = editTextName.getText().toString();
            String surname = editTextSurname.getText().toString();
            String mail = editTextMail.getText().toString();
            String password = editTextPassword.getText().toString();

            // Vérification des champs
            if (name.isEmpty() || surname.isEmpty() || mail.isEmpty() || password.isEmpty()) {
                Toast.makeText(InscriptionActivity.this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                // Appel de l'API pour l'inscription
                ApiClient.registerUser(name, surname, mail, password, new ApiClient.ApiCallback() {
                    @Override
                    public void onSuccess(String response) {
                        runOnUiThread(() -> {
                            Toast.makeText(InscriptionActivity.this, response, Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                            startActivity(intent);
                        });
                    }

                    @Override
                    public void onFailure(String error) {
                        runOnUiThread(() -> Toast.makeText(InscriptionActivity.this, error, Toast.LENGTH_SHORT).show());
                    }
                });
            }
        });
    }

    // Configurer le Spinner pour les codes de pays
    private void setupCountryCodeSpinner() {
        Spinner spinnerCountryCode = findViewById(R.id.spinnerCountryCode);

        if (spinnerCountryCode != null) {
            String[] countryCodes = {"+33", "+34", "+49", "+44", "+1"};

            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryCodes) {
                @Override
                public View getView(int position, View convertView, ViewGroup parent) {
                    View view = super.getView(position, convertView, parent);
                    ((TextView) view).setTextColor(Color.WHITE); // Texte blanc
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
}
