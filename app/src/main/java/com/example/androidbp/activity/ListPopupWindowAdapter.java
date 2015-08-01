package com.example.androidbp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

import com.example.androidbp.R;

/**
 * Created by HWANG's on 8/1/2015.
 */
public class ListPopupWindowAdapter extends BaseAdapter {

    private Context context;
    private ArrayList<DataItem> dataItem;

    public ListPopupWindowAdapter(Context context, ArrayList<DataItem> dataItem)
    {
        this.context = context;
        this.dataItem = dataItem;
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        ImageView profilePic;
        TextView name;
        TextView userName;
        boolean isWithPicture = (dataItem.get(position).pic != 0);

        // Small List View , no need to recycle views
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        // Is this the row with the p.picture
        if(isWithPicture)
        {
            //Layout for the top row with profile picture /Avatar
            convertView = inflater.inflate(R.layout.toolbar_overflow_row_item, parent, false);

            profilePic = (ImageView) convertView .findViewById(R.id.imageProfilePic);
            profilePic.setImageResource(dataItem.get(position).pic);

            userName = (TextView) convertView .findViewById(R.id.textUsername);
            userName.setText(dataItem.get(position).userName);
        }
        else
        {
            //Layout for the other layout without an images
            convertView = inflater.inflate(R.layout.toolbar_overflow_row_item_text, parent, false);
        }


        name = (TextView) convertView .findViewById(R.id.textViewName);
        name.setText(dataItem.get(position).name);


        return convertView ;
    }


    // ----------------------------------------
    //  Implemented
    // ----------------------------------------
    @Override
    public Object getItem(int index)
    {
        return dataItem.get(index);
    }

    @Override
    public long getItemId(int position)
    {
        return position;
    }

    @Override
    public int getCount()
    {
        return dataItem.size();
    }

}
