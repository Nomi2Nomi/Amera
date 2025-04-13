package com.example.ameramain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class SigninActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private CheckBox rememberMeCheckBox;
    private Button signInButton;
    private TextView forgotPassword, signUpText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);
        signInButton = findViewById(R.id.signInButton);
        forgotPassword = findViewById(R.id.forgotPassword);
        signUpText = findViewById(R.id.signUpText);

        signInButton.setOnClickListener(v -> {
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();

            if (email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SigninActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else {
                // Отправляем данные на сервер
                new LoginTask().execute(email, password);
            }
        });

        forgotPassword.setOnClickListener(v ->
                Toast.makeText(SigninActivity.this, "Password recovery not implemented", Toast.LENGTH_SHORT).show()
        );

        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(intent);
            finish();
        });
    }

    // AsyncTask для выполнения асинхронного HTTP запроса
    private class LoginTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String email = params[0];
            String password = params[1];
            String result = "";

            try {
                URL url = new URL("http://88.198.10.91:8081/api/users/login"); // Укажите свой URL
                HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
                urlConnection.setRequestMethod("POST");
                urlConnection.setRequestProperty("Content-Type", "application/json");
                urlConnection.setDoOutput(true);

                // Создание JSON-объекта с данными для авторизации
                JSONObject loginData = new JSONObject();
                loginData.put("email", email);
                loginData.put("password", password);

                // Отправка данных на сервер
                urlConnection.getOutputStream().write(loginData.toString().getBytes());

                int responseCode = urlConnection.getResponseCode();
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
                    String inputLine;
                    StringBuilder response = new StringBuilder();
                    while ((inputLine = in.readLine()) != null) {
                        response.append(inputLine);
                    }
                    in.close();
                    result = response.toString();
                } else {
                    result = "Error: " + responseCode;
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
                result = "Exception: " + e.getMessage();
            }

            return result;
        }


        @Override
        protected void onPostExecute(String result) {
            super.onPostExecute(result);

            // Логирование ответа сервера
            Log.d("LoginResponse", "Server response: " + result);

            try {
                // Проверяем, является ли ответ JSON-объектом
                JSONObject jsonResponse = null;
                try {
                    jsonResponse = new JSONObject(result);
                } catch (JSONException e) {
                    // Если не JSON, просто обработаем как строку ошибки
                    Log.e("LoginResponse", "Error parsing JSON, response is: " + result);
                    Toast.makeText(SigninActivity.this, "Server error: " + result, Toast.LENGTH_SHORT).show();
                    return;
                }

                if (jsonResponse.has("token")) {
                    String token = jsonResponse.getString("token");
                    saveToken(token);  // Сохранение токена

                    // Перенаправление на главный экран
                    Intent intent = new Intent(SigninActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String error = jsonResponse.getString("error");
                    Toast.makeText(SigninActivity.this, error, Toast.LENGTH_SHORT).show();
                }

            } catch (JSONException e) {
                e.printStackTrace();
                Toast.makeText(SigninActivity.this, "Parsing error: " + e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        }


    }

    // Сохранение токена в SharedPreferences
    private void saveToken(String token) {
        getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                .edit()
                .putString("auth_token", token)
                .apply();
    }
}
