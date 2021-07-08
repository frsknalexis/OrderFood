package com.saitama.orderfood.task;

import android.os.AsyncTask;

import com.saitama.orderfood.dto.JsonResult;

import okhttp3.RequestBody;

public class OrderConfirmTask extends AsyncTask<RequestBody, Void, JsonResult<String>> {

    @Override
    protected JsonResult<String> doInBackground(RequestBody... requestBodies) {
        return null;
    }

}
