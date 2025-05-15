package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ameramain.models.Product;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private ProductAdapter adapter;
    private List<Product> favouriteProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        ImageView backButton = findViewById(R.id.backButton2);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        favouriteProducts = new ArrayList<>();
        Set<Long> favouriteIds = FavouriteManager.getFavourites(this); // локально сохранённые ID

        adapter = new ProductAdapter(favouriteProducts, favouriteIds, updatedSet -> {
            FavouriteManager.saveFavourites(this, updatedSet);
            // Не обновляем вручную, можно просто оставить — пока без автоматической перезагрузки
        });

        recyclerView.setAdapter(adapter);

        // Загрузка избранных товаров с сервера
        Long userId; // заменить на актуальный userId из SharedPreferences или Login
        userId = UserManager.getUserId(this);
        if (userId == null) {
            // обработка случая, если userId не сохранён (например, перекинуть на экран логина)
            Log.e("HomepageActivity", "userId is null, redirecting to login");
            startActivity(new Intent(this, SigninActivity.class)); // если у вас есть экран логина
            finish();
            return;
        }
        loadFavouriteProducts(userId);

        setupBottomNav();

        backButton.setOnClickListener(v -> finish());
    }

    private void loadFavouriteProducts(Long userId) {
        ApiService apiService = RetrofitClient.getApiService();

        // Получаем токен из SharedPreferences
        String token = getSharedPreferences("MyAppPrefs", MODE_PRIVATE)
                .getString("auth_token", null);

        // Логируем токен для проверки
        Log.d("FavouritesActivity", "Token: " + token);

        if (token == null) {
            Toast.makeText(this, "Token is missing. Please log in again.", Toast.LENGTH_SHORT).show();
            return;
        }

        apiService.getFavouriteProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    Log.d("FavouritesActivity", "Response body: " + response.body().toString()); // Добавляем лог
                    favouriteProducts.clear();
                    favouriteProducts.addAll(response.body());
                    adapter.notifyDataSetChanged();
                } else {
                    Log.d("FavouritesActivity", "Response error: " + response.code());
                    if (response.code() == 403) {
                        Toast.makeText(FavouritesActivity.this, "Access Denied. Please check your credentials.", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(FavouritesActivity.this, "Error: " + response.message(), Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
                Toast.makeText(FavouritesActivity.this, "Network error: " + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void setupBottomNav() {
        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> startActivity(new Intent(this, HomepageActivity.class)));
        favBtn.setOnClickListener(v -> {});  // Избранное — уже на этой странице
        searchBtn.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        userBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}
