package com.saitama.orderfood.adapter;

import android.annotation.SuppressLint;
import android.app.Activity;
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
import com.saitama.orderfood.model.CartModel;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.service.api.CartApi;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.RecycleViewFoodUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.CartViewHolder> {
    private Activity context;
    private List<CartModel> listCart;
    private View view;
    private TextView tvTotalPrice;

    public CartAdapter(Activity context, List<CartModel> popularList) {
        this.context = context;
        this.listCart = popularList;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_cart, parent, false);
        tvTotalPrice = context.findViewById(R.id.tv_cart_total_price);
        return new CartViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        FoodModel foodModel = listCart.get(position).getFood();
        RecycleViewFoodUtil.recycleFood(foodModel, holder.tvFoodName, holder.tvFoodPrice, holder.tvRestaurantName);
        Picasso.get()
                .load(ContainsUtil.URL + foodModel.getAvatar())
                .resize(100, 100)
                .centerCrop()
                .into(holder.imgFood);
        holder.tvAmount.setText(listCart.get(position).getAmount() + "");
        /*reduction food*/
        holder.icMinus.setOnClickListener(v -> {
            int n = Integer.parseInt((String) holder.tvAmount.getText());
            if (n > 1)
                holder.tvAmount.setText(n - 1 + "");
            tvTotalPrice.setText(ContainsUtil.formatCurrency(CartApi.addCart(foodModel.getId(), holder.tvAmount.getText().toString())));
        });
        /*increase food*/
        holder.icPlus.setOnClickListener(v -> {
            int n = Integer.parseInt((String) holder.tvAmount.getText());
            holder.tvAmount.setText(n + 1 + "");
            tvTotalPrice.setText(ContainsUtil.formatCurrency(CartApi.addCart(foodModel.getId(), holder.tvAmount.getText().toString())));
        });

        holder.layoutDelete.setOnClickListener(v -> {
            tvTotalPrice.setText(ContainsUtil.formatCurrency(CartApi.delCart(listCart.get(position).getId())));
            listCart.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return listCart.size();
    }

    public static class CartViewHolder extends RecyclerView.ViewHolder {
        private final TextView tvFoodName, tvFoodPrice, tvRestaurantName, tvAmount;
        private final ConstraintLayout layoutDelete;
        private final ImageView imgFood, icMinus, icPlus;

        public CartViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_cart_food_name);
            tvFoodPrice = itemView.findViewById(R.id.tv_cart_food_price);
            tvAmount = itemView.findViewById(R.id.tv_cart_amount);
            tvRestaurantName = itemView.findViewById(R.id.tv_cart_restaurant_name);
            layoutDelete = itemView.findViewById(R.id.layout_cart_delete);
            imgFood = itemView.findViewById(R.id.img_cart_food);
            icMinus = itemView.findViewById(R.id.ic_cart_minus);
            icPlus = itemView.findViewById(R.id.ic_cart_plus);
        }
    }
}
