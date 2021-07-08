package com.saitama.orderfood.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SharedPrefsUtil {
    private static final String PREFS_NAME = "share_prefs";
    private static SharedPrefsUtil mInstance;
    private SharedPreferences mSharedPreferences;

    private SharedPrefsUtil() {
        mSharedPreferences = App.self().getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    public static SharedPrefsUtil getInstance() {
        if (mInstance == null) {
            mInstance = new SharedPrefsUtil();
        }
        return mInstance;
    }

    @SuppressWarnings("unchecked")
    public <T> T get(String key, Class<T> anonymousClass) {
        if (anonymousClass == String.class) {
            return (T) mSharedPreferences.getString(key, "");
        } else if (anonymousClass == Boolean.class) {
            return (T) Boolean.valueOf(mSharedPreferences.getBoolean(key, false));
        } else if (anonymousClass == Float.class) {
            return (T) Float.valueOf(mSharedPreferences.getFloat(key, 0));
        } else if (anonymousClass == Integer.class) {
            return (T) Integer.valueOf(mSharedPreferences.getInt(key, 0));
        } else if (anonymousClass == Long.class) {
            return (T) Long.valueOf(mSharedPreferences.getLong(key, 0));
        } else
            return (T) App.self().getGSon().fromJson(mSharedPreferences.getString(key, null), anonymousClass);
    }

    public <T> void put(String key, T data) {
        try {
            SharedPreferences.Editor editor = mSharedPreferences.edit();
            if (data instanceof String) {
                editor.putString(key, (String) data);
            } else if (data instanceof Boolean) {
                editor.putBoolean(key, (Boolean) data);
            } else if (data instanceof Float) {
                editor.putFloat(key, (Float) data);
            } else if (data instanceof Integer) {
                editor.putInt(key, (Integer) data);
            } else if (data instanceof Long) {
                editor.putLong(key, (Long) data);
            } else
                editor.putString(key, App.self().getGSon().toJson(data));
            editor.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void clear() {
        mSharedPreferences.edit().clear().apply();
    }

    public void remove(String key) {
        mSharedPreferences.edit().remove(key).apply();
    }
}
