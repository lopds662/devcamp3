package com.example.androidbp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.location.Address;
import android.location.Geocoder;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.AchievementItem;
import com.example.androidbp.api.model.CreateAchievementBody;
import com.example.androidbp.api.model.ImageUploadResult;
import com.example.androidbp.manager.HttpManager;

import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedFile;

public class AddAchievement extends ActionBarActivity {

    private Uri fileUri;
    Bitmap photo;
    String captured_image;
    ImageButton imageButton;
    File file;
    Bitmap bitmap;
    public static final int PICTURE_REQUEST_CODE = 200;
    double latitude = 0;
    double longitude = 0;
    public String addressText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achivement);

        Bundle extras = getIntent().getExtras();

        if (extras != null) {
            latitude = extras.getDouble("latitude");
            longitude = extras.getDouble("longitude");
        }

        Geocoder geocoder = new Geocoder(this, Locale.getDefault());
        addressText = "";
        try {
            List<Address> listA = geocoder.getFromLocation(latitude, longitude, 1);
            ArrayList<String> addressGet = new ArrayList<>();
            addressGet.add(listA.get(0).getCountryName());
            addressGet.add(listA.get(0).getAdminArea());
            addressGet.add(listA.get(0).getSubAdminArea());
            addressGet.add(listA.get(0).getThoroughfare());
            addressGet.add(listA.get(0).getPostalCode());
            for (int i = 0; i < 5; i++) {
                String temp = addressGet.get(i);
                if (temp != null) {
                    addressText = addressText + temp + "  ";
                }
            }
            addressText += "\n";
        } catch (IOException e) {
            e.printStackTrace();
        }

        TextView textView = (TextView) findViewById(R.id.textView);
        textView.setText(addressText);







//        double lat = MainActivity.mLastLocation.getLatitude();
//        double log = MainActivity.mLastLocation.getLongitude();
//        Toast.makeText(this, "Lat "+String.valueOf(lat) + " Long "+String.valueOf(log), Toast.LENGTH_LONG).show();
//
//        String temp = "Lat > " + String.valueOf(lat) + "\nLong > " + String.valueOf(log);
//        TextView textView = (TextView)findViewById(R.id.textView);
//        textView.setText(temp);

//        TextView textView = (TextView) findViewById(R.id.textView);
//        String latitudeText = String.valueOf(MainActivity.mLastLocation.getLatitude());
//        String longitudeText = String.valueOf(MainActivity.mLastLocation.getLongitude());
//        String temp = "test location " + latitudeText + " baeee " + longitudeText;
//        textView.setText(temp);
//        // Get message from the intent
//        Intent intent = getIntent();
//        String message = intent.getStringExtra(MainActivity.EXTRA_MESSAGE);
//
//        // create the text view
//        TextView textView = new TextView(this);
//        textView.setTextSize(40);
//        textView.setText(message);
//
//        // Set the text view as the activity layout
//        setContentView(textView);

        imageButton = (ImageButton) findViewById(R.id.imageButton1);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_add_achivement, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.done) {
            onClickDone();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void takePic(View view){
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivity(intent);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, 0);

        // check camera
        if(getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_ANY)) {
            // open default camera
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            captured_image = System.currentTimeMillis() + ".jpg";
            file = new File(Environment.getExternalStorageDirectory(), captured_image);
            captured_image = file.getAbsolutePath();
            fileUri = Uri.fromFile(file);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, fileUri);

            // start the image capture intent
            startActivityForResult(intent, PICTURE_REQUEST_CODE);
        }
        else {
            Toast.makeText(getApplication(), "Camera not supported", Toast.LENGTH_LONG);
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {


        if(requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
//            Log.d("getPath", fileUri.getPath());
//            try {
//                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
//            } catch (IOException e) {
//                Toast.makeText(getApplication(), "Unable to locate file", Toast.LENGTH_LONG);
//            }

//            imageButton.setImageBitmap(photo);

            bitmap = decodeSampledBitmapFromFile(file.getAbsolutePath(), 1000, 500);
            imageButton.setImageBitmap(bitmap);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    public static Bitmap decodeSampledBitmapFromFile(String path, int reqWidth, int reqHeight) { // BEST QUALITY MATCH

        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(path, options);

        // Calculate inSampleSize
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        options.inPreferredConfig = Bitmap.Config.RGB_565;
        int inSampleSize = 1;

        if (height > reqHeight) {
            inSampleSize = Math.round((float)height / (float)reqHeight);
        }

        int expectedWidth = width / inSampleSize;

        if (expectedWidth > reqWidth) {
            //if(Math.round((float)width / (float)reqWidth) > inSampleSize) // If bigger SampSize..
            inSampleSize = Math.round((float)width / (float)reqWidth);
        }


        options.inSampleSize = inSampleSize;

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(path, options);
    }

    public void onClickDone(){
        TextView textView = (TextView) findViewById(R.id.textAddAchievement);
        String text = textView.getText().toString();
        if (!text.equals("")) {
            addAchievement(text, latitude, longitude);
//            Intent intent = new Intent(this, MainActivity.class);
//            startActivity(intent);
            finish();
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please enter achievement!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAchievement(final String achievement, double latitude, double longitude){
        TypedFile typedImage = new TypedFile("application/octet-stream", file);
        HttpManager.ApiFor(Api.class).uploadImage(typedImage, new Callback<ImageUploadResult>() {
            @Override
            public void success(ImageUploadResult imageUploadResult, Response response) {
                callHttpCreateAchievement(imageUploadResult.id, achievement);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GGG", error.getMessage());
            }
        });
    }

    private void callHttpCreateAchievement(String imageId, String achievement) {
        CreateAchievementBody body = new CreateAchievementBody();
        body.title = achievement;
        body.latitude = (float)latitude;
        body.longitude = (float)longitude;
        body.image_id = imageId;
        String profileId = "";
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            profileId = extras.getString("profile_id");
        }
//        String profileId = "37c6f4fa-c175-4410-8679-7964293412b6"; //TODO: change to current profile id
        HttpManager.ApiFor(Api.class).createNewAchievement(profileId, body, new Callback<AchievementItem>() {
            @Override
            public void success(AchievementItem achievementItem, Response response) {
                Log.d("GGG", String.valueOf(achievementItem));
                Toast.makeText(AddAchievement.this, "Success", Toast.LENGTH_LONG).show();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("GGG", error.getMessage());
            }
        });
    }
}
