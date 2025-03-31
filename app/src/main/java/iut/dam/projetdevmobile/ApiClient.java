package iut.dam.projetdevmobile;

import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class ApiClient {

    // Méthode pour s'inscrire
    public static void registerUser(String name, String surname, String mail, String password, ApiCallback callback) {
        new AsyncTask<Void, Void, String>() {
            @Override
            protected String doInBackground(Void... voids) {
                try {
                    // URL de ton fichier PHP d'inscription
                    String apiUrl = "https://bdsaemobile.alwaysdata.net/php/insert_user.php";

                    // Construire les données à envoyer en POST
                    String data = "firstname=" + name +
                            "&lastname=" + surname +
                            "&email=" + mail +
                            "&password=" + password;

                    // Convertir les données en bytes
                    byte[] postData = data.getBytes(StandardCharsets.UTF_8);

                    // Créer la connexion HTTP
                    HttpURLConnection urlConnection = (HttpURLConnection) new URL(apiUrl).openConnection();
                    urlConnection.setRequestMethod("POST");
                    urlConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
                    urlConnection.setRequestProperty("Content-Length", String.valueOf(postData.length));
                    urlConnection.setDoOutput(true);
                    urlConnection.getOutputStream().write(postData);

                    // Obtenir la réponse du serveur
                    int responseCode = urlConnection.getResponseCode();
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        return "Inscription réussie";
                    } else {
                        return "Erreur lors de l'inscription: " + responseCode;
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                    return "Erreur de connexion: " + e.getMessage();
                }
            }

            @Override
            protected void onPostExecute(String result) {
                // Callback pour informer l'activité que la requête a terminé
                if (result.equals("Inscription réussie")) {
                    callback.onSuccess(result);
                } else {
                    callback.onFailure(result);
                }
            }
        }.execute();
    }

    // Interface de retour pour l'activité
    public interface ApiCallback {
        void onSuccess(String response);
        void onFailure(String error);
    }
}
