package com.example.androidbp.api.model;

import java.util.List;

/**
 * Created by Game on 8/1/2015.
 */
public class AchievementItem {
    public String id, title, image_url;
    public Float latitude, longitude;
    public Boolean saved, succeeded;
    public Profile owner_profile;
    public List<Profile> succeeded_profiles;
}
