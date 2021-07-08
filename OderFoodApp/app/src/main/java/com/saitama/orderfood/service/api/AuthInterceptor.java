package com.saitama.orderfood.service.api;

import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AuthInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request originalRequest = chain.request();
        String token = SharedPrefsUtil.getInstance().get(ContainsUtil.LOGIN, String.class);
        Request authRequest = originalRequest;
        if (originalRequest.url().toString().contains("/api") && token != null) {
            authRequest = originalRequest.newBuilder().header("Authorization", token).build();
        }
        return chain.proceed(authRequest);
    }
}
