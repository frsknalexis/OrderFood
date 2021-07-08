package com.saitama.orderfood.utils;

import android.annotation.SuppressLint;
import android.app.ActionBar;
import android.app.Dialog;
import android.content.Context;
import android.content.res.Resources;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.saitama.orderfood.R;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.service.api.CartApi;
import com.squareup.picasso.Picasso;

public class RecycleViewFoodUtil {
    public static void recycleFood(FoodModel foodModel, TextView tvFoodName, TextView tvFoodPrice, TextView tvRestaurant) {
        tvFoodName.setText(foodModel.getName());
        tvFoodPrice.setText(ContainsUtil.formatCurrency(foodModel.getPrice()));
        tvRestaurant.setText(foodModel.getRestaurant().getName());
    }

    @SuppressLint("SetTextI18n")
    public static void dialogFood(Context context, FoodModel foodModel) {
        View view = LayoutInflater.from(context).inflate(R.layout.dialog_food, null);
        TextView tvDialogFoodName = view.findViewById(R.id.tv_dialog_food_name);
        TextView tvDialogRestaurantName = view.findViewById(R.id.tv_dialog_restaurant_name);
        TextView tvDialogPrice = view.findViewById(R.id.tv_dialog_price);
        TextView tvAmount = view.findViewById(R.id.tv_dialog_amuont);
        Button btAdd = view.findViewById(R.id.bt_dialog_add);
        ImageView icClose = view.findViewById(R.id.ic_dialog_close);
        ImageView icReduction = view.findViewById(R.id.ic_dialog_reduction);
        ImageView icIncrease = view.findViewById(R.id.ic_dialog_increase);
        ImageView imgFood = view.findViewById(R.id.img_dialog_cart_food);

        Picasso.get()
                .load(ContainsUtil.URL + foodModel.getAvatar())
                .resize(Resources.getSystem().getDisplayMetrics().widthPixels, (int) ((double) Resources.getSystem().getDisplayMetrics().widthPixels / 2))
                .centerCrop()
                .into(imgFood);

        tvDialogFoodName.setText(foodModel.getName());
        tvDialogRestaurantName.setText(foodModel.getRestaurant().getName());
        tvDialogPrice.setText(ContainsUtil.formatCurrency(foodModel.getPrice()));
        Dialog builder = new Dialog(context, android.R.style.Theme_Light_NoTitleBar);
        builder.setContentView(view);
//        builder.setCancelable(true);
//        Dialog dialog = builder.create();
        builder.getWindow().setLayout(-1, ActionBar.LayoutParams.WRAP_CONTENT);
        builder.getWindow().setGravity(Gravity.BOTTOM);
        builder.show();

        /* button add */
        btAdd.setOnClickListener(v1 -> {
            CartApi.addCart(foodModel.getId(), tvAmount.getText().toString());
            Toast.makeText(context, "Đã thêm vào giỏ", Toast.LENGTH_SHORT).show();
            builder.cancel();
        });
        /* ic close */
        icClose.setOnClickListener(v1 -> {
            builder.cancel();
        });
        /* ic reduction*/
        icReduction.setOnClickListener(v1 -> {
            int n = Integer.parseInt((String) tvAmount.getText());
            if (n > 1)
                tvAmount.setText(n - 1 + "");
        });
        /* ic increase*/
        icIncrease.setOnClickListener(v1 -> {
            int n = Integer.parseInt((String) tvAmount.getText());
            tvAmount.setText(n + 1 + "");
        });
    }
}
