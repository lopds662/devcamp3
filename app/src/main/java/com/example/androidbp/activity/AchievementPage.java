package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.AchievementItem;
import com.example.androidbp.api.model.CreateAchievementBody;
import com.example.androidbp.api.model.ImageUploadResult;
import com.example.androidbp.manager.HttpManager;

import java.util.HashMap;
import java.util.Map;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AchievementPage extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        Map<String, String> params = new HashMap<String, String>();
        // get the post's id & profile's id
        params.put("id", "");
        params.put("profile_id", "");
        HttpManager.ApiFor(Api.class).showDetail(params, new Callback<AchievementItem>() {
            @Override
            public void success(AchievementItem achievementItem, Response response) {
                Log.d("GGG", String.valueOf(achievementItem));
                ImageView imageView_ac = (ImageView) findViewById(R.id.imageView_achievement);
                ImageView imageView_cr = (ImageView) findViewById(R.id.imageView_creator);
                // convert url to bitmap
                // ac = achievementItem.image_url
                // cr = achievementItem.creator.avatar_image_url
                imageView_ac.setImageBitmap(null);
                imageView_cr.setImageBitmap(null);

                TextView textView_ac = (TextView) findViewById(R.id.textView_achievement);
                TextView textView_cr = (TextView) findViewById(R.id.textView_creator);
                textView_ac.setText(achievementItem.title);
                textView_cr.setText(achievementItem.creator.name);

                Button completeButton = (Button) findViewById(R.id.completeButton);

                if (achievementItem.succeeded) {
                    completeButton.setText("Completed");
                    completeButton.setEnabled(false);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GGG", error.getMessage());
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
//        if (id == R.id.action_settings) {
//            return true;
//        }

        return super.onOptionsItemSelected(item);
    }
}
