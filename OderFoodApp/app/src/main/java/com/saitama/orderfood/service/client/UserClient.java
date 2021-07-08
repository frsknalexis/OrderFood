package com.saitama.orderfood.service.client;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.POST;

public interface UserClient {
    @POST("/login")
    Call<JsonResult<String>> login(@Body UserModel userModel);

    @HTTP(path = "/api/register/token/fcm", method = "PUT", hasBody = true)
    Call<JsonResult<String>> registerTokenFCM(@Body RequestBody body);

    @GET("/api/user/info")
    Call<JsonResult<UserModel>> getInfo();

    @POST("/register")
    Call<JsonResult<String>> register(@Body RequestBody body);
}
