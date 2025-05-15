package com.example.ameramain;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ameramain.R;

import java.util.List;

public class ImageSliderAdapter extends RecyclerView.Adapter<ImageSliderAdapter.SliderViewHolder> {

    private final List<String> imageUrls;

    public ImageSliderAdapter(List<String> imageUrls) {
        this.imageUrls = imageUrls;
    }

    @NonNull
    @Override
    public SliderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_image_slider, parent, false);
        return new SliderViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SliderViewHolder holder, int position) {
        String url = imageUrls.get(position);
        Glide.with(holder.itemView.getContext())
                .load((url == null || url.isEmpty() || url.equals("null"))
                        ? R.drawable.product_1
                        : url)
                .override(407, 451)
                .into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageUrls.size();
    }

    static class SliderViewHolder extends RecyclerView.ViewHolder {
        ImageView imageView;

        SliderViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.sliderImage);
        }
    }
}
