package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ameramain.models.Favourites;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;



public class FavouritesActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private FavouritesAdapter adapter;
    private List<Favourites> itemList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.favourites);

        // Назад
        ImageView backButton = findViewById(R.id.backButton2);

        // BottomNavigationView
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.favbtn);

        // RecyclerView
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        itemList = new ArrayList<>();
        adapter = new FavouritesAdapter(itemList);
        recyclerView.setAdapter(adapter);

        // Получаем данные с сервера
        Long userId = 5L; // Получить из логина или SharedPreferences

        ApiService apiService = RetrofitClient.getApiService();
        apiService.getFavourites(userId).enqueue(new Callback<List<Favourites>>() {
            @Override
            public void onResponse(Call<List<Favourites>> call, Response<List<Favourites>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    itemList.clear();
                    itemList.addAll(response.body());
                    adapter.notifyDataSetChanged();
                }
            }

            @Override
            public void onFailure(Call<List<Favourites>> call, Throwable t) {
                t.printStackTrace();
            }
        });

        // Назад
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(FavouritesActivity.this, HomepageActivity.class));
        });

        favBtn.setOnClickListener(v -> {
            // Уже на этой странице, можно ничего не делать
        });

        searchBtn.setOnClickListener(v -> {
            startActivity(new Intent(FavouritesActivity.this, SearchActivity.class));
        });

        cartBtn.setOnClickListener(v -> {
            startActivity(new Intent(FavouritesActivity.this, CartActivity.class));
        });

        userBtn.setOnClickListener(v -> {
            startActivity(new Intent(FavouritesActivity.this, ProfileActivity.class));
        });

    }
}
