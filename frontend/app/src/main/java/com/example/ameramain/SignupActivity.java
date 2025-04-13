package com.example.ameramain;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class SignupActivity extends AppCompatActivity {

    private EditText nameField, surnameField, phoneField, emailField, passwordField;
    private CheckBox privacyCheckBox;
    private Button signUpButton;
    private TextView signInText;
    private static final String SERVER_URL = "http://88.198.10.91:8081/api/users/register";  // Здесь IP заменен на localhost

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signup);

        nameField = findViewById(R.id.nameField);
        surnameField = findViewById(R.id.surnameField);
        phoneField = findViewById(R.id.phoneField);
        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        privacyCheckBox = findViewById(R.id.privacyCheckBox);
        signUpButton = findViewById(R.id.signUpButton);
        signInText = findViewById(R.id.signInText);

        signUpButton.setOnClickListener(v -> {
            String name = nameField.getText().toString().trim();
            String surname = surnameField.getText().toString().trim();
            String phone = phoneField.getText().toString().trim();
            String email = emailField.getText().toString().trim();
            String password = passwordField.getText().toString().trim();
            boolean isPrivacyAccepted = privacyCheckBox.isChecked();

            if (name.isEmpty() || surname.isEmpty() || phone.isEmpty() || email.isEmpty() || password.isEmpty()) {
                Toast.makeText(SignupActivity.this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
            } else if (!isPrivacyAccepted) {
                Toast.makeText(SignupActivity.this, "You must accept the Privacy Policy", Toast.LENGTH_SHORT).show();
            } else {
                new RegisterUserTask().execute(name, surname, phone, email, password);
            }
        });

        signInText.setOnClickListener(v -> {
            Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
            startActivity(intent);
            finish();
        });
    }

    private class RegisterUserTask extends AsyncTask<String, Void, String> {
        @Override
        protected String doInBackground(String... params) {
            try {
                JSONObject jsonObject = new JSONObject();
                jsonObject.put("name", params[0]);
                jsonObject.put("surname", params[1]);
                jsonObject.put("phone", params[2]);
                jsonObject.put("email", params[3]);
                jsonObject.put("password", params[4]);

                URL url = new URL(SERVER_URL);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                conn.setRequestMethod("POST");
                conn.setRequestProperty("Content-Type", "application/json");
                conn.setDoOutput(true);

                OutputStream os = conn.getOutputStream();
                os.write(jsonObject.toString().getBytes("UTF-8"));
                os.flush();
                os.close();

                int statusCode = conn.getResponseCode();
                Log.d("SignupActivity", "Response code: " + statusCode);  // Логируем код ответа
                if (statusCode != HttpURLConnection.HTTP_OK) {
                    return "Error: " + statusCode;
                }

                BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream(), "UTF-8"));
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    response.append(line);
                }
                br.close();

                Log.d("SignupActivity", "Response: " + response.toString());  // Логируем ответ
                return response.toString();
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }

        @Override
        protected void onPostExecute(String result) {
            if (result != null) {
                Log.d("SignupActivity", "Response: " + result);
                try {
                    // Пробуем разобрать строку как JSON
                    JSONObject jsonResponse = new JSONObject(result);

                    // Проверяем наличие ошибки
                    if (jsonResponse.has("error")) {
                        Toast.makeText(SignupActivity.this, jsonResponse.getString("error"), Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(SignupActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(SignupActivity.this, SigninActivity.class);
                        startActivity(intent);
                        finish();
                    }
                } catch (JSONException e) {
                    // Если не удается разобрать как JSON, печатаем строку в лог
                    Log.e("SignupActivity", "JSON parsing error: " + result);
                    Toast.makeText(SignupActivity.this, "Registration failed: Invalid response from server", Toast.LENGTH_SHORT).show();
                }
            } else {
                Toast.makeText(SignupActivity.this, "Registration failed: No response", Toast.LENGTH_SHORT).show();
            }
        }


    }

}
