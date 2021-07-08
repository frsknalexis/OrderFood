package com.saitama.orderfood.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import com.saitama.orderfood.MainActivity;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.service.api.UserApi;
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
        this.dialog.show();
    }

    @Override
    protected JsonResult<String> doInBackground(UserModel... userModels) {

        JsonResult<String> result = UserApi.login(userModels[0]);

        if (result.getCode() == 0) {
            /* Lưu token đăng nhập */
            SharedPrefsUtil.getInstance().put(ContainsUtil.LOGIN, ContainsUtil.TOKEN_TYPE + result.getData());

            String token = SharedPrefsUtil.getInstance().get(ContainsUtil.TOKEN_FCM, String.class);
            RequestBody requestBody = new MultipartBody.Builder()
                    .setType(MultipartBody.FORM)
                    .addFormDataPart("token", token)
                    .build();
            UserApi.registerTokenFCM(requestBody);
        }
        return result;
    }


    @Override
    protected void onPostExecute(JsonResult<String> result) {
        super.onPostExecute(result);

        SystemClock.sleep(1000);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        if (result.getCode() == 0) {
            builder.setTitle(result.getMsg());
            builder.setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent(activity, MainActivity.class);
                activity.startActivity(intent);
                activity.finish();
            });
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        } else {
            builder.setTitle("Sai tài khoản hoặc mật khẩu");
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
            AlertDialog alertDialog = builder.create();
            alertDialog.show();
        }
    }
}
