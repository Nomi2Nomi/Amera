package com.example.ameramain;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.example.ameramain.models.FavouriteRequest;
import com.example.ameramain.models.Product;
import com.example.ameramain.network.ApiService;
import com.example.ameramain.network.RetrofitClient;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;
import java.util.Set;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {

    private final List<Product> productList;
    private final Set<Long> favouritesSet;
    private final OnFavouriteChangeListener onFavouriteChangeListener;

    public ProductAdapter(List<Product> productList, Set<Long> favouritesSet, OnFavouriteChangeListener onFavouriteChangeListener) {
        this.productList = productList;
        this.favouritesSet = favouritesSet;
        this.onFavouriteChangeListener = onFavouriteChangeListener;
    }

    public static class ProductViewHolder extends RecyclerView.ViewHolder {
        ImageView imageProduct, imageFav;
        TextView textTitle, textPrice;
        Context context;

        public ProductViewHolder(@NonNull View itemView, Context context) {
            super(itemView);
            this.context = context;
            imageProduct = itemView.findViewById(R.id.item_image);
            imageFav = itemView.findViewById(R.id.item_fav);
            textTitle = itemView.findViewById(R.id.item_title);
            textPrice = itemView.findViewById(R.id.item_price);
        }

        public void bind(Product product, Set<Long> favouritesSet, OnFavouriteChangeListener onFavouriteChangeListener) {
            textTitle.setText(product.getName());
            textPrice.setText(product.getPrice() + " ₸");

            String imageUrl = "http://88.198.10.91:8081/api/images/" + product.getImageUrl();
            Glide.with(context)
                    .load(imageUrl.isEmpty() || imageUrl.equals("null") ? R.drawable.product_1 : imageUrl)
                    .override(367, 512)
                    .into(imageProduct);

            Long productId = (long) product.getId();
            boolean isFavourite = favouritesSet.contains(productId);
            imageFav.setImageResource(isFavourite ? R.drawable.frame2 : R.drawable.heart__2_);

            itemView.setOnClickListener(v -> {
                Intent intent = new Intent(context, ProductDetailsActivity.class);
                intent.putExtra("product_id", product.getId());
                intent.putExtra("product_name", product.getName());
                intent.putExtra("product_price", product.getPrice());
                intent.putExtra("product_image", product.getImageUrl());
                context.startActivity(intent);
            });

            imageFav.setOnClickListener(v -> {
                boolean isNowFavourite;

                if (favouritesSet.contains(productId)) {
                    favouritesSet.remove(productId);
                    isNowFavourite = false;
                } else {
                    favouritesSet.add(productId);
                    isNowFavourite = true;
                }

                // Анимация
                animateHeart(imageFav, isNowFavourite);

                // Сохраняем изменения
                FavouriteManager.saveFavourites(context, favouritesSet);

                // Уведомляем слушателя
                if (onFavouriteChangeListener != null) {
                    onFavouriteChangeListener.onFavouriteChanged(favouritesSet);
                }

                // Отправляем на сервер
                sendFavouriteChange(productId, isNowFavourite);
            });
        }

        private void animateHeart(ImageView imageView, boolean isFavourite) {
            ObjectAnimator scaleX = ObjectAnimator.ofFloat(imageView, "scaleX", 1f, 1.3f, 1f);
            ObjectAnimator scaleY = ObjectAnimator.ofFloat(imageView, "scaleY", 1f, 1.3f, 1f);
            AnimatorSet animatorSet = new AnimatorSet();
            animatorSet.setDuration(300);
            animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
            animatorSet.playTogether(scaleX, scaleY);

            animatorSet.addListener(new android.animation.AnimatorListenerAdapter() {
                @Override
                public void onAnimationStart(android.animation.Animator animation) {
                    imageView.setImageResource(isFavourite ? R.drawable.frame2 : R.drawable.heart__2_);
                }
            });

            animatorSet.start();
        }

        private void sendFavouriteChange(Long productId, boolean isFavourite) {
            Log.d("FavouriteChange", "Вызван метод sendFavouriteChange()");
            Toast.makeText(context, "Запрос отправляется", Toast.LENGTH_SHORT).show();

            Long userId = UserManager.getUserId(context);
            if (userId == null) {
                Log.e("FavouriteChange", "userId is null — отмена запроса");
                return;
            }

            ApiService apiService = RetrofitClient.getApiService();
            if (apiService == null) {
                Log.e("FavouriteChange", "ApiService is null");
                return;
            }

            FavouriteRequest request = new FavouriteRequest(userId, productId);
            Call<Void> call = isFavourite
                    ? apiService.addToFavourites(request)
                    : apiService.removeFromFavourites(request);

            if (call == null) {
                Log.e("FavouriteChange", "call is null");
                return;
            }

            call.enqueue(new Callback<Void>() {
                @Override
                public void onResponse(Call<Void> call, Response<Void> response) {
                    if (response.isSuccessful()) {
                        Log.d("FavouriteChange", "Успех: " + response.code());
                    } else {
                        Log.e("FavouriteChange", "Ошибка: " + response.code());
                    }
                }

                @Override
                public void onFailure(Call<Void> call, Throwable t) {
                    Log.e("FavouriteChange", "Сбой запроса: " + t.getMessage(), t);
                }
            });
        }
    }

    @NonNull
    @Override
    public ProductViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new ProductViewHolder(view, parent.getContext());
    }

    @Override
    public void onBindViewHolder(@NonNull ProductViewHolder holder, int position) {
        holder.bind(productList.get(position), favouritesSet, onFavouriteChangeListener);
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
}
