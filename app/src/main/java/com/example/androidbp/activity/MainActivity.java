package com.example.androidbp.activity;

import android.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.Repo;
import com.example.androidbp.event.GithubRepoLoaded;
import com.example.androidbp.manager.BusManager;
import com.example.androidbp.manager.HttpManager;
import com.google.android.gms.maps.MapFragment;
import com.squareup.otto.Subscribe;

import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Inject view id to above properties
        ButterKnife.bind(this);

        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutFragment, new MapFragment());
        ft.commit();

        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

//        getSupportFragmentManager().findFragmentById()

        //Call api from github
//        HttpManager.ApiFor(Api.class).listRepos("stackle", new Callback<List<Repo>>() {
//            @Override
//            public void success(List<Repo> repos, Response response) {
//                Log.d("GGGG", String.valueOf(repos));
//                BusManager.post(new GithubRepoLoaded(repos));
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.e("GGGG", String.valueOf(error));
//            }
//        });



    }

    @Override
    protected void onStart() {
        super.onStart();
        BusManager.register(this);
    }

    @Override
    protected void onStop() {
        BusManager.unregister(this);
        super.onStop();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.com_ach) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void HandleCountButtonClicked(GithubRepoLoaded e) {

    }
}
