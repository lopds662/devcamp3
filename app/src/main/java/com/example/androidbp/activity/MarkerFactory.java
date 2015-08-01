package com.example.androidbp.activity;

import android.util.Log;

import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.manager.HttpManager;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by HWANG's on 8/1/2015.
 */
public class MarkerFactory {

    public MarkerFactory(final GoogleMap map, float lat, float lng, float distance){
        Map<String, Float> params = new HashMap<String, Float>();
        // get the post's id & profile's id
        params.put("lat", lat);
        params.put("lng", lng);
        params.put("distance", distance);
        HttpManager.ApiFor(Api.class).archievementNearby(params, new Callback<List<ArchivementFeedItem>>() {
            @Override
            public void success(List<ArchivementFeedItem> archivementFeedItems, Response response) {
                Log.d("GGG", "Loaded" + String.valueOf(archivementFeedItems));
                for(ArchivementFeedItem a: archivementFeedItems) {
                    MarkerOptions m = new MarkerOptions();
                    m.position(new LatLng(a.latitude, a.longitude));
                    m.title(a.title);
                    float color = BitmapDescriptorFactory.HUE_ORANGE;
                    if (a.succeeded) {
                        color = BitmapDescriptorFactory.HUE_GREEN;
                    }
                    else if (a.saved) {
                        color = BitmapDescriptorFactory.HUE_BLUE;
                    }
                    m.icon(BitmapDescriptorFactory.defaultMarker(color));
                    map.addMarker(m);

                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GGG", "Error" + error.getMessage());
            }
        });
    }
}
