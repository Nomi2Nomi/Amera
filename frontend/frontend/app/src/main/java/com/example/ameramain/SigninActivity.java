package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ameramain.models.LoginRequest;
import com.example.ameramain.models.LoginResponse;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SigninActivity extends AppCompatActivity {

    private EditText emailField, passwordField;
    private Button signInButton;
    private TextView signUpText;
    private CheckBox rememberMeCheckBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.signin);

        emailField = findViewById(R.id.emailField);
        passwordField = findViewById(R.id.passwordField);
        signInButton = findViewById(R.id.signinbutton);
        signUpText = findViewById(R.id.signUpText);
        rememberMeCheckBox = findViewById(R.id.rememberMeCheckBox);

        signInButton.setOnClickListener(v -> loginUser());
        signUpText.setOnClickListener(v -> {
            Intent intent = new Intent(SigninActivity.this, SignupActivity.class);
            startActivity(intent);
        });
    }

    private void loginUser() {
        String email = emailField.getText().toString().trim();
        String password = passwordField.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            Toast.makeText(this, "The fields should not be empty!", Toast.LENGTH_SHORT).show();
            return;
        }

        LoginRequest loginRequest = new LoginRequest(email, password);
        ApiService apiService = RetrofitClient.getRetrofitInstance().create(ApiService.class);

        Call<LoginResponse> call = apiService.loginUser(loginRequest);
        call.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                if (response.isSuccessful() && response.body() != null && response.body().getToken() != null) {
                    String token = response.body().getToken();
                    saveToken(token);
                    Toast.makeText(SigninActivity.this, "Success!", Toast.LENGTH_SHORT).show();

                    Intent intent = new Intent(SigninActivity.this, HomepageActivity.class);
                    startActivity(intent);
                    finish();
                } else {
                    String err = "Wrong data";
                    if (response.body() != null && response.body().getError() != null) {
                        err = response.body().getError();
                    }
                    Toast.makeText(SigninActivity.this, "Error: " + err, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(SigninActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }



    private void saveToken(String token) {
        getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                .edit()
                .putString("auth_token", token)
                .apply();
    }
}
