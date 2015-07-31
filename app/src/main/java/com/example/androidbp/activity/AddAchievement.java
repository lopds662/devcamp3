package com.example.androidbp.activity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
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

import java.io.File;
import java.io.IOException;

public class AddAchievement extends ActionBarActivity {

    private Uri fileUri;
    Bitmap photo;
    String captured_image;
    ImageButton imageButton;
    public static final int PICTURE_REQUEST_CODE = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achivement);


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
            File file = new File(Environment.getExternalStorageDirectory(), captured_image);
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
        Log.d("getPath", fileUri.getPath());

        if(requestCode == PICTURE_REQUEST_CODE && resultCode == RESULT_OK) {
            try {
                photo = MediaStore.Images.Media.getBitmap(this.getContentResolver(), fileUri);
            } catch (IOException e) {
                Toast.makeText(getApplication(), "Unable to locate file", Toast.LENGTH_LONG);
            }

            imageButton = (ImageButton) findViewById(R.id.imageButton1);
            imageButton.setImageBitmap(photo);

        }
        super.onActivityResult(requestCode, resultCode, data);
    }
    public void onClickDone(){
        TextView textView = (TextView) findViewById(R.id.textAddAchievement);
        String text = textView.getText().toString();
        if (!text.equals("")) {
            addAchievement(text);
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
            Toast.makeText(this, "Done", Toast.LENGTH_SHORT).show();
        }else{
            Toast.makeText(this, "Please enter achievement!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAchievement(String achievement){
        
    }
}
