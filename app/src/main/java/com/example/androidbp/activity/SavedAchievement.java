package com.example.androidbp.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.Api;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.example.androidbp.manager.HttpManager;

import java.util.ArrayList;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class SavedAchievement extends ActionBarActivity {

    @Bind(R.id.list)
    public RecyclerView listView;

    public ListView listViewForSearch;

    private TextView etSearch;

    List<ArchivementFeedItem> listSavedfromServer;

    List<ArchivementFeedItem> newlistSavedfromServer;

    AchievementViewHolder[] listSavedAchievement;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_saved_achievement);

        // Get ListView object from xml
        ButterKnife.bind(this);


        // Defined Array listCompleted to show in card
        final CustomArrayAdapter achieAdapter = new CustomArrayAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(achieAdapter);
        String profileId = "0a776aa7-6073-4998-8802-b4ee79ff5d2a";

        HttpManager.ApiFor(Api.class).archievementSaved(profileId, new Callback<List<ArchivementFeedItem>>() {
            @Override
            public void success(List<ArchivementFeedItem> archivementFeedItems, Response response) {
                //make list for search engine
                listSavedfromServer = archivementFeedItems;

                //adaptor to show
                achieAdapter.setData(archivementFeedItems);
                achieAdapter.notifyDataSetChanged();
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });




//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listSavedfromServer);
//        // Assign adapter to ListView
//        listView.setAdapter(adapter);
//
//        // ListView Item Click Listener
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) listView.getItemAtPosition(position);
//
//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
//
//            }
//
//        });




        // Search Engine in
        // Add Text Change Listener to EditText
        etSearch = (EditText) findViewById(R.id.etSearch);
        etSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                newlistSavedfromServer = getNewListBySearch(etSearch.getText().toString());
                setNewListBySearched();
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_saved_achievement, menu);
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

    public ArrayList<ArchivementFeedItem> getNewListBySearch(String inputSearch) {
        ArrayList<ArchivementFeedItem> listedBySearch = new ArrayList<>();
        for (ArchivementFeedItem i : listSavedfromServer) {
            if ((i.getTitle().toLowerCase()).startsWith(inputSearch.toLowerCase())) {
                listedBySearch.add(i);
            }
        }

    return  listedBySearch;
    }

    protected void setNewListBySearched(){

        Toast.makeText(this, "ok.",Toast.LENGTH_SHORT).show();
        final CustomArrayAdapter achieAdapter = new CustomArrayAdapter();
        listView.setLayoutManager(new LinearLayoutManager(this));
        listView.setAdapter(achieAdapter);
        String profileId = "0a776aa7-6073-4998-8802-b4ee79ff5d2a";

        achieAdapter.setData(newlistSavedfromServer);
        achieAdapter.notifyDataSetChanged();


//        // Get ListView object from xml
//        listViewForSearch = (ListView) findViewById(R.id.list);
//
//        // Defined Array listCompleted to show in ListView
//        ArrayList<String> listBySearch = getNewListBySearch(etSearch.getText().toString());
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
//                android.R.layout.simple_list_item_1, android.R.id.text1, listBySearch);
//        // Assign adapter to ListView
//        listViewForSearch.setAdapter(adapter);
//
//        // ListView Item Click Listener
//        listViewForSearch.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view,
//                                    int position, long id) {
//
//                // ListView Clicked item index
//                int itemPosition     = position;
//
//                // ListView Clicked item value
//                String  itemValue    = (String) listViewForSearch.getItemAtPosition(position);
//
//                // Show Alert
//                Toast.makeText(getApplicationContext(),
//                        "Position :" + itemPosition + "  ListItem : " + itemValue, Toast.LENGTH_LONG)
//                        .show();
//
//            }
//
//        });
    }
    public AchievementViewHolder[] getSavedAchievement(){
        //code back-end
//        listSavedAchievement = input;
        return listSavedAchievement;
    }
}
