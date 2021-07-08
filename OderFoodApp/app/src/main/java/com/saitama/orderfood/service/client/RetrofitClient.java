package com.saitama.orderfood.service.client;

import com.saitama.orderfood.service.api.AuthInterceptor;
import com.saitama.orderfood.utils.ContainsUtil;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitClient {

    public static Retrofit getClient() {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(new AuthInterceptor())
                .addNetworkInterceptor(new AuthInterceptor())
                .build();

        return new Retrofit.Builder()
                .baseUrl(ContainsUtil.URL)
                .client(okHttpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }
}
