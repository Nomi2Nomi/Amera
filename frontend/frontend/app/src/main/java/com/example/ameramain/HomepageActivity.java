package com.example.ameramain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;
import com.example.ameramain.models.Product;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomepageActivity extends AppCompatActivity implements OnFavouriteChangeListener {

    private View splashScreen;
    RecyclerView recyclerView;
    ProductAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        splashScreen = findViewById(R.id.splashScreen);
        recyclerView = findViewById(R.id.recyclerProducts); // убедись, что есть такой id
        recyclerView.setLayoutManager(new GridLayoutManager(this, 2));

        splashScreen.setOnTouchListener((v, event) -> {
            if (event.getAction() == MotionEvent.ACTION_DOWN) {
                float touchY = event.getY();
                int screenHeight = splashScreen.getHeight();
                if (touchY > screenHeight * 0.7) {
                    splashScreen.setVisibility(View.GONE);
                }
            }
            return true;
        });

        loadProductsFromApi();

        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> {});
        favBtn.setOnClickListener(v -> startActivity(new Intent(this, FavouritesActivity.class)));
        searchBtn.setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        cartBtn.setOnClickListener(v -> startActivity(new Intent(this, CartActivity.class)));
        userBtn.setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }

    private void loadProductsFromApi() {
        ApiService api = RetrofitClient.getRetrofitInstance().create(ApiService.class);
        api.getAllProducts().enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                if (response.isSuccessful()) {
                    Set<Long> favouritesSet = getFavouritesIds(); // Получаем избранные товары
                    adapter = new ProductAdapter(response.body(), favouritesSet, HomepageActivity.this); // Передаем слушатель
                    recyclerView.setAdapter(adapter);
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace(); // В лог
            }
        });
    }

    private Set<Long> getFavouritesIds() {
        SharedPreferences prefs = getSharedPreferences("favourites", MODE_PRIVATE);
        Set<String> favIds = prefs.getStringSet("favourites_ids", new HashSet<>());
        Set<Long> favouritesSet = new HashSet<>();
        for (String id : favIds) {
            favouritesSet.add(Long.parseLong(id));
        }
        return favouritesSet;
    }

    private void saveFavouritesIds(Set<Long> favouritesSet) {
        SharedPreferences prefs = getSharedPreferences("favourites", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        Set<String> favIds = new HashSet<>();
        for (Long id : favouritesSet) {
            favIds.add(id.toString());
        }
        editor.putStringSet("favourites_ids", favIds);
        editor.apply();
    }

    @Override
    public void onFavouriteChanged(Set<Long> updatedFavouritesSet) {
        // Обновляем избранное и перерисовываем RecyclerView
        saveFavouritesIds(updatedFavouritesSet);
        // Обновляем данные в адаптере, если нужно
        adapter.notifyDataSetChanged();
    }
}
