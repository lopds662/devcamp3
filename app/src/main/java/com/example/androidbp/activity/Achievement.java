package com.example.androidbp.activity;

import android.widget.ImageView;

import com.example.androidbp.R;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class Achievement {
    private String name;
    private String location;
    private ImageView image;
    private int res;

    public Achievement(String name, String location, ImageView image){
        this.name = name;
        this.location = location;
        this.image = image;
    }

    public Achievement(String name, String location){
        this.name = name;
        this.location = location;
        this.res = R.drawable.ic_add_white_24dp;
    }

    public String getName(){
        return  name;
    }

    public String getLocation(){
        return  location;
    }

    public  ImageView getImage(){
        return  image;
    }

    public  int getRes(){
        return res;
    }
}
