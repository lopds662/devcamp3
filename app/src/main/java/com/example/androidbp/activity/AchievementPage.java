package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
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

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AchievementPage extends ActionBarActivity {

    @Bind(R.id.imageView_achievement)
    public ImageView imageView_ac;

    @Bind(R.id.imageView_creator)
    public ImageView imageView_cr;

    @Bind(R.id.textView_achievement)
    public TextView textView_ac;

    @Bind(R.id.textView_creator)
    public TextView textView_cr;

    public String idPost;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_achievement_page);

        ButterKnife.bind(this);

        String id = "";
        String profile_id = "";

        // get id's from MainActivity
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            id = extras.getString("id");
            profile_id = extras.getString("profile_id");
        }

        Map<String, String> params = new HashMap<String, String>();
        params.put("id", id);
        params.put("profile_id", profile_id);
        HttpManager.ApiFor(Api.class).showDetail(params, new Callback<AchievementItem>() {
            @Override
            public void success(AchievementItem achievementItem, Response response) {
                Log.d("GGG", String.valueOf(achievementItem));
                // convert url to bitmap
                // ac = achievementItem.image_url
                // cr = achievementItem.creator.avatar_image_url
                imageView_ac.setImageBitmap(null);
                imageView_cr.setImageBitmap(null);

//                TextView textView_ac = (TextView) findViewById(R.id.textView_achievement);
//                TextView textView_cr = (TextView) findViewById(R.id.textView_creator);
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
