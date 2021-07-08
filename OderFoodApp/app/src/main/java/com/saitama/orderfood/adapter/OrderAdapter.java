package com.saitama.orderfood.adapter;

import android.annotation.SuppressLint;
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
import com.saitama.orderfood.model.OrderModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.RecycleViewFoodUtil;
import com.squareup.picasso.Picasso;

import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.OrderViewHolder> {
    private Context context;
    private List<OrderModel> listOrder;
    private View view;

    public OrderAdapter(Context context, List<OrderModel> listOrder) {
        this.context = context;
        this.listOrder = listOrder;

    }

    @NonNull
    @Override
    public OrderAdapter.OrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.item_order, parent, false);
        return new OrderAdapter.OrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull OrderViewHolder holder, int position) {
        OrderModel orderModel = listOrder.get(position);
        FoodModel foodModel = orderModel.getFood();
        RecycleViewFoodUtil.recycleFood(foodModel, holder.tvFoodName, holder.tvFoodPrice, holder.tvRestaurantName);
        Picasso.get()
                .load(ContainsUtil.URL + foodModel.getAvatar())
                .resize(100, 100)
                .centerCrop()
                .into(holder.imgFood);

        String status;
        if (orderModel.getStatus() == 0) status = "Chờ xác nhận";
        else if (orderModel.getStatus() == 1) status = "Đang giao";
        else if (orderModel.getStatus() == 2) status = "Đã giao";
        else status = "Đã hủy";
        holder.tvAmount.setText("x" + orderModel.getAmount());
        holder.tvAmount1.setText(orderModel.getAmount() + " phần");
        holder.tvTotalPrice.setText(ContainsUtil.formatCurrency(orderModel.getTotalPrice()));
        holder.tvStatus.setText(status);
    }

    @Override
    public int getItemCount() {
        return listOrder.size();
    }

    public static class OrderViewHolder extends RecyclerView.ViewHolder {
        private TextView tvFoodName, tvFoodPrice, tvRestaurantName, tvAmount, tvAmount1, tvTotalPrice, tvStatus;
        private ImageView imgFood;

        public OrderViewHolder(@NonNull View itemView) {
            super(itemView);
            tvFoodName = itemView.findViewById(R.id.tv_order_food_name);
            tvAmount = itemView.findViewById(R.id.tv_order_amount);
            tvAmount1 = itemView.findViewById(R.id.tv_order_amount1);
            tvRestaurantName = itemView.findViewById(R.id.tv_order_restaurant_name);
            tvTotalPrice = itemView.findViewById(R.id.tv_order_total_price);
            tvFoodPrice = itemView.findViewById(R.id.tv_order_food_price);
            tvStatus = itemView.findViewById(R.id.tv_order_startus);
            imgFood = itemView.findViewById(R.id.img_order_food);
        }
    }
}
