package com.example.androidbp.activity;

import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.event.GithubRepoLoaded;
import com.example.androidbp.manager.BusManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.MapFragment;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.text.BreakIterator;

import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
        ,LocationListener{


    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;
    public static final String EXTRA_MESSAGE = "ASFM PASMF";

    GoogleApiClient mGoogleApiClient;
    public Location mLastLocation;
    private BreakIterator mLatitudeText;
    private BreakIterator mLongitudeText;
    private GoogleMap googleMap;
    private LocationRequest mLocationRequest;
    LatLng myPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        //Inject view id to above properties
        ButterKnife.bind(this);

//        //Google Maps api fragemnt
//        FragmentTransaction ft = getFragmentManager().beginTransaction();
//        ft.replace(R.id.frameLayoutFragment, new MapFragment());
//        ft.commit();

        //Show icon on actionbar
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setIcon(R.mipmap.ic_launcher);

        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addApi(Drive.API)
                .addScope(Drive.SCOPE_FILE)
                .build();

        if (checkGooglePlayServices()) {
            buildGoogleApiClient();
        }

        // Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        LocationManager locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if(location!=null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);

            googleMap.addMarker(new MarkerOptions().position(myPosition).title("Start"));


        }
    }

    protected synchronized void buildGoogleApiClient() {
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .build();

    }

    @Override
    protected void onStart() {
        super.onStart();
        BusManager.register(this);
        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();

//            Toast.makeText(this, "Onstart connect", Toast.LENGTH_LONG).show();
        }
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
        startLocationUpdates();

        Toast.makeText(this,
                "Latitude:" + mLastLocation.getLatitude()+
                        ", Longitude:"+mLastLocation.getLongitude(),
                Toast.LENGTH_LONG).show();

    }
    public void logOut(){
        Intent intent = new Intent(this, MainActivity.class);
        Toast.makeText(this, "Log out", Toast.LENGTH_SHORT).show();
    }
    public void savedAchievement() {
        Intent intent = new Intent(this, SavedAchievement.class);
        startActivity(intent);

    }

    @Override
    public void onConnected(Bundle bundle) {
        mLastLocation = LocationServices.FusedLocationApi.getLastLocation(
                mGoogleApiClient);
        if (mLastLocation != null) {
            createLocationRequest();
            Toast.makeText(this,
                    "Latitude:" + mLastLocation.getLatitude()+
                    ", Longitude:"+mLastLocation.getLongitude(),
                    Toast.LENGTH_LONG).show();

        }


    }

    @Override
    public void onConnectionSuspended(int i) {

    }

    @Override
    public void onConnectionFailed(ConnectionResult connectionResult) {

    }

    private boolean checkGooglePlayServices() {

        int checkGooglePlayServices = GooglePlayServicesUtil
                .isGooglePlayServicesAvailable(this);
        if (checkGooglePlayServices != ConnectionResult.SUCCESS) {
			/*
			* google play services is missing or update is required
			*  return code could be
			* SUCCESS,
			* SERVICE_MISSING, SERVICE_VERSION_UPDATE_REQUIRED,
			* SERVICE_DISABLED, SERVICE_INVALID.
			*/
            GooglePlayServicesUtil.getErrorDialog(checkGooglePlayServices,
                    this, REQUEST_CODE_RECOVER_PLAY_SERVICES).show();

            return false;
        }

        return true;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE_RECOVER_PLAY_SERVICES) {

            if (resultCode == RESULT_OK) {
                // Make sure the app is not already connected or attempting to connect
                if (!mGoogleApiClient.isConnecting() &&
                        !mGoogleApiClient.isConnected()) {
                    mGoogleApiClient.connect();
                }
            } else if (resultCode == RESULT_CANCELED) {
                Toast.makeText(this, "Google Play Services must be installed.",
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }
    }
    protected void createLocationRequest() {
        mLocationRequest = new LocationRequest();
        mLocationRequest.setInterval(20000);
        mLocationRequest.setFastestInterval(5000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }
    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Toast.makeText(this,"Current >> " + "Latitude:" + mLastLocation.getLatitude()+
                ", Longitude:"+mLastLocation.getLongitude(),Toast.LENGTH_LONG).show();

    }
    private void updateUI() {
        mLatitudeText.setText(String.valueOf(mLastLocation.getLatitude()));
        mLongitudeText.setText(String.valueOf(mLastLocation.getLongitude()));
    }

    @Override
    protected void onPause() {
        super.onPause();
        stopLocationUpdates();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

    }

}
