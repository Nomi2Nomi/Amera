package com.example.ameramain;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ameramain.models.CartProduct;
import com.example.ameramain.models.Product;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {

    public interface OnCartChangeListener {
        void onQuantityChanged();
        void onItemDeleted(CartProduct item);
    }

    private final List<CartProduct> cartItems;
    private final Context context;
    private final OnCartChangeListener listener;

    public CartAdapter(Context context, List<CartProduct> cartItems, OnCartChangeListener listener) {
        this.context = context;
        this.cartItems = cartItems;
        this.listener = listener;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_for_cart, parent, false);
        return new CartViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        CartProduct item = cartItems.get(position);
        holder.bind(item);
    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartViewHolder extends RecyclerView.ViewHolder {
        ImageView productImage, deleteIcon, plusIcon, minusIcon;
        TextView name, price, size, quantity;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            productImage = itemView.findViewById(R.id.product1Image);
            deleteIcon = itemView.findViewById(R.id.deleteProduct1);
            plusIcon = itemView.findViewById(R.id.imageView7);
            minusIcon = itemView.findViewById(R.id.imageView16);
            name = itemView.findViewById(R.id.product1Name);
            price = itemView.findViewById(R.id.product1Price);
            size = itemView.findViewById(R.id.product1Size);
            quantity = itemView.findViewById(R.id.textView9);
        }

        public void bind(CartProduct item) {
            Product product = item.getProduct();

            name.setText(product.getName());
            price.setText(String.format("%.0f тг", product.getPrice()));
            size.setText(item.getSize());
            quantity.setText(String.valueOf(item.getQuantity()));

            // Подгрузка изображения через Glide (если есть URL)
            String imageUrl = "http://88.198.10.91:8081/api/images/" + product.getImageUrl();
            Glide.with(context)
                    .load(imageUrl)
                    .placeholder(R.drawable._2520510001_e1_2) // твой placeholder
                    .into(productImage);

            plusIcon.setOnClickListener(v -> {
                item.setQuantity(item.getQuantity() + 1);
                quantity.setText(String.valueOf(item.getQuantity()));
                listener.onQuantityChanged();
            });

            minusIcon.setOnClickListener(v -> {
                if (item.getQuantity() > 1) {
                    item.setQuantity(item.getQuantity() - 1);
                    quantity.setText(String.valueOf(item.getQuantity()));
                    listener.onQuantityChanged();
                }
            });

            deleteIcon.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION) {
                    CartProduct removedItem = cartItems.remove(position);
                    notifyItemRemoved(position);
                    listener.onItemDeleted(removedItem);
                }
            });
        }
    }
}
