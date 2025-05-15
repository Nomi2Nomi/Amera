package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ameramain.models.ApiResponse;
import com.example.ameramain.models.RegisterRequest;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SignupActivity extends AppCompatActivity {

    private EditText nameField, surnameField, phoneField, emailField, passwordField;
    private CheckBox privacyCheckBox;
    private Button signUpButton;
    private TextView signInText;

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
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show();
                return;
            }

            if (!isPrivacyAccepted) {
                Toast.makeText(this, "You must accept the Privacy Policy", Toast.LENGTH_SHORT).show();
                return;
            }

            RegisterRequest registerRequest = new RegisterRequest(name, surname, phone, email, password);
            ApiService apiService = RetrofitClient.getApiService();

            signUpButton.setEnabled(false);

            apiService.registerUser(registerRequest).enqueue(new Callback<ApiResponse>() {
                @Override
                public void onResponse(Call<ApiResponse> call, Response<ApiResponse> response) {
                    signUpButton.setEnabled(true);
                    if (response.isSuccessful() && response.body() != null) {
                        ApiResponse resp = response.body();
                        if (resp.error != null) {
                            Toast.makeText(SignupActivity.this, resp.error, Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(SignupActivity.this, "Account Created!", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
                            finish();
                        }
                    } else {
                        Toast.makeText(SignupActivity.this, "Registration failed", Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<ApiResponse> call, Throwable t) {
                    signUpButton.setEnabled(true);
                    Toast.makeText(SignupActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_LONG).show();
                }
            });
        });

        signInText.setOnClickListener(v -> {
            startActivity(new Intent(SignupActivity.this, SigninActivity.class));
            finish();
        });
    }
}
