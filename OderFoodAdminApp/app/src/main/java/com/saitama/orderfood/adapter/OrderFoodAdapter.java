package com.saitama.orderfood.adapter;


import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.saitama.orderfood.R;
import com.saitama.orderfood.api.OrderAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.OrderModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.MyUtil;
import com.saitama.orderfood.utils.PicassoCircleTransformation;
import com.squareup.picasso.Picasso;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class OrderFoodAdapter extends RecyclerView.Adapter<OrderFoodAdapter.FoodOrderViewHolder> {

    private Context context;
    private List<OrderModel> list;
    private Integer idNav;

    public OrderFoodAdapter(Context ctx, List<OrderModel> list, Integer idNav) {
        this.context = ctx;
        this.list = list;
        this.idNav = idNav;
    }

    @NonNull
    @Override
    public FoodOrderViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_order_food, parent, false);
        return new FoodOrderViewHolder(view);
    }

    @SuppressLint("SetTextI18n")
    @Override
    public void onBindViewHolder(@NonNull FoodOrderViewHolder holder, int position) {
        Picasso.get().load(ContainsUtil.SERVICE_URL + list.get(position).getUser().getAvatar())
                .transform(new PicassoCircleTransformation())
                .resize(30, 30).centerCrop().into(holder.imgUser);
        Picasso.get().load(ContainsUtil.SERVICE_URL + list.get(position).getFood().getAvatar())
                .resize(70, 70).centerCrop().into(holder.imgFood);
        holder.txtPhone.setText(list.get(position).getUser().getUsername());

        String status;
        if (list.get(position).getStatus() == 0) {
            status = "Chờ xác nhận";
        } else if (list.get(position).getStatus() == 1) {
            status = "Đang giao";
        } else if (list.get(position).getStatus() == 2) {
            holder.btnOrderCancel.setVisibility(View.GONE);
            holder.btnOrderConfirm.setVisibility(View.GONE);
            status = "Đã giao";
        } else {
            holder.btnOrderCancel.setVisibility(View.GONE);
            holder.btnOrderConfirm.setVisibility(View.GONE);
            status = "Đã hủy";
        }

        holder.txtStatus.setText(status);
        holder.txtFoodName.setText(list.get(position).getFood().getName());
        holder.txtAmount.setText("x" + list.get(position).getAmount());
        holder.txtAddress.setText(list.get(position).getAddress());
        holder.txtFoodPrice.setText(MyUtil.formatCurrency(list.get(position).getPrice()) + "đ");
        holder.txtTotal.setText(MyUtil.formatCurrency(list.get(position).getTotalPrice()) + "đ");

        holder.btnOrderConfirm.setOnClickListener(v -> {
            MultipartBody.Builder body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", String.valueOf(list.get(position).getId()));

            if (idNav == 0) body.addFormDataPart("status", String.valueOf(1));
            else if (idNav == 1) body.addFormDataPart("status", String.valueOf(2));

            new ConfirmTask().execute(body.build());
            list.remove(position);
            notifyDataSetChanged();
        });

        holder.btnOrderCancel.setOnClickListener(v -> {
            MultipartBody.Builder body = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("id", String.valueOf(list.get(position).getId()))
                    .addFormDataPart("status", String.valueOf(3));
            new ConfirmTask().execute(body.build());
            list.remove(position);
            notifyDataSetChanged();
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class FoodOrderViewHolder extends RecyclerView.ViewHolder {
        TextView txtPhone, txtStatus, txtFoodName, txtFoodPrice, txtAmount, txtAddress, txtTotal;
        ImageView imgUser, imgFood;
        Button btnOrderConfirm, btnOrderCancel;

        public FoodOrderViewHolder(@NonNull View itemView) {
            super(itemView);
            txtPhone = itemView.findViewById(R.id.txt_order_phone);
            txtStatus = itemView.findViewById(R.id.txt_order_status);
            txtFoodName = itemView.findViewById(R.id.txt_order_name_food);
            txtFoodPrice = itemView.findViewById(R.id.txt_order_food_price);
            txtAmount = itemView.findViewById(R.id.txt_order_amount);
            txtAddress = itemView.findViewById(R.id.txt_order_address);
            txtTotal = itemView.findViewById(R.id.txt_order_total);
            imgUser = itemView.findViewById(R.id.img_order_user_avatar);
            imgFood = itemView.findViewById(R.id.img_order_food_avatar);
            btnOrderConfirm = itemView.findViewById(R.id.btn_order_confirm);
            btnOrderCancel = itemView.findViewById(R.id.btn_order_cancel);
        }
    }

    @SuppressLint("StaticFieldLeak")
    class ConfirmTask extends AsyncTask<RequestBody, Void, JsonResult<String>> {

        private ProgressDialog dialog;

        public ConfirmTask() {
            dialog = new ProgressDialog(context);
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog.setMessage("Loading...");
            dialog.setCancelable(false);
            dialog.show();
        }

        @Override
        protected JsonResult<String> doInBackground(RequestBody... requestBodies) {
            return OrderAPI.confirm(requestBodies[0]);
        }

        @Override
        protected void onPostExecute(JsonResult<String> stringJsonResult) {
            super.onPostExecute(stringJsonResult);
            Toast.makeText(context, stringJsonResult.getMsg(), Toast.LENGTH_SHORT).show();
            if (dialog.isShowing())
                dialog.dismiss();
        }
    }
}
