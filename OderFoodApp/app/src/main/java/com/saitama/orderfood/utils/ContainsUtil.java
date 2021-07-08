package com.saitama.orderfood.utils;

import com.saitama.orderfood.service.client.RetrofitClient;

import java.text.DecimalFormat;

import retrofit2.Retrofit;

public class ContainsUtil {
    public static final String LOGIN = "login";
    public static final String TOKEN_TYPE = "Bearer ";
    public static final String TOKEN_FCM = "token-firebase-cloud-message";// Key lưu token FCM
    public static final String URL = "http://192.168.1.83:8080";
    public static final Retrofit RETROFIT = RetrofitClient.getClient();

    public static String formatCurrency(double value) {
        DecimalFormat df = new DecimalFormat("###,###,###");
        return df.format(value) + "đ";
    }
}
