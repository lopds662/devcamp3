package com.example.androidbp.activity;

import android.content.Intent;
import android.location.Address;
import android.location.Criteria;
import android.location.Geocoder;
import android.location.Location;
import android.location.LocationManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListPopupWindow;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.event.GithubRepoLoaded;
import com.example.androidbp.manager.BusManager;
import com.example.androidbp.manager.HttpManager;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.GooglePlayServicesUtil;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.drive.Drive;
import com.google.android.gms.location.LocationListener;
import com.google.android.gms.location.LocationRequest;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.BitmapDescriptorFactory;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;
import com.squareup.otto.Subscribe;

import java.io.IOException;
import java.text.BreakIterator;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener
        ,LocationListener {


    private static int REQUEST_CODE_RECOVER_PLAY_SERVICES = 200;
    public static final String EXTRA_MESSAGE = "ASFM PASMF";
    public static final String com_arc = "Completed Archievements";
    public static final String sav_arc = "Saved Archievements";
    public static final String log_out = "Log out";

    public GoogleApiClient mGoogleApiClient;
    public static Location mLastLocation;
    protected GoogleMap googleMap;
    protected LocationRequest mLocationRequest;
    protected LocationManager locationManager;
    protected LatLng myPosition;

    private HashMap<String, ArchivementFeedItem> markerData = new HashMap<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);
        new myLocationOverlay();
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

        if (mGoogleApiClient != null) {
            mGoogleApiClient.connect();
        }


        // Build Map Code.... .
        // Getting reference to the SupportMapFragment of activity_main.xml
        SupportMapFragment fm = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);

        // Getting GoogleMap object from the fragment
        googleMap = fm.getMap();

        googleMap.getUiSettings().setScrollGesturesEnabled(true);

        // Enabling MyLocation Layer of Google Map
        googleMap.setMyLocationEnabled(true);

        googleMap.getUiSettings().setMapToolbarEnabled(false);

        // Getting LocationManager object from System Service LOCATION_SERVICE
        locationManager = (LocationManager) getSystemService(LOCATION_SERVICE);

        // Creating a criteria object to retrieve provider
        Criteria criteria = new Criteria();

        // Getting the name of the best provider
        String provider = locationManager.getBestProvider(criteria, true);

        // Getting Current Location
        Location location = locationManager.getLastKnownLocation(provider);

        if (location != null) {
            // Getting latitude of the current location
            double latitude = location.getLatitude();

            // Getting longitude of the current location
            double longitude = location.getLongitude();

            // Creating a LatLng object for the current location
            LatLng latLng = new LatLng(latitude, longitude);

            myPosition = new LatLng(latitude, longitude);

//            googleMap.addMarker(new MarkerOptions().position(myPosition).title("Start"));



        }

        // click marker to show detail on AchievementPage
        googleMap.setOnMarkerClickListener(new GoogleMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Intent i = new Intent(getApplicationContext(), AchievementPage.class);
                // get the post's id & profile's id ???????
//                i.putExtra("profile_id", "");
                ArchivementFeedItem item = markerData.get(marker.getId());
                i.putExtra("id", item.id);
//                i.putExtra("profile_id", "");
                startActivity(i);
                return true;
            }
        });

//        getUiSettings().setMapToolbarEnabled(false);
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
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
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

//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        // Handle action bar item clicks here. The action bar will
//        // automatically handle clicks on the Home/Up button, so long
//        // as you specify a parent activity in AndroidManifest.xml.
//        int id = item.getItemId();
//
//        //noinspection SimplifiableIfStatement
//
//        switch (id) {
//            case (R.id.com_ach):
//                toCompleteAchView();
//                break;
//            case (R.id.saved_ach):
//                savedAchievement();
//                break;
//            case (R.id.log_out):
//                logOut();
//                break;
//        }
//
//        return super.onOptionsItemSelected(item);
//    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        switch (item.getItemId())
        {
            case R.id.action_overflow:
                // Works as long as list item is always visible and does not go into the menu overflow
                final View menuItemView = findViewById(R.id.action_overflow);
                onListPopUp(menuItemView);
                Log.w("TESTING OVERFLOW", "You called me OverFlow");

                return true;
            default:
            {
                return super.onOptionsItemSelected(item);
            }
        }
    }

    @Subscribe
    public void HandleCountButtonClicked(GithubRepoLoaded e) {

    }

    public void toCompleteAchView() {
        Intent intent = new Intent(this, CompleteAchievementActivity.class);
        startActivity(intent);
    }

    public void addAchievement(View view) {
//        Intent intent = new Intent(this, AddAchievement.class);
//        startActivity(intent);

        Toast.makeText(this,
                "Latitude:" + mLastLocation.getLatitude() +
                        ", Longitude:" + mLastLocation.getLongitude(),
                Toast.LENGTH_LONG).show();

        double lat = mLastLocation.getLatitude();
        double log = mLastLocation.getLongitude();
        Intent i = new Intent(getApplicationContext(), AddAchievement.class);
        i.putExtra("latitude", lat);
        i.putExtra("longitude", log);
        startActivity(i);
        //Log.d("ADDRESS", temp);
    }

    public void logOut() {
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

            double latitude = mLastLocation.getLatitude();
            double longitude = mLastLocation.getLongitude();
            LatLng latLng = new LatLng(latitude, longitude);

            CameraUpdate cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 10);
            googleMap.animateCamera(cameraUpdate);

            startLocationUpdates();
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
        mLocationRequest.setInterval(5000);
        mLocationRequest.setFastestInterval(1000);
        mLocationRequest.setPriority(LocationRequest.PRIORITY_HIGH_ACCURACY);
    }

    protected void startLocationUpdates() {
        LocationServices.FusedLocationApi.requestLocationUpdates(mGoogleApiClient, mLocationRequest, this);
    }

    @Override
    public void onLocationChanged(Location location) {
        mLastLocation = location;
        Log.d("GGG", "Current >> " + "Latitude:" + mLastLocation.getLatitude() +
                ", Longitude:" + mLastLocation.getLongitude());

        updateNearbyAchievementList(location.getLatitude(), location.getLongitude());
    }

    private void updateNearbyAchievementList(double lat, double lng) {
        HttpManager.ApiFor(Api.class).achievementNearby(lat, lng, 400000000, new Callback<List<ArchivementFeedItem>>() {
            @Override
            public void success(List<ArchivementFeedItem> archivementFeedItems, Response response) {
                Log.d("GGG", "nearby succeed:" + archivementFeedItems.toString());

                googleMap.clear();
                for(ArchivementFeedItem item : archivementFeedItems) {
                    LatLng latLng = new LatLng(item.latitude, item.longitude);
//                    Marker marker = googleMap.addMarker(new MarkerOptions().position(latLng).title(item.title));
                    MarkerOptions m = new MarkerOptions();
                    m.position(latLng);
                    m.title(item.title);
                    float color = BitmapDescriptorFactory.HUE_ORANGE;
                    if (item.succeeded) {
                        color = BitmapDescriptorFactory.HUE_GREEN;
                    }
                    else if (item.saved) {
                        color = BitmapDescriptorFactory.HUE_BLUE;
                    }
                    m.icon(BitmapDescriptorFactory.defaultMarker(color));

                    Marker marker = googleMap.addMarker(m);
                    markerData.put(marker.getId(), item);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GGG", "Failed to get nearby location due to: " + error.getMessage());
                error.printStackTrace();
            }
        });
    }

    @Override
    protected void onPause() {
        super.onPause();
    }

    protected void stopLocationUpdates() {
        LocationServices.FusedLocationApi.removeLocationUpdates(
                mGoogleApiClient, this);

    }

    public void onListPopUp(View anchor)
    {
        // This a sample dat to fill our ListView
        // find pic, name, last name to be correctly put
        ArrayList<DataItem> personItem = new ArrayList<DataItem>();
        personItem.add(new DataItem(R.drawable.ic_add_white_24dp, "NAME", "@USERNAME"));
        personItem.add(new DataItem(0, com_arc, ""));
        personItem.add(new DataItem(0, sav_arc, ""));
        personItem.add(new DataItem(0, log_out, ""));
        // Initialise our adapter
        ListPopupWindowAdapter mListPopUpAdapter = new ListPopupWindowAdapter(this, personItem);

        //Initialise our ListPopupWindow instance
        final ListPopupWindow pop = new ListPopupWindow(this);
        pop.setVerticalOffset(-105);
        pop.setHorizontalOffset(-96);
        // Configure ListPopupWindow properties
        pop.setAdapter(mListPopUpAdapter);
        // Set the view below/above which ListPopupWindow dropdowns
        pop.setAnchorView(anchor);
        // Setting this enables window to be dismissed by click outside ListPopupWindow
        pop.setModal(true);
        // Sets the width of the ListPopupWindow
        pop.setContentWidth(370);
        // Sets the Height of the ListPopupWindow
        pop.setHeight(ListPopupWindow.WRAP_CONTENT);
        // Set up a click listener for the ListView items
        pop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {
                // Dismiss the LisPopupWindow when a list item is clicked
                pop.dismiss();

                String title = ((DataItem) adapterView.getItemAtPosition(position)).name;
                switch (title) {
                    case (com_arc):
                        toCompleteAchView();
                        break;
                    case (sav_arc):
                        savedAchievement();
                        break;
                    case (log_out):
                        logOut();
                        break;
                }    }
        });
        pop.show();
    }

}
