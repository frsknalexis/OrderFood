package com.saitama.orderfood.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.saitama.orderfood.R;
import com.saitama.orderfood.api.RestaurantAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.NetworkUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

public class ActivityWelcome extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        new Handler().postDelayed(() -> {
            if (NetworkUtil.isInternetAvailable()) {
                String tokenLogin = SharedPrefsUtil.getInstance().get(ContainsUtil.TOKEN_LOGIN, String.class);
                if (tokenLogin.equals("")) {
                    startActivity(new Intent(ActivityWelcome.this, ActivityLogin.class));
                } else {
                    RestaurantModel restaurantClient = SharedPrefsUtil.getInstance().get(ContainsUtil.RESTAURANT_CLIENT, RestaurantModel.class);
                    if (restaurantClient == null) {
                        JsonResult<RestaurantModel> exists = RestaurantAPI.exists();
                        if (exists.getCode() == 0) {
                            SharedPrefsUtil.getInstance().put(ContainsUtil.RESTAURANT_CLIENT, exists.getData());
                            startActivity(new Intent(ActivityWelcome.this, ActivityMain.class));
                        } else
                            startActivity(new Intent(ActivityWelcome.this, ActivityRegRestaurant.class));
                    } else
                        startActivity(new Intent(ActivityWelcome.this, ActivityMain.class));
                }
            } else {
                Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            }
            finish();
        }, 2000);
    }
}