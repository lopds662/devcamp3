package com.example.androidbp;

import android.app.Application;

import com.example.androidbp.manager.HttpManager;

/**
 * Created by sharp on 12/1/2014 AD.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        HttpManager.init(getApplicationContext());
    }

    @Override
    public void onTerminate() {

        super.onTerminate();
    }
}
