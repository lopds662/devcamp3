package com.example.androidbp.activity;

import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.androidbp.R;
import com.example.androidbp.api.model.ArchivementFeedItem;
import com.squareup.picasso.Picasso;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class AchievementViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.archeivement_title)
    public TextView title;

    @Bind(R.id.achievementLocation)
    public TextView location;

    @Bind(R.id.viewImage)
    public ImageView image;
    private ArchivementFeedItem model;

    public AchievementViewHolder(final View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(itemView.getContext(), "555",Toast.LENGTH_SHORT).show();

                Intent intent = new Intent(v.getContext(), AchievementPage.class);
                intent.putExtra("id", model.id);
                v.getContext().startActivity(intent);
            }
        });
    }

    public void bindData(ArchivementFeedItem item) {
        model = item;
        title.setText(item.title);
        location.setText("lat: "+item.latitude+" lng: "+item.longitude);
        Log.d("CCC", "Loading image url:" + item.image_url);
        Picasso.with(this.itemView.getContext()).load(item.image_url).placeholder(R.drawable.ic_media_play).into(image);
    }
    public View getItemView(){
        return this.itemView;
    }



}
