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

public class SimilarFoodAdater extends RecyclerView.Adapter<SimilarFoodAdater.SimilarFoodViewHolder> {

    private final Context context;
    private final List<FoodModel> data;
    private   View view;

    public SimilarFoodAdater(Context context, List<FoodModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public SimilarFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
         view = LayoutInflater.from(context).inflate(R.layout.item_similar_food, parent, false);
        return new SimilarFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SimilarFoodViewHolder holder, int position) {
        FoodModel foodModel=data.get(position);
      RecycleViewFoodUtil.recycleFood(foodModel,  holder.tvFoodName,holder.tvFoodPrice,holder.tvRestaurantName);
        Picasso.get()
                .load(ContainsUtil.URL + data.get(position).getAvatar())
                .resize(225, 150)
                .centerCrop()
                .into(holder.imgFood);
        view.setOnClickListener(v -> {
            RecycleViewFoodUtil.dialogFood(context, foodModel);
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class SimilarFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvRestaurantName, tvFoodPrice;
        ImageView imgFood;


        public SimilarFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName =itemView.findViewById(R.id.name_food_detailRestaurant);
            tvRestaurantName =itemView.findViewById(R.id.name_detailRestaurant);
            tvFoodPrice =itemView.findViewById(R.id.price_food_detailRestaurant);
            imgFood=itemView.findViewById(R.id.img_food_detailRestaurant);
        }
    }
}
