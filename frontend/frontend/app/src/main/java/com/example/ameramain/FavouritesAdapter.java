package com.example.ameramain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;  // Для загрузки изображений
import com.example.ameramain.models.Favourites;

import java.util.List;

public class FavouritesAdapter extends RecyclerView.Adapter<FavouritesAdapter.FavViewHolder> {

    private List<Favourites> itemList;

    public FavouritesAdapter(List<Favourites> itemList) {
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public FavViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item, parent, false);
        return new FavViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FavViewHolder holder, int position) {
        Favourites item = itemList.get(position);
        holder.title.setText(item.getProductName());
        holder.price.setText(item.getProductPrice());

        // Используем Glide для загрузки изображения
        Glide.with(holder.itemView.getContext())
                .load(item.getImageUrl())  // URL изображения из модели
                .into(holder.image);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    static class FavViewHolder extends RecyclerView.ViewHolder {
        ImageView image, favIcon;
        TextView title, price;

        public FavViewHolder(@NonNull View itemView) {
            super(itemView);
            image = itemView.findViewById(R.id.item_image);
            favIcon = itemView.findViewById(R.id.item_fav);
            title = itemView.findViewById(R.id.item_title);
            price = itemView.findViewById(R.id.item_price);
        }
    }
}
