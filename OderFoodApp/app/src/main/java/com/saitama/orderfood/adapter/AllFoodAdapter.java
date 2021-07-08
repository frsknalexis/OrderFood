package com.saitama.orderfood.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.R;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.RecycleViewFoodUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class AllFoodAdapter extends RecyclerView.Adapter<AllFoodAdapter.AllFoodViewHolder> {

    private final Context context;
    private final List<FoodModel> data;
    private View view;

    public AllFoodAdapter(Context context, List<FoodModel> data) {
        this.context = context;
        this.data = data;
    }

    @NonNull
    @Override
    public AllFoodViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_food, parent, false);
        return new AllFoodViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AllFoodViewHolder holder, int position) {
        RecycleViewFoodUtil.recycleFood(data.get(position), holder.tvFoodName, holder.tvFoodPrice,holder.tvRestaurantName);
        Picasso.get()
                .load(ContainsUtil.URL + data.get(position).getAvatar())
                .resize(100, 100)
                .centerCrop()
                .into(holder.imgFood);
        view.setOnClickListener(v -> {
            RecycleViewFoodUtil.dialogFood(context, data.get(position));
        });
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public static class AllFoodViewHolder extends RecyclerView.ViewHolder {
        TextView tvFoodName, tvFoodPrice, tvRestaurantName;
        ImageView imgFood;
        ConstraintLayout layout;

        public AllFoodViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.txt_food_all_name);
            tvFoodPrice = itemView.findViewById(R.id.txt_food_all_price);
            tvRestaurantName=itemView.findViewById(R.id.tv_food_all_name_restaurant);
            imgFood = itemView.findViewById(R.id.img_food_all);
           layout=itemView.findViewById(R.id.layout_dialog_cart);
        }
    }
}
