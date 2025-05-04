package com.example.ameramain;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

import java.util.Arrays;
import java.util.List;

public class ProductDetailsActivity extends AppCompatActivity {

    private ViewPager2 viewPager;
    private TabLayout tabLayout;
    private ImageView btnFavorite;
    private boolean isFavorite = false;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.productdetails);

        viewPager = findViewById(R.id.viewPager);
        tabLayout = findViewById(R.id.tabIndicator);
        btnFavorite = findViewById(R.id.btnFavorite);

        List<Integer> imageList = Arrays.asList(
                R.drawable.product_1,
                R.drawable.product_2
        );

        ImageSliderAdapter adapter = new ImageSliderAdapter(imageList);
        viewPager.setAdapter(adapter);

        new TabLayoutMediator(tabLayout, viewPager, (tab, position) -> {}).attach();

        btnFavorite.setOnClickListener(v -> {
            isFavorite = !isFavorite;
            btnFavorite.setImageResource(isFavorite ? R.drawable.frame2 : R.drawable.heart__2_);
        });

        // Назад
        findViewById(R.id.btnBack).setOnClickListener(v -> finish());

        // Add to cart
        Button btnAddToCart = findViewById(R.id.btnAddToCart);
        btnAddToCart.setOnClickListener(v -> {
            // здесь будет логика добавления в корзину
        });
    }
}