package com.mph.retrofitutils;

import android.app.Application;

import com.mph.retrofitutils.exception.CrashHandler;

/**
 * Created by：hcs on 2016/10/21 14:05
 * e_mail：aaron1539@163.com
 */
public class MyApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        CrashHandler crashHandler = CrashHandler.getInstance();
        crashHandler.init(getApplicationContext());
    }
}
