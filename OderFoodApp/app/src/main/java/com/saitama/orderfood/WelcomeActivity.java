package com.saitama.orderfood;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.StrictMode;
import android.widget.Toast;

import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.NetworkUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

public class WelcomeActivity extends AppCompatActivity {

    @SuppressLint("SetTextI18n")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);

        new Handler().postDelayed(() -> {
            if (NetworkUtil.isInternetAvailable()) {
                String token = SharedPrefsUtil.getInstance().get(ContainsUtil.LOGIN, String.class);
                if (token.equals("")) {
                    startActivity(new Intent(this, LoginActivity.class));
                } else {
                    startActivity(new Intent(this, MainActivity.class));
                }
                finish();
            } else {
                Toast.makeText(this, "Không có kết nối mạng", Toast.LENGTH_SHORT).show();
            }
        }, 2000);
    }
}