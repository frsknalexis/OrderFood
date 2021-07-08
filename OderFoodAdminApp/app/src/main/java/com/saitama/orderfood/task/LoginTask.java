package com.saitama.orderfood.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import com.saitama.orderfood.activity.ActivityMain;
import com.saitama.orderfood.activity.ActivityRegRestaurant;
import com.saitama.orderfood.api.RestaurantAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.api.UserAPI;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;

public class LoginTask extends AsyncTask<UserModel, Void, JsonResult<String>> {

    private final Activity activity;
    private final ProgressDialog dialog;

    public LoginTask(Activity ctx) {
        dialog = new ProgressDialog(ctx);
        activity = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Đăng nhập...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected JsonResult<String> doInBackground(UserModel... userModels) {
        JsonResult<String> result = UserAPI.login(userModels[0]);

        if (result.getCode() == 0) {
            /* Lưu token đăng nhập */
            SharedPrefsUtil.getInstance().put(ContainsUtil.TOKEN_LOGIN, ContainsUtil.TOKEN_TYPE + result.getData());
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("token", SharedPrefsUtil.getInstance().get(ContainsUtil.TOKEN_FCM, String.class))
                    .build();
            UserAPI.registerTokenFCM(requestBody);
        }
        return result;
    }

    @Override
    protected void onPostExecute(JsonResult<String> result) {
        super.onPostExecute(result);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (result.getCode() == 0) {
            builder.setTitle(result.getMsg());
            builder.setPositiveButton("OK", (dialog, which) -> {
                JsonResult<RestaurantModel> exists = RestaurantAPI.exists();
                if (exists.getCode() == 0) {
                    SharedPrefsUtil.getInstance().put(ContainsUtil.RESTAURANT_CLIENT, exists.getData());
                    Intent intent = new Intent(activity, ActivityMain.class);
                    activity.startActivity(intent);
                } else {
                    Intent intent = new Intent(activity, ActivityRegRestaurant.class);
                    activity.startActivity(intent);
                }
                activity.finish();
            });
        } else {
            builder.setTitle("Tài khoản hoặc mật khẩu không đúng");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        }
        if (dialog.isShowing()) {
            SystemClock.sleep(1000);
            dialog.dismiss();
        }
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
