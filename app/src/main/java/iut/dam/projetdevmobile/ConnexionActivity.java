package iut.dam.projetdevmobile;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class ConnexionActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_connexion);

        editTextEmail = findViewById(R.id.editTextTextEmailAddress);
        editTextPassword = findViewById(R.id.editTextTextPassword);
        Button btnConnexion = findViewById(R.id.button3);
        Button btnRetour = findViewById(R.id.button2);

        btnRetour.setOnClickListener(v -> {
            Intent intent = new Intent(ConnexionActivity.this, MainActivity.class);
            startActivity(intent);
        });

        btnConnexion.setOnClickListener(v -> {
            String email = editTextEmail.getText().toString();
            String password = editTextPassword.getText().toString();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(this, "Veuillez remplir tous les champs", Toast.LENGTH_SHORT).show();
            } else {
                new UserLoginTask().execute(email, password);
            }
        });
    }

    private class UserLoginTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];

            try {
                URL url = new URL("https://bdsaemobile.alwaysdata.net/php/login.php");
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                conn.setDoOutput(true);

                String postData = "email=" + email + "&password=" + password;
                try (OutputStream os = conn.getOutputStream()) {
                    os.write(postData.getBytes());
                    os.flush();
                }

                BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    response.append(line);
                }
                reader.close();

                return response.toString();
            } catch (Exception e) {
                return "Erreur: " + e.getMessage();
            }
        }

        @Override
        protected void onPostExecute(String result) {
            try {
                JSONObject jsonResponse = new JSONObject(result);
                boolean success = jsonResponse.getBoolean("success");

                if (success) {
                    Toast.makeText(ConnexionActivity.this, "Connexion réussie !", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(ConnexionActivity.this, HabitatActivity.class);
                    startActivity(intent);
                } else {
                    Toast.makeText(ConnexionActivity.this, "Email ou mot de passe incorrect", Toast.LENGTH_SHORT).show();
                }
            } catch (JSONException e) {
                Toast.makeText(ConnexionActivity.this, "Erreur serveur", Toast.LENGTH_SHORT).show();
            }
        }
    }
}
