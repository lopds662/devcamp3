package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidbp.R;

import java.util.ArrayList;

public class CompleteAchievement extends ActionBarActivity {

    ListView listView;
    Achievement[] listAchieCom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_complete_achievement);

        // Get ListView object from xml
        listView = (ListView) findViewById(R.id.list);

        // Defined Array listCompleted to show in ListView
//        listCompleted = getListCompleted();
        listAchieCom = getListAchieCom();


        // Define a new Adapter
        // First parameter - Context
        // Second parameter - Layout for the row
        // Third parameter - ID of the TextView to which the data is written
        // Forth - the Array of data

//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listCompleted);
        CustomArrayAdapter achieAdapter = new CustomArrayAdapter(this,android.R.layout.simple_list_item_1,listAchieCom);


        // Assign adapter to ListView
//        listView.setAdapter(adapter);
        listView.setAdapter(achieAdapter);

        // ListView Item Click Listener
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {

                // ListView Clicked item index
                int itemPosition     = position;

                // ListView Clicked item value
                Achievement  itemValue    = (Achievement) listView.getItemAtPosition(position);

                // Show Alert

                Toast.makeText(getApplicationContext(),
                        "Position :"+itemPosition+"  ListItem : " +itemValue.getName() , Toast.LENGTH_SHORT)
                        .show();

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

    public Achievement[] getListAchieCom(){
        Data a = new Data();
        Achievement[] list1 = new Achievement[Data.completedAchievement.size()];
        Data.completedAchievement.toArray(list1);
        return list1;
    }

    /**
     * Get List CompleteAchievement from database.
     * @return array of Achievement
     */
    public Achievement[] getListCompleteAchievement(){

        return null;
    }
}
