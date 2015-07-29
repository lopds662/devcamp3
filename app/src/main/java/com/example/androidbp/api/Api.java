package com.example.androidbp.api;

import com.example.androidbp.api.model.Repo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.GET;
import retrofit.http.Path;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public interface Api {
    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repo>> cb);
}
