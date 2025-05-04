package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class CartActivity extends AppCompatActivity {

    private int quantityProduct1 = 1;
    private int quantityProduct2 = 1;

    private TextView product1QuantityText;
    private TextView product2QuantityText;
    private TextView subTotalPriceText;
    private TextView totalPriceText;

    private final int priceProduct1 = 3290;
    private final int priceProduct2 = 3290;
    private final int shippingPrice = 50;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        // Назад
        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        // Плюс/минус product1
        ImageView plusProduct1 = findViewById(R.id.imageView7);
        ImageView minusProduct1 = findViewById(R.id.imageView16);
        product1QuantityText = findViewById(R.id.textView9);

        plusProduct1.setOnClickListener(v -> {
            quantityProduct1++;
            product1QuantityText.setText(String.valueOf(quantityProduct1));
            updateTotals();
        });

        minusProduct1.setOnClickListener(v -> {
            if (quantityProduct1 > 1) {
                quantityProduct1--;
                product1QuantityText.setText(String.valueOf(quantityProduct1));
                updateTotals();
            }
        });

        // Плюс/минус product2
        ImageView plusProduct2 = findViewById(R.id.imageView9);
        ImageView minusProduct2 = findViewById(R.id.imageView17);
        product2QuantityText = findViewById(R.id.textView10);

        plusProduct2.setOnClickListener(v -> {
            quantityProduct2++;
            product2QuantityText.setText(String.valueOf(quantityProduct2));
            updateTotals();
        });

        minusProduct2.setOnClickListener(v -> {
            if (quantityProduct2 > 1) {
                quantityProduct2--;
                product2QuantityText.setText(String.valueOf(quantityProduct2));
                updateTotals();
            }
        });

        // Checkout
        Button checkoutButton = findViewById(R.id.checkoutbutton);
        checkoutButton.setOnClickListener(v -> {
            Intent intent = new Intent(CartActivity.this, CheckoutActivity.class);
            startActivity(intent);
        });

        // TextViews для расчётов
        subTotalPriceText = findViewById(R.id.subTotalPrice);
        totalPriceText = findViewById(R.id.totalPrice);
        updateTotals();

        // Нижняя навигация
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cartbtn);

        ImageView homeBtn = findViewById(R.id.homebtn);
        ImageView favBtn = findViewById(R.id.favbtn);
        ImageView searchBtn = findViewById(R.id.searchbtn);
        ImageView cartBtn = findViewById(R.id.cartbtn);
        ImageView userBtn = findViewById(R.id.userbtn);

        homeBtn.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, HomepageActivity.class));
        });

        favBtn.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, FavouritesActivity.class));
        });

        searchBtn.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, SearchActivity.class));
        });

        cartBtn.setOnClickListener(v -> {
            // Уже на этой странице, можно ничего не делать
        });

        userBtn.setOnClickListener(v -> {
            startActivity(new Intent(CartActivity.this, ProfileActivity.class));
        });

    }

    private void updateTotals() {
        int subtotal = (quantityProduct1 * priceProduct1) + (quantityProduct2 * priceProduct2);
        int total = subtotal + shippingPrice;

        subTotalPriceText.setText(subtotal + ".00 тг");
        totalPriceText.setText(total + ".00 тг");
    }
}
