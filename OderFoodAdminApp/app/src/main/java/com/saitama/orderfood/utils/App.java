package com.saitama.orderfood.utils;

import android.app.Application;

import com.google.gson.Gson;

public class App extends Application {
    private static App app;
    private Gson gson;

    public static App self() {
        return app;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        app = this;
        gson = new Gson();
    }

    public Gson getGSon() {
        return gson;
    }
}
