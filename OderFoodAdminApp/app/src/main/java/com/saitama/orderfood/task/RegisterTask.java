package com.saitama.orderfood.task;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import com.saitama.orderfood.activity.ActivityLogin;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.UserModel;
import com.saitama.orderfood.api.UserAPI;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import okhttp3.RequestBody;

public class RegisterTask extends AsyncTask<RequestBody, Void, JsonResult<String>> {

    private final Activity activity;
    private final ProgressDialog dialog;

    public RegisterTask(Activity ctx) {
        dialog = new ProgressDialog(ctx);
        activity = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        super.onPreExecute();
        this.dialog.setMessage("Vui lòng đợi...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected JsonResult<String> doInBackground(RequestBody... requestBodies) {
        return UserAPI.register(requestBodies[0]);
    }

    @Override
    protected void onPostExecute(JsonResult<String> stringJsonResult) {
        super.onPostExecute(stringJsonResult);
        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(stringJsonResult.getMsg());
        if (stringJsonResult.getCode() == 0) {
            builder.setPositiveButton("OK", (dialog, which) -> {
                if (stringJsonResult.getMsg().contains("Đăng kí")) {
                    Intent intent = new Intent(activity, ActivityLogin.class);
                    activity.startActivity(intent);
                }
                activity.finish();
            });
        } else {
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
