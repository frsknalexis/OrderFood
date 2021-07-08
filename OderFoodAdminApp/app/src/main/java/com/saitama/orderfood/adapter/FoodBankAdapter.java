package com.saitama.orderfood.adapter;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.activity.ActivityEditFood;
import com.saitama.orderfood.R;
import com.saitama.orderfood.model.FoodModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.MyUtil;
import com.saitama.orderfood.utils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

public class FoodBankAdapter extends RecyclerView.Adapter<FoodBankAdapter.FoodBankViewHolder> {
    protected Context context;
    protected List<FoodModel> list;

    public FoodBankAdapter(Context context, List<FoodModel> list) {
        this.context = context;
        this.list = list;
    }

    @NonNull
    @Override
    public FoodBankViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_food_bank, parent, false);
        return new FoodBankViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodBankViewHolder holder, int position) {
        Picasso
                .get()
                .load(ContainsUtil.SERVICE_URL + list.get(position).getAvatar())
                .resize(100, 100)
                .centerCrop()
                .into(holder.imgFoodAll);

        holder.txtFoodAllName.setText(list.get(position).getName());
        holder.txtFoodAllPrice.setText(MyUtil.formatCurrency(list.get(position).getPrice()) + "đ");

        holder.itemView.setOnClickListener(v -> {
            Bundle bundle = new Bundle();
            bundle.putLong("id", list.get(position).getId());
            bundle.putDouble("price", list.get(position).getPrice());
            bundle.putString("name", list.get(position).getName());
            bundle.putString("avatar", list.get(position).getAvatar());
            bundle.putString("description", list.get(position).getDescription());
            bundle.putInt("status", list.get(position).getStatus());

            Intent intent = new Intent(holder.itemView.getContext(), ActivityEditFood.class);
            intent.putExtras(bundle);
            holder.itemView.getContext().startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class FoodBankViewHolder extends RecyclerView.ViewHolder {
        ImageView imgFoodAll;
        TextView txtFoodAllName;
        TextView txtFoodAllPrice;
        View itemView;

        public FoodBankViewHolder(@NonNull View itemView) {
            super(itemView);
            this.itemView = itemView;
            this.imgFoodAll = itemView.findViewById(R.id.img_food_all);
            this.txtFoodAllName = itemView.findViewById(R.id.txt_food_all_name);
            this.txtFoodAllPrice = itemView.findViewById(R.id.txt_food_all_price);
        }
    }
}
