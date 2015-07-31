package com.example.androidbp.activity;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.androidbp.R;
import com.example.androidbp.api.model.ArchivementFeedItem;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class AchievementViewHolder extends RecyclerView.ViewHolder {

    @Bind(R.id.archeivement_title)
    public TextView title;

    public AchievementViewHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void bindData(ArchivementFeedItem item) {
        title.setText(item.title);
    }

}
