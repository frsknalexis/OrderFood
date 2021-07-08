package com.saitama.orderfood.api;

import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.service.RetrofitClientCreator;
import com.saitama.orderfood.service.UserService;

import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserAPI {
    private static final Retrofit retrofit = RetrofitClientCreator.getClient();

    /**
     * Kiểm tra đăng nhập
     *
     * @param userModel userModel
     * @return JsonResponse<String>
     */
    public static JsonResult<String> login(UserModel userModel) {
        try {
            UserService userService = retrofit.create(UserService.class);
            Call<JsonResult<String>> call = userService.login(userModel);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Đăng kí
     *
     * @param body RequestBody
     * @return JsonResponse<String>
     */
    public static JsonResult<String> register(RequestBody body) {
        try {
            UserService userService = retrofit.create(UserService.class);
            Call<JsonResult<String>> call = userService.register(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lấy thông tin tài khoản
     */
    public static JsonResult<UserModel> getInfo() {
        try {
            UserService userService = retrofit.create(UserService.class);
            Call<JsonResult<UserModel>> call = userService.getInfo();
            Response<JsonResult<UserModel>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * Lưu token firebase cloud message sau khi đăng nhập thành công
     */
    public static JsonResult<String> registerTokenFCM(RequestBody body) {
        try {
            UserService userService = retrofit.create(UserService.class);
            Call<JsonResult<String>> call = userService.registerTokenFCM(body);
            Response<JsonResult<String>> response = call.execute();
            return response.body();
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
