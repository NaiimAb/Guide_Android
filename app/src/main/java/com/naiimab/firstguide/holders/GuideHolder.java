package com.naiimab.firstguide.holders;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.naiimab.firstguide.R;

public class GuideHolder extends RecyclerView.ViewHolder {

    public RelativeLayout guideItem;
    public ImageView imageView;
    public TextView titleView;

    public GuideHolder(LayoutInflater layoutInflater, ViewGroup viewGroup) {
        super(layoutInflater.inflate(R.layout.guide_item, viewGroup, false));
        guideItem = itemView.findViewById(R.id.guideItem);
        imageView = itemView.findViewById(R.id.image);
        titleView = itemView.findViewById(R.id.title);
    }
}
