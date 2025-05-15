package com.example.ameramain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

public class ProfileActivity extends AppCompatActivity {

    private Button logoutButton;
    private ImageView backButton, bellButton;
    private TextView helpCenterText, myDetailsText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.profile);

        logoutButton = findViewById(R.id.button);
        backButton = findViewById(R.id.backButton);
        bellButton = findViewById(R.id.bell);
        helpCenterText = findViewById(R.id.textView21);
        myDetailsText = findViewById(R.id.textView16);

        logoutButton.setOnClickListener(v -> {
            // Очистка сохранённых данных (например, токенов или ID пользователя)
            SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs", MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear(); // удаляет все сохранённые данные
            editor.apply();

            // Переход на экран входа
            Intent intent = new Intent(ProfileActivity.this, SigninActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK); // очищает стек активностей
            startActivity(intent);

            // Можно также показать сообщение
            Toast.makeText(ProfileActivity.this, "Вы вышли из аккаунта", Toast.LENGTH_SHORT).show();
        });

        backButton.setOnClickListener(v -> finish());

        bellButton.setOnClickListener(v -> {
            Toast.makeText(this, "Notifications clicked", Toast.LENGTH_SHORT).show();
        });

        helpCenterText.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, HelpCenterActivity.class);
            startActivity(intent);
        });

        myDetailsText.setOnClickListener(v -> {
            Intent intent = new Intent(ProfileActivity.this, MyDetailsActivity.class);
            startActivity(intent);
        });

        // Навигация
        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> startActivity(new Intent(this, HomepageActivity.class)));
        favBtn.setOnClickListener(v -> startActivity(new Intent(this, FavouritesActivity.class)));
        searchBtn.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        userBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}

