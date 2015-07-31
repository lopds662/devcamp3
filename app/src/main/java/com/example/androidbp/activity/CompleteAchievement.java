package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.example.androidbp.R;

public class CompleteAchievement extends ActionBarActivity {

    ListView listView;
    String[] listCompleted;
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

    public String[] getListCompleted(){
        String[] listCompleted = new String[] { "Android List View",
                "Adapter implementation",
                "Simple List View In Android",
                "Create List View Android",
                "Android Example",
                "List View Source Code",
                "List View Array Adapter",
                "Android Example List View",
                "1" ,
                "2",
                "3",
                "4",
                "5",
                "6",
                "7",
                "8"
        };
        return listCompleted;
    }

    public Achievement[] getListAchieCom(){
        Achievement[] list = new Achievement[]{new Achievement("Ban Ing Koaw","Saraburi"),
                new Achievement("Kasetsart","Bankok"),
                new Achievement("Nam Tok Chet Sao Noi","Saraburi"),
                new Achievement("Khun Malee Grape Farm","Saraburi"),
                new Achievement("Papasara Grape Farm","Saraburi"),
                new Achievement("ÊÇ¹Í§Øè¹ÀÙÍÁÃáÅÐäÇ¹ìÍ§Øè¹ÀÙÍÁÃ","Saraburi"),
                new Achievement("Kamnan Meng Grape Farm","Saraburi"),
                new Achievement("ÍØâÁ§¤ìµé¹äÁé","Saraburi"),
                new Achievement("·Øè§·Ò¹µÐÇÑ¹áÊÅ§¾Ñ¹","Saraburi"),
                new Achievement("MuakLek ATV","Saraburi"),
                new Achievement("Buddhist Temple ÇÑ´¾ÃÐ¾Ø·¸©ÒÂ ","Saraburi"),
                new Achievement("Wat Phra Phutthabat","Saraburi"),
                new Achievement("ÈÙ¹ÂìÈÖ¡ÉÒ¸ÃÃÁªÒµÔáÅÐ·èÍ§à·ÕèÂÇàªÔ§¹ÔàÇÈ¹ìà¨ç´¤µ-â»è§¡éÍ¹àÊÒ","Saraburi"),
                new Achievement("¹éÓµ¡â¡Ã¡ÍÕ´¡","Saraburi"),
                new Achievement("Pa Sak Cholasit ","Saraburi"),
                new Achievement("Namtok Sam Lan National Park","Saraburi"),
                new Achievement("laisakunahansa","Saraburi"),
                new Achievement("Rai Kusuma Resort ","Saraburi"),
                new Achievement("Wat Tham Phra Phothisat","Saraburi"),
                new Achievement("ÊÇ¹ÃØ¡¢ªÒµÔÁÇ¡àËÅç¡","Saraburi"),
                new Achievement("ÃéÒ¹ÍÒËÒÃà¤ÕÂ§ÇÒÃÔ¹ Í. àÊÒäËé","Saraburi"),
                new Achievement("Wat Pa Sawan Bun","Saraburi")
        };
        return list;
    }
}
