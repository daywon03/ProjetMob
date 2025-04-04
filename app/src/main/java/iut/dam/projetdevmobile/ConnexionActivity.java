package iut.dam.projetdevmobile;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.gson.JsonObject;
import com.koushikdutta.async.future.FutureCallback;
import com.koushikdutta.ion.Ion;

import org.json.JSONException;
import org.json.JSONObject;

public class ConnexionActivity extends AppCompatActivity {

    private EditText editTextEmail, editTextPassword;
    private Button btnConnexion, btnRetour;
    private ProgressDialog pDialog;
    private static final String LOGIN_URL = "http://10.0.2.2:8888/powerhome_server/login.php"; // URL de la connexion

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        btnConnexion = findViewById(R.id.button3);
        btnRetour = findViewById(R.id.button2);

        // Retour à l'écran principal
        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        // Validation et envoi des informations de connexion
        btnConnexion.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString().trim();
            String password = editTextPassword.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                loginUser(email, password);
            }
        });
    }

    // Fonction pour envoyer la requête de connexion
    private void loginUser(String email, String password) {
        pDialog = new ProgressDialog(this);
        pDialog.setMessage("Connexion en cours...");
        pDialog.setIndeterminate(true);
        pDialog.setCancelable(false);
        pDialog.show();

        // Création de l'objet JSON avec les données de connexion
        JsonObject json = new JsonObject();
        json.addProperty("email", email);
        json.addProperty("password", password);

        // Envoi de la requête POST avec Ion
        Ion.with(this)
                .load("POST", LOGIN_URL)
                .setJsonObjectBody(json) // Envoi du JSON
                .asString()
                .setCallback(new FutureCallback<String>() {
                    @Override
                    public void onCompleted(Exception e, String result) {
                        pDialog.dismiss();
                        if (e != null) {
                            Toast.makeText(ConnexionActivity.this, "Erreur réseau : " + e.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }

                        try {
                            JSONObject jsonResponse = new JSONObject(result);
                            if (jsonResponse.has("token")) {
                                // Connexion réussie, obtenir le token et passer à la MainActivity
                                String token = jsonResponse.getString("token");
                                Toast.makeText(ConnexionActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                                goToHabitat(email, token);
                            } else {
                                // Email ou mot de passe incorrect
                                Toast.makeText(ConnexionActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                            }
                        } catch (JSONException jsonException) {
                            Toast.makeText(ConnexionActivity.this, "Erreur serveur", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

    // Redirection vers MainActivity après la connexion
    private void goToHabitat(String email, String token) {
        Intent intent = new Intent(this, HabitatActivity.class);
        Bundle bundle = new Bundle();
        bundle.putString("email", email);
        bundle.putString("token", token);
        intent.putExtras(bundle);
        startActivity(intent);
    }
}
