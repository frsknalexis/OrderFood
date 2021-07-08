package com.saitama.orderfood.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import com.saitama.orderfood.activity.ActivityMain;
import com.saitama.orderfood.api.RestaurantAPI;
import com.saitama.orderfood.dto.JsonResult;
import com.saitama.orderfood.model.RestaurantModel;
import com.saitama.orderfood.utils.ContainsUtil;
import com.saitama.orderfood.utils.SharedPrefsUtil;

import okhttp3.RequestBody;

public class RestaurantRegisterTask extends AsyncTask<RequestBody, Void, JsonResult<RestaurantModel>> {

    @SuppressLint("StaticFieldLeak")
    private final Activity activity;
    private final ProgressDialog dialog;

    public RestaurantRegisterTask(Activity ctx) {
        dialog = new ProgressDialog(ctx);
        activity = ctx;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        this.dialog.setMessage("Vui lòng đợi...");
        this.dialog.setCancelable(false);
        this.dialog.show();
    }

    @Override
    protected JsonResult<RestaurantModel> doInBackground(RequestBody... requestBodies) {
        return RestaurantAPI.register(requestBodies[0]);
    }

    @Override
    protected void onPostExecute(JsonResult<RestaurantModel> stringJsonResult) {
        super.onPostExecute(stringJsonResult);

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(stringJsonResult.getMsg());
        if (stringJsonResult.getCode() == 0) {
            SharedPrefsUtil.getInstance().put(ContainsUtil.RESTAURANT_CLIENT, stringJsonResult.getData());
            builder.setPositiveButton("OK", (dialog, which) -> {
                Intent intent = new Intent(activity, ActivityMain.class);
                activity.startActivity(intent);
                activity.finish();
            });
        } else
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        if (dialog.isShowing()) {
            SystemClock.sleep(1000);
            dialog.dismiss();
        }

        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
