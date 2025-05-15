package com.example.ameramain;

import android.widget.Toast;

import com.example.ameramain.models.CartRequest;
import com.example.ameramain.network.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.example.ameramain.network.RetrofitClient;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Collections;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private boolean isFavorite = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);

        // Получение данных из Intent
        long productId = getIntent().getIntExtra("product_id", -1);
        String productName = getIntent().getStringExtra("product_name");
        double productPrice = getIntent().getDoubleExtra("product_price", 0.0);
        String productImage = getIntent().getStringExtra("product_image");

        // Привязка элементов интерфейса
        TextView textTitle = findViewById(R.id.textTitle);
        TextView textPrice = findViewById(R.id.textPrice);
        ImageView btnFavorite = findViewById(R.id.btnFavorite);
        ImageView btnBack = findViewById(R.id.btnBack);
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        ViewPager2 viewPager = findViewById(R.id.viewPager);
        TabLayout tabLayout = findViewById(R.id.tabIndicator);

        // Установка текста
        textTitle.setText(productName);
        textPrice.setText(productPrice + " тг");

        // Создание URL полного пути к изображению
        String imageUrl = "http://88.198.10.91:8081/api/images/" + productImage;

        // Список изображений (пока одно изображение)
        List<String> imageList = Collections.singletonList(imageUrl);

        // Установка адаптера с Glide
        ImageSliderAdapter adapter = new ImageSliderAdapter(imageList);
        viewPager.setAdapter(adapter);
        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        // Назад
        btnBack.setOnClickListener(v -> finish());

        // Избранное
        btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            btnFavorite.setImageResource(isFavorite ? R.drawable.frame2 : R.drawable.heart__2_);
        });

        // Добавление в корзину
        btnAddToCart.setOnClickListener(v -> {
            long userId = UserManager.getUserId(this);

            if (productId == -1) {
                Toast.makeText(this, "Ошибка: продукт не найден", Toast.LENGTH_SHORT).show();
                return;
            }

            CartRequest request = new CartRequest(userId, productId);

            ApiService apiService = RetrofitClient.getApiService(); // ✅ Используем готовый клиент

            apiService.addToCart(request).enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Toast.makeText(ProductDetailsActivity.this, "Товар добавлен в корзину", Toast.LENGTH_SHORT).show();
                    } else if (response.code() == 409) {
                        Toast.makeText(ProductDetailsActivity.this, "Товар уже в корзине", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(ProductDetailsActivity.this, "Ошибка: " + response.code(), Toast.LENGTH_SHORT).show();
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Toast.makeText(ProductDetailsActivity.this, "Ошибка подключения: " + t.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
        });

    }
}
