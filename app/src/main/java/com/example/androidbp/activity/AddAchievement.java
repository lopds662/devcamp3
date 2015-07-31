package com.example.androidbp.activity;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;

public class AddAchievement extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_achivement);
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

    public void cameraOn(View view){
        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
        startActivity(intent);
//        Intent intent = new Intent("android.media.action.IMAGE_CAPTURE");
//        startActivityForResult(intent, 0);
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
            Toast.makeText(this, "Please input achievement!", Toast.LENGTH_SHORT).show();
        }
    }

    public void addAchievement(String achievement){

    }
}
