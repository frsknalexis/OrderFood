package com.saitama.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.R;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.RecycleViewFoodUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class PopularFoodAdapter extends RecyclerView.Adapter<PopularFoodAdapter.PopularFoodViewHolder> {

    private final Context context;
    private final List<FoodModel> popularList;
    private View view;

    public PopularFoodAdapter(Context ctx, List<FoodModel> data) {
        context = ctx;
        popularList = data;
    }

    @NonNull
    @Override
    public PopularFoodAdapter.PopularFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_popular_food, parent, false);
        return new PopularFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PopularFoodAdapter.PopularFoodViewHolder holder, int position) {
        FoodModel foodModel = popularList.get(position);
        RecycleViewFoodUtil.recycleFood(foodModel, holder.tvFoodName, holder.tvFoodPrice, holder.tvRestaurantName);
        Picasso.get()
                .load(ContainsUtil.URL + foodModel.getAvatar())
                .resize(225, 150)
                .centerCrop()
                .into(holder.imgFood);
        view.setOnClickListener(v -> {
            RecycleViewFoodUtil.dialogFood(context, foodModel);
        });
    }

    @Override
    public int getItemCount() {
        return popularList.size();
    }


    public static class PopularFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice, tvRestaurantName;
        ImageView imgFood;

        public PopularFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.food_name);
            tvFoodPrice = itemView.findViewById(R.id.food_price);
            tvRestaurantName = itemView.findViewById(R.id.tv_popular_food_restaurant_name);
            imgFood = itemView.findViewById(R.id.food_img);

        }
    }
}
