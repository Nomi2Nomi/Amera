package com.example.ameramain;

import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ameramain.models.Product;

import java.io.InputStream;
import java.net.URL;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executors;

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

            // Проверка на null или пустой URL, если URL пустой — используем статичную картинку
            String imageUrl = "http://88.198.10.91:8081/api/images/" + product.getImageUrl();
            if (imageUrl == null || imageUrl.isEmpty() || imageUrl.equals("null")) {
                // Если URL пустой или равен null, загружаем статичную картинку
                Glide.with(context)
                        .load(R.drawable.product_1)  // Замените на вашу статичную картинку
                        .into(imageProduct);
            } else {
                // Если URL присутствует, загружаем изображение с URL
                Glide.with(context)
                        .load("https://media.istockphoto.com/id/1440977634/ru/%D1%84%D0%BE%D1%82%D0%BE/%D0%B2%D0%B5%D1%80%D1%82%D0%B8%D0%BA%D0%B0%D0%BB%D1%8C%D0%BD%D1%8B%D0%B9-%D1%81%D0%BD%D0%B8%D0%BC%D0%BE%D0%BA-%D0%BA%D1%80%D0%B0%D1%81%D0%B8%D0%B2%D0%BE%D0%B3%D0%BE-%D1%80%D0%BE%D0%B7%D0%BE%D0%B2%D0%BE%D0%B3%D0%BE-%D0%BF%D0%BB%D0%B0%D1%82%D1%8C%D1%8F-%D0%B8%D0%B7%D0%BE%D0%BB%D0%B8%D1%80%D0%BE%D0%B2%D0%B0%D0%BD%D0%BD%D0%BE%D0%B3%D0%BE-%D0%BD%D0%B0-%D0%B1%D0%B5%D0%BB%D0%BE%D0%BC-%D1%84%D0%BE%D0%BD%D0%B5.jpg?s=612x612&w=0&k=20&c=efu4MrJM3i2E4xmsjTUJ2mer9qbnYPAB6MQAvRHSjRA=")
                        .into(imageProduct);
            }

            // Проверка, является ли товар в избранном
            if (favouritesSet.contains(product.getId())) {
                imageFav.setImageResource(R.drawable.frame2); // Если товар в избранном
            } else {
                imageFav.setImageResource(R.drawable.heart__2_); // Если товар не в избранном
            }

            // Обработка клика по сердечку
            imageFav.setOnClickListener(v -> {
                Long productId = (long) product.getId();
                if (favouritesSet.contains(productId)) {
                    favouritesSet.remove(productId); // Удаляем из избранного
                    imageFav.setImageResource(R.drawable.heart__2_); // Обновляем изображение
                } else {
                    favouritesSet.add(productId); // Добавляем в избранное
                    imageFav.setImageResource(R.drawable.frame2); // Обновляем изображение
                }

                // Сохраняем обновленный список избранных товаров
                FavouriteManager.saveFavourites(context, favouritesSet);

                // Уведомляем активность о изменении
                if (onFavouriteChangeListener != null) {
                    onFavouriteChangeListener.onFavouriteChanged(favouritesSet);
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
