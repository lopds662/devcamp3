package com.example.androidbp.activity;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidbp.R;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class CustomArrayAdapter extends ArrayAdapter<Achievement> {

    private final Context context;
    private final Achievement[] values;

    public CustomArrayAdapter(Context context, int resource, Achievement[] objects) {
        super(context, resource, objects);
        this.context = context;
        this.values = objects;
    }

//    public CustomArrayAdapter(Context context, String[] values) {
//        super(context, -1, values);
//        this.context = context;
//        this.values = values;
//    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View rowView = inflater.inflate(R.layout.achievement_view, parent, false);
        TextView achievement = (TextView) rowView.findViewById(R.id.nameAchievement);
        TextView location = (TextView) rowView.findViewById(R.id.achievementLocation);
        ImageView imageView = (ImageView) rowView.findViewById(R.id.icon);
        achievement.setText(values[position].getName());
        location.setText(values[position].getName());
//        imageView.setImageResource(R.drawable.ic_add_white_24dp);
        imageView.setImageResource(values[position].getRes());

        return rowView;
    }
}
