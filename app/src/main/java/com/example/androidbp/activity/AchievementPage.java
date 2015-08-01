package com.example.androidbp.activity;

import android.graphics.Color;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.AchievementItem;
import com.example.androidbp.manager.HttpManager;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AchievementPage extends ActionBarActivity {

    @Bind(R.id.imageView_achievement)
    public ImageView imageView_ac;

    @Bind(R.id.imageView_creator)
    public ImageView imageView_cr;

    @Bind(R.id.textView_achievement)
    public TextView textView_ac;

    @Bind(R.id.textView_creator)
    public TextView textView_cr;

    @Bind(R.id.completeButton)
    public Button completeBt;


    public static String id;
    public static String profileId;
    public static double lat;
    public static double lng;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        ButterKnife.bind(this);

        id = "";
        profileId = "0a776aa7-6073-4998-8802-b4ee79ff5d2a";
        lat = 0.0f;
        lng = 0.0f;

        // get id's from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
//            profileId = extras.getString("profile_id");
            lat = extras.getFloat("lat");
            lng = extras.getFloat("lng");
        }

        Log.d("GGG", "Getting Achievement for id:" + id + " and for profile id:" + profileId);

//        Map<String, String> params = new HashMap<String, String>();
//        params.put("id", id);
//        params.put("profile_id", profileId);

        HttpManager.ApiFor(Api.class).showDetail(id, profileId, new Callback<AchievementItem>() {
            @Override
            public void success(AchievementItem achievementItem, Response response) {
                Log.d("GGG", "Detail Success:" + String.valueOf(achievementItem));
                // convert url to bitmap
                // ac = achievementItem.image_url
                // cr = achievementItem.creator.avatar_image_url
//                imageView_ac.setImageBitmap(null);
                Picasso.with(imageView_ac.getContext()).load(achievementItem.image_url).placeholder(R.drawable.ic_media_play).into(imageView_ac);
                Log.d("CCC", achievementItem.image_url);
//                imageView_cr.setImageBitmap(null);
                Picasso.with(imageView_cr.getContext()).load(achievementItem.owner_profile.profile_image).placeholder(R.drawable.ic_media_play).into(imageView_cr);

//                TextView textView_ac = (TextView) findViewById(R.id.textView_achievement);
//                TextView textView_cr = (TextView) findViewById(R.id.textView_creator);
//                textView_ac.setText(achievementItem.title);
                textView_ac.setText(achievementItem.title);
                textView_cr.setText(achievementItem.owner_profile.name);
//                Log.d("GGG", "succ");


                Button completeButton = (Button) findViewById(R.id.completeButton);

                if (achievementItem.succeeded != null && achievementItem.succeeded == true) {

                    completeButton.setText("Completed");
                    completeBt.setBackgroundColor(Color.parseColor("#CCFFCC"));
                    completeButton.setEnabled(false);
                }else{
                    completeBt.setEnabled(true);
                    completeBt.setBackgroundColor(Color.parseColor("#66FF66"));
                }

                completeButton.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        completeAchievement(v);
                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GGG", "Detail Error:" + error.getMessage());
                error.printStackTrace();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_achievement_page, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.saveButton) {
            onClickSave();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    private void onClickSave() {
        TextView textView = (TextView) findViewById(R.id.textView_achievement);
        String text = textView.getText().toString();

        HttpManager.ApiFor(Api.class).saveArchievement(profileId, id, "", new Callback<Object>() {
            @Override
            public void success(Object objects, Response response) {
                Log.d("AAA", "Loaded" + String.valueOf(objects));
                Toast.makeText(null, "Saved", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("AAA", "Loaded" + String.valueOf(error));
//                Toast.makeText(null, "Error Saving ", Toast.LENGTH_LONG).show();
            }
        });

    }

    public void completeAchievement(View view) {

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            lat = extras.getDouble("latitude");
            lng = extras.getDouble("longitude");
        }

        Log.d("GGG", "completing achievement id:" + id + " with lat:" + lat + " lng:" + lng + " for profile id:" + profileId);
        HttpManager.ApiFor(Api.class).completeArchievement(profileId, id, lat, lng, "", new Callback<Object>() {
            @Override
            public void success(Object obj, Response response) {
                Log.d("GGG", "Loaded" + String.valueOf(obj));
                completeBt.setEnabled(false);
                completeBt.setBackgroundColor(Color.parseColor("#CCFFCC"));
                Toast.makeText(null, "Successfully completing Achievement", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GGG", "Error" + error.getMessage());
//                Toast.makeText(null, "Error completing Achievement", Toast.LENGTH_LONG).show();
            }
        });
    }
}

