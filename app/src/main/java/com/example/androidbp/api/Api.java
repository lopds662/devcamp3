package com.example.androidbp.api;

import com.example.androidbp.api.model.AchievementItem;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.api.model.CreateAchievementBody;
import com.example.androidbp.api.model.ImageUploadResult;
import com.example.androidbp.api.model.Repo;

import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Multipart;
import retrofit.http.POST;
import retrofit.http.Part;
import retrofit.http.Path;
import retrofit.http.Query;
import retrofit.http.QueryMap;
import retrofit.mime.TypedFile;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public interface Api {
    @GET("/users/{user}/repos")
    void listRepos(@Path("user") String user, Callback<List<Repo>> cb);

    @GET("/post/feed")
    void archivementFeed(Callback<List<ArchivementFeedItem>> cb);

    @GET("/post/nearby")
    void achievementNearby(@Query("lat") double lat, @Query("lng") double lng, @Query("distance") int distance, @Query("profile_id") String profileId, Callback<List<ArchivementFeedItem>> cb);

    @POST("/post/add_post")
    void createNewAchievement(@Query("profile_id") String profileId, @Body CreateAchievementBody body, Callback<AchievementItem> cb);

    @POST("/image/upload")
    @Multipart
    void uploadImage(@Part("file") TypedFile file, Callback<ImageUploadResult> cb);

    @GET("/post/detail")
    void showDetail(@Query("id") String idPost, @Query("profile_id") String profileId, Callback<AchievementItem> cb);

    @GET("/post/nearby")
    void archievementNearby(@QueryMap Map<String, Float> m, Callback<List<ArchivementFeedItem>> cb);

    @GET("/profile/complete_post")
    void archivementComplete(@Query("profile_id") String profileId, Callback<List<ArchivementFeedItem>> cb);

    @POST("/profile/complete_post")
    void completeArchievement(@Query("profile_id") String profile_id, @Query("id") String post_id, @Query("lat") double lat, @Query("lng") double lng, @Body String body, Callback<Object> cb);

    @GET("/profile/save_post")
    void archievementSaved(@Query("profile_id") String profileId, Callback<List<ArchivementFeedItem>> cb);


    @POST("/profile/save_post")
    void saveArchievement(@Query("profile_id") String profileId,@Query("id") String id, @Body String body, Callback<Object> cb );


}
