package com.example.androidbp.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.event.GithubRepoLoaded;
import com.example.androidbp.manager.BusManager;
import com.google.android.gms.maps.MapFragment;
import com.squareup.otto.Subscribe;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    public static final String EXTRA_MESSAGE = "ASFM PASMF";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Inject view id to above properties
        ButterKnife.bind(this);

        //Google Maps api fragemnt
        FragmentTransaction ft = getFragmentManager().beginTransaction();
        ft.replace(R.id.frameLayoutFragment, new MapFragment());
        ft.commit();

        //Show icon on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

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

        switch (id){
            case (R.id.com_ach):
                toCompleteAchView();
                break;
            case (R.id.saved_ach):
                savedAchievement();
                break;
            case (R.id.log_out):
                logOut();
                break;
                

        }




        return super.onOptionsItemSelected(item);
    }

    @Subscribe
    public void HandleCountButtonClicked(GithubRepoLoaded e) {

    }

    public void toCompleteAchView(){
        Intent intent = new Intent(this,CompleteAchievement.class);
        startActivity(intent);
    }

    public void sendMessage(View view){
        Intent intent = new Intent(this, AddAchievement.class);
        String message = "String Testing";
        intent.putExtra(EXTRA_MESSAGE, message);
        startActivity(intent);

    }
    public void logOut(){
        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
    }
    public void savedAchievement(){
        Intent intent = new Intent(this, SavedAchievement.class);
        startActivity(intent);

    }
}
