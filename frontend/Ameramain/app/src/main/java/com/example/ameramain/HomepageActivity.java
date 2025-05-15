package com.example.ameramain;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
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

    private Long userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homepage);

        // ✅ ИНИЦИАЛИЗАЦИЯ RetrofitClient
        RetrofitClient.init(getApplicationContext());


        userId = UserManager.getUserId(this);
        if (userId == null) {
            // обработка случая, если userId не сохранён (например, перекинуть на экран логина)
            Log.e("HomepageActivity", "userId is null, redirecting to login");
            startActivity(new Intent(this, SigninActivity.class)); // если у вас есть экран логина
            finish();
            return;
        }
        splashScreen = findViewById(R.id.splashScreen);
        recyclerView = findViewById(R.id.recyclerProducts);
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

        loadFavouritesAndProducts(); // Вместо одного запроса вызываем метод, который сначала получит избранное

        // Навигация
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

    private void loadFavouritesAndProducts() {
        ApiService apiService = RetrofitClient.getApiService();

        // Получаем список избранных продуктов (полные объекты)
        apiService.getFavouriteProducts(userId).enqueue(new Callback<List<Product>>() {
            @Override
            public void onResponse(Call<List<Product>> call, Response<List<Product>> favResponse) {
                if (favResponse.isSuccessful()) {
                    List<Product> favouriteProducts = favResponse.body();
                    if (favouriteProducts != null && !favouriteProducts.isEmpty()) {
                        Set<Long> favouritesSet = new HashSet<>();
                        for (Product product : favouriteProducts) {
                            favouritesSet.add((long) product.getId());
                        }

                        // Загружаем все продукты
                        apiService.getAllProducts().enqueue(new Callback<List<Product>>() {
                            @Override
                            public void onResponse(Call<List<Product>> call, Response<List<Product>> response) {
                                if (response.isSuccessful()) {
                                    List<Product> allProducts = response.body();
                                    if (allProducts != null) {
                                        // Преобразуем список всех продуктов в адаптер
                                        adapter = new ProductAdapter(allProducts, favouritesSet, HomepageActivity.this);
                                        recyclerView.setAdapter(adapter);
                                    }
                                } else {
                                    // Логируем ошибку получения всех продуктов
                                }
                            }

                            @Override
                            public void onFailure(Call<List<Product>> call, Throwable t) {
                                t.printStackTrace();
                                // Логируем ошибку при загрузке всех продуктов
                            }
                        });
                    } else {
                        // Обработка случая, если нет избранных продуктов
                    }
                } else {
                    // Логируем ошибку при получении избранных продуктов
                }
            }

            @Override
            public void onFailure(Call<List<Product>> call, Throwable t) {
                t.printStackTrace();
                // Логируем ошибку при загрузке избранных продуктов
            }
        });
    }


    private void saveFavouritesIds(Set<Long> favouritesSet) {
        Log.d("FavouriteManager", "Saving favourites: " + favouritesSet);

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
