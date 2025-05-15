package com.example.ameramain;

import android.os.Bundle;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;
import com.example.ameramain.models.UserDto;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MyDetailsActivity extends AppCompatActivity {

    private EditText fullNameField, phoneField, emailField, dobField;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mydetails);

        // Найди поля по ID
        ImageView backButton = findViewById(R.id.btnBack3);
        fullNameField = findViewById(R.id.nameField20);
        phoneField = findViewById(R.id.nameField23);
        emailField = findViewById(R.id.nameField21);

        backButton.setOnClickListener(v -> finish());

        // Получи userId (например, из SharedPreferences, сейчас временно захардкодим)
        Long userId = UserManager.getUserId(this);

        // Вызови API
        ApiService apiService = RetrofitClient.getApiService();
        Call<UserDto> call = apiService.getUserById(userId);

        call.enqueue(new Callback<UserDto>() {
            @Override
            public void onResponse(Call<UserDto> call, Response<UserDto> response) {
                if (response.isSuccessful() && response.body() != null) {
                    UserDto user = response.body();

                    // Заполни поля
                    fullNameField.setText(user.getName() + " " + user.getSurname());
                    phoneField.setText(user.getPhone());
                    emailField.setText(user.getEmail());
                    // dobField.setText(user.getDob()); // если будет поле
                } else {
                    Toast.makeText(MyDetailsActivity.this, "Не удалось получить данные", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<UserDto> call, Throwable t) {
                Toast.makeText(MyDetailsActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }
}
