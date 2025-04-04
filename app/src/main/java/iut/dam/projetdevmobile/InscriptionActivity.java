package iut.dam.projetdevmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Patterns;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Pattern;

public class InscriptionActivity extends AppCompatActivity {

    private EditText editTextName, editTextSurname, editTextMail, editTextPassword;
    private Spinner spinnerCountryCode;
    private Button btnInscription;
    private ProgressDialog pDialog;
    private static final String REGISTER_URL = "http://10.0.2.2:8888/powerhome_server/register.php"; // URL de l'inscription

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inscription);

        // Liaison des vues
        editTextName = findViewById(R.id.editTextName);
        editTextSurname = findViewById(R.id.editTextSurname);
        editTextMail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        spinnerCountryCode = findViewById(R.id.spinnerCountryCode);
        btnInscription = findViewById(R.id.button3);

        // Configurer le Spinner pour le code du pays
        setupCountryCodeSpinner();

        // Validation et envoi des données lors du clic sur le bouton "S'inscrire"
        btnInscription.setOnClickListener(v -> {
            String name = editTextName.getText().toString().trim();
            String surname = editTextSurname.getText().toString().trim();
            String mail = editTextMail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            // Validation des champs
            if (validateInputs(name, surname, mail, password)) {
                registerUser(name, surname, mail, password);
            }
        });

        // Retour à l'écran principal
        Button btnRetour = findViewById(R.id.button2);
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
            startActivity(intent);
        });
    }

    // Configurer le Spinner pour les codes de pays
    private void setupCountryCodeSpinner() {
        String[] countryCodes = {"+33", "+34", "+49", "+44", "+1"};

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_dropdown_item, countryCodes);
        spinnerCountryCode.setAdapter(adapter);
    }

    // Validation des entrées
    private boolean validateInputs(String name, String surname, String email, String password) {
        String nameRegex = "^[A-Za-zÀ-ÖØ-öø-ÿ]{2,25}$"; // Vérification du nom
        String emailRegex = Patterns.EMAIL_ADDRESS.pattern(); // Vérification de l'email
        String passwordRegex = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*?&])[A-Za-z\\d@$!%*?&]{8,}$"; // Vérification du mot de passe

        if (!Pattern.matches(nameRegex, name)) {
            editTextName.setError("Le prénom doit être alphabétique (2-25 lettres)");
            return false;
        }
        if (!Pattern.matches(nameRegex, surname)) {
            editTextSurname.setError("Le nom doit être alphabétique (2-25 lettres)");
            return false;
        }
        if (!Pattern.matches(emailRegex, email)) {
            editTextMail.setError("Email invalide");
            return false;
        }
        if (!Pattern.matches(passwordRegex, password)) {
            editTextPassword.setError("Le mot de passe doit contenir au moins 8 caractères, une lettre, un chiffre et un symbole");
            return false;
        }
        return true;
    }

    // Envoi des données d'inscription à l'API
    private void registerUser(String name, String surname, String email, String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Inscription en cours...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        String phone = spinnerCountryCode.getSelectedItem().toString() + "123456789"; // Exemple de numéro de téléphone

        // Création de l'objet JSON
        JsonObject json = new JsonObject();
        json.addProperty("name", name);
        json.addProperty("surname", surname);
        json.addProperty("email", email);
        json.addProperty("password", password);
        json.addProperty("phone", phone); // Ajouter un numéro de téléphone
        json.addProperty("address", ""); // Adresse vide (à compléter si nécessaire)

        // Envoi de la requête POST avec Ion
        Ion.with(this)
                .load("POST", REGISTER_URL)
                .setJsonObjectBody(json)
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        pDialog.dismiss();
                        if (e != null) {
                            Toast.makeText(InscriptionActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONObject response = new JSONObject(result);
                            if (response.has("message")) {
                                Toast.makeText(InscriptionActivity.this, response.getString("message"), Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(InscriptionActivity.this, MainActivity.class);
                                startActivity(intent);
                            } else {
                                Toast.makeText(InscriptionActivity.this, "Erreur création : " + response.getString("error"), Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException ex) {
                            Toast.makeText(InscriptionActivity.this, "Erreur JSON", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }
}
