package com.example.androidbp.activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.location.Location;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.event.GithubRepoLoaded;
import com.example.androidbp.manager.BusManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.MapFragment;
import com.squareup.otto.Subscribe;

import java.text.BreakIterator;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener {

    public static final String EXTRA_MESSAGE = "ASFM PASMF";
    GoogleApiClient mGoogleApiClient;
    private Location mLastLocation;
    private BreakIterator mLatitudeText;
    private BreakIterator mLongitudeText;

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

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();
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

    public void addAchievement(View view){
        Intent intent = new Intent(this, AddAchievement.class);
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

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();
    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
            mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
            String temp = "Test Located"+mLatitudeText.toString() + mLongitudeText.toString();
            Toast.makeText(this, "Temp", Toast.LENGTH_LONG).show();
        }

    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }
}
