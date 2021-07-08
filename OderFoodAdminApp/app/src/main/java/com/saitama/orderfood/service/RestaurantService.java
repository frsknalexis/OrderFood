package com.saitama.orderfood.service;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface RestaurantService {

    @POST("/api/admin/restaurant/register")
    Call<JsonResult<RestaurantModel>> register(@Body RequestBody body);

    @GET("/api/admin/restaurant/exists")
    Call<JsonResult<RestaurantModel>> exists();

    @POST("/api/admin/restaurant/close")
    Call<JsonResult<RestaurantModel>> close(@Body RequestBody body);
}
