package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.manager.HttpManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class CompleteAchievementActivity extends ActionBarActivity {

    @Bind(R.id.list)
    public RecyclerView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_achievement);

        ButterKnife.bind(this);

        final CustomArrayAdapter achieAdapter = new CustomArrayAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(achieAdapter);

        HttpManager.ApiFor(Api.class).archivementFeed(new Callback<List<ArchivementFeedItem>>() {
            @Override
            public void success(List<ArchivementFeedItem> archivementFeedItems, Response response) {
                Log.d("GGG", "Loaded" + String.valueOf(archivementFeedItems));
                achieAdapter.setData(archivementFeedItems);
                achieAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d("GGG", "Error" + error.getMessage());
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_complete_achievement, menu);
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
