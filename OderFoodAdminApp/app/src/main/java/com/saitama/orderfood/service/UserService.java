package com.saitama.orderfood.service;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.HTTP;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.PUT;

public interface UserService {
    @POST("/login")
    Call<JsonResult<String>> login(@Body UserModel userModel);

    @POST("/register")
    Call<JsonResult<String>> register(@Body RequestBody body);

    @HTTP(path = "/api/register/token/fcm", method = "PUT", hasBody = true)
    Call<JsonResult<String>> registerTokenFCM(@Body RequestBody body);

    @GET("/api/user/info")
    Call<JsonResult<UserModel>> getInfo();
}
