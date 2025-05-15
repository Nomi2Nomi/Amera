package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class WishlistActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.wishlist);

        ImageView backButton = findViewById(R.id.backButton);
        ConstraintLayout myFavouriteCategory = findViewById(R.id.myFavouriteCategory);
        ConstraintLayout tShirtCategory = findViewById(R.id.tShirtCategory);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setSelectedItemId(R.id.favbtn);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        myFavouriteCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(WishlistActivity.this, FavouritesActivity.class);
                startActivity(intent);
            }
        });

        tShirtCategory.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // тут пока без перехода
            }
        });

        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(WishlistActivity.this, HomepageActivity.class));
        });

        favBtn.setOnClickListener(v -> {
            startActivity(new Intent(WishlistActivity.this, FavouritesActivity.class));
        });

        searchBtn.setOnClickListener(v -> {
            startActivity(new Intent(WishlistActivity.this, SearchActivity.class));
        });

        cartBtn.setOnClickListener(v -> {
            // Уже на этой странице, можно ничего не делать
        });

        userBtn.setOnClickListener(v -> {
            startActivity(new Intent(WishlistActivity.this, ProfileActivity.class));
        });

    }
}
