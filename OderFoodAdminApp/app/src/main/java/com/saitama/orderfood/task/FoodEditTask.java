package com.saitama.orderfood.task;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.SystemClock;

import androidx.appcompat.app.AlertDialog;

import com.saitama.orderfood.api.FoodAPI;
import com.saitama.orderfood.dto.JsonResult;

import okhttp3.RequestBody;

public class FoodEditTask extends AsyncTask<RequestBody, Void, JsonResult<String>> {

    @SuppressLint("StaticFieldLeak")
    private final Activity activity;
    private final ProgressDialog dialog;

    public FoodEditTask(Activity ctx) {
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
    protected JsonResult<String> doInBackground(RequestBody... requestBodies) {
        return FoodAPI.edit(requestBodies[0]);
    }

    @Override
    protected void onPostExecute(JsonResult<String> stringJsonResult) {
        super.onPostExecute(stringJsonResult);
        if (dialog.isShowing()) {
            SystemClock.sleep(1000);
            dialog.dismiss();
        }

        AlertDialog.Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle(stringJsonResult.getMsg());
        if (stringJsonResult.getCode() == 0) {
            builder.setPositiveButton("OK", (dialog, which) -> {
                activity.finish();
            });
        } else
            builder.setPositiveButton("OK", (dialog, which) -> dialog.dismiss());
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }
}
