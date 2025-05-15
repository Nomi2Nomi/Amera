package com.example.ameramain;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

public class HelpCenterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.userhelpcenter); // Создай соответствующий XML-файл


        Button buttonWhatsapp = findViewById(R.id.buttonwhatsapp);
        Button buttonInstagram = findViewById(R.id.buttoninstagram);

        buttonWhatsapp.setOnClickListener(v -> {
            String url = "hhttps://wa.me/qr/XURPW7AIKRAEE1"; // замените на актуальный номер или ссылку
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
            startActivity(intent);
        });

        buttonInstagram.setOnClickListener(v -> {
            String url = "https://www.instagram.com/koobicorre?igsh=MXRpanpkbXc0eGI4dw=="; // замените на вашу страницу
            Intent intent = new Intent(Intent.ACTION_VIEW);
            intent.setData(Uri.parse(url));
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
