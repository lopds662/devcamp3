package com.example.androidbp.api.model;

import com.google.gson.annotations.SerializedName;

/**
 * Created by sharp on 7/29/2015 AD.
 */
public class Repo {
    public int id;

    public String name, full_name, html_url;

    @SerializedName("private")
    public String isPrivate;

    @Override
    public String toString() {
        return name + " " + full_name + " " + html_url + " " + isPrivate;
    }
}
