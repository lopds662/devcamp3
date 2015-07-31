package com.example.androidbp.activity;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.androidbp.R;
import com.example.androidbp.api.model.ArchivementFeedItem;

import java.util.List;

/**
 * Created by HackercleverPalm on 31/7/2558.
 */
public class CustomArrayAdapter extends RecyclerView.Adapter<AchievementViewHolder> {

    private Context context;
    private List<ArchivementFeedItem> list;

    public void setData(List<ArchivementFeedItem> list) {
        this.list = list;
    }

    @Override
    public AchievementViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.achievement_cardlayout, parent, false);
        AchievementViewHolder ac = new AchievementViewHolder(v);
        return ac;
    }

    @Override
    public void onBindViewHolder(AchievementViewHolder holder, int position) {
        holder.bindData(list.get(position));
    }

    @Override
    public int getItemCount() {
        if (list == null) return 0;
        return list.size();
    }
}
