package com.saitama.orderfood.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.DetailRestaurantActivity;
import com.saitama.orderfood.R;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularRestaurantAdapter extends RecyclerView.Adapter<PopularRestaurantAdapter.PopularRestaurantViewHolder> {

    private final Context context;
    private final List<RestaurantModel> popularList;

    public PopularRestaurantAdapter(Context ctx, List<RestaurantModel> data) {
        context = ctx;
        popularList = data;
    }

    @NonNull
    @Override
    public PopularRestaurantViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_popular_restaurant, parent, false);
        return new PopularRestaurantViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularRestaurantViewHolder holder, int position) {
        holder.txt_restaurant_name.setText(popularList.get(position).getName());
        Picasso.get()
                .load(ContainsUtil.URL + popularList.get(position).getAvatar())
                .resize(175,125)
                .centerCrop()
                .into(holder.img_restaurant_popular);
        holder.layoutRestaurant.setOnClickListener(v -> {
            Intent intent = new Intent(context, DetailRestaurantActivity.class);
            Bundle bundle = new Bundle();
            bundle.putLong("id", popularList.get(position).getId());
            intent.putExtra("bundle", bundle);
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }

    public static class PopularRestaurantViewHolder extends RecyclerView.ViewHolder {
        ImageView img_restaurant_popular;
        TextView txt_restaurant_name;
        ConstraintLayout layoutRestaurant;

        public PopularRestaurantViewHolder(@NonNull View itemView) {
            super(itemView);
            img_restaurant_popular = itemView.findViewById(R.id.img_restaurant);
            txt_restaurant_name = itemView.findViewById(R.id.txt_restaurant_name);
            layoutRestaurant=itemView.findViewById(R.id.layout_restaurant);
        }
    }
}
