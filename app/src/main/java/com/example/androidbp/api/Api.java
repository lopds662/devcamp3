package com.example.androidbp.api;

import com.example.androidbp.api.model.AchievementItem;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.api.model.CreateAchievementBody;
import com.example.androidbp.api.model.ImageUploadResult;
import com.example.androidbp.api.model.Repo;

import java.util.List;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.mime.TypedFile;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public interface Api {
    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repo>> cb);

    @GET("/post/feed")
    void archivementFeed(Callback<List<ArchivementFeedItem>> cb);

    @POST("/post/add_post")
    void createNewAchievement(@Body CreateAchievementBody body, Callback<AchievementItem> cb);

    @POST("/image/upload")
    @Multipart
    void uploadImage(@Part("file") TypedFile file, Callback<ImageUploadResult> cb);
}
