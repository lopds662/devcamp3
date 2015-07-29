package com.example.androidbp.manager;

import android.content.Context;

import retrofit.RestAdapter;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public class HttpManager {
    private static HttpManager instance = new HttpManager();
    private Context context;

    private RestAdapter rest;

    private HttpManager() {

    }

//    public static HttpManager instance() {
//        return instance;
//    }

    public static void init(Context ctx) {
        instance.context = ctx;

        instance.rest = new RestAdapter.Builder()
                .setEndpoint("https://api.github.com")
                .build();
    }

    public static <T> T ApiFor(Class<T> t) {
        return instance.rest.create(t);
    }
}
