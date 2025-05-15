package com.example.ameramain;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ameramain.models.CartProduct;
import com.example.ameramain.models.CartRequest;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class CartActivity extends AppCompatActivity {

    private static final String TAG = "CartActivity";
    private static final int SHIPPING_PRICE = 50;
    private Long currentUserId;

    private TextView subTotalPriceText, totalPriceText;
    private CartAdapter cartAdapter;
    private List<CartProduct> cartItems = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);

        subTotalPriceText = findViewById(R.id.subTotalPrice);
        totalPriceText = findViewById(R.id.totalPrice);

        ImageView backButton = findViewById(R.id.backButton);
        backButton.setOnClickListener(v -> finish());

        RecyclerView recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        currentUserId = UserManager.getUserId(this);

        cartAdapter = new CartAdapter(this, cartItems, new CartAdapter.OnCartChangeListener() {
            @Override
            public void onQuantityChanged() {
                updateTotals();
            }

            @Override
            public void onItemDeleted(CartProduct item) {
                removeFromCart((long) item.getProduct().getId());
            }
        });

        recyclerView.setAdapter(cartAdapter);

        Button checkoutButton = findViewById(R.id.checkoutbutton);
        checkoutButton.setOnClickListener(v -> startActivity(new Intent(this, CheckoutActivity.class)));

        setupBottomNavigation();
        loadCartItems();
    }

    private void loadCartItems() {
        ApiService apiService = RetrofitClient.getApiService();
        Call<List<CartProduct>> call = apiService.getCartProducts(currentUserId);
        call.enqueue(new Callback<List<CartProduct>>() {
            @Override
            public void onResponse(Call<List<CartProduct>> call, Response<List<CartProduct>> response) {
                if (response.isSuccessful() && response.body() != null) {
                    cartItems.clear();
                    cartItems.addAll(response.body());
                    cartAdapter.notifyDataSetChanged();
                    updateTotals();
                } else {
                    Log.e(TAG, "Ошибка получения корзины: пустой ответ или код " + response.code());
                }
            }

            @Override
            public void onFailure(Call<List<CartProduct>> call, Throwable t) {
                Log.e(TAG, "Ошибка запроса корзины", t);
            }
        });
    }

    private void removeFromCart(Long productId) {
        ApiService apiService = RetrofitClient.getApiService();
        CartRequest request = new CartRequest(currentUserId, productId);

        apiService.removeFromCart(request).enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if (response.isSuccessful()) {
                    loadCartItems(); // Перезагружаем корзину
                } else {
                    Log.e(TAG, "Ошибка удаления товара: " + response.code());
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                Log.e(TAG, "Ошибка удаления из корзины", t);
            }
        });
    }

    private void updateTotals() {
        int subtotal = 0;
        for (CartProduct item : cartItems) {
            subtotal += item.getProduct().getPrice() * item.getQuantity();
        }
        int total = subtotal + SHIPPING_PRICE;
        subTotalPriceText.setText(subtotal + ".00 тг");
        totalPriceText.setText(total + ".00 тг");
    }

    private void setupBottomNavigation() {
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.cartbtn);

        findViewById(R.id.homebtn).setOnClickListener(v -> startActivity(new Intent(this, HomepageActivity.class)));
        findViewById(R.id.favbtn).setOnClickListener(v -> startActivity(new Intent(this, FavouritesActivity.class)));
        findViewById(R.id.searchbtn).setOnClickListener(v -> startActivity(new Intent(this, SearchActivity.class)));
        findViewById(R.id.userbtn).setOnClickListener(v -> startActivity(new Intent(this, ProfileActivity.class)));
    }
}
