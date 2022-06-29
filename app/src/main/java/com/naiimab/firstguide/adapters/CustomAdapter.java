package com.naiimab.firstguide.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.nativead.NativeAd;
import com.google.android.gms.ads.nativead.NativeAdView;
import com.naiimab.firstguide.activities.Details;
import com.naiimab.firstguide.holders.GuideHolder;
import com.naiimab.firstguide.Models.ModelItems;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.helper.UnifiedNativeAdViewHolder;


import java.util.List;

public class CustomAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private static final int View_List = 0;
    private static final int View_NativeAd = 1;

    List<Object> modelItemsList;
    Context context;
    GuideHolder guideHolder;

    public CustomAdapter(Context context, List<Object> modelItems) {
        this.context = context;
        this.modelItemsList = modelItems;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        switch (viewType) {
            case View_NativeAd:
                return new UnifiedNativeAdViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.items_ad_unified, parent,false));
            case View_List:
                // Fall through.
            default:
                return new GuideHolder(LayoutInflater.from(parent.getContext()), parent);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        final int viewType = getItemViewType(position);
        switch (viewType) {
            case View_NativeAd:
                NativeAd nativeAd = (NativeAd) modelItemsList.get(position);
                populateNativeAdView(nativeAd, ((UnifiedNativeAdViewHolder) holder).getAdView());
                break;
            case View_List:
            default:
                onBind(holder, position);

        }
    }

    private void onBind(@NonNull RecyclerView.ViewHolder holder, final int position) {
        guideHolder = (GuideHolder)holder;
        final ModelItems modelItem = (ModelItems) modelItemsList.get(position);
        guideHolder.titleView.setText(modelItem.getTitle());

        Glide.with(context).load(modelItem.getImage()).into(guideHolder.imageView);

        guideHolder.guideItem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent detailsI = new Intent(context, Details.class);
                detailsI.putExtra("position", modelItem.getPosition());
                context.startActivity(detailsI);
            }
        });

    }

    @Override
    public int getItemViewType(int position) {
        Object recyclerViewItem = modelItemsList.get(position);
        if (recyclerViewItem instanceof NativeAd) {
            return View_NativeAd;
        }
        return View_List;
    }

    @Override
    public int getItemCount() {
        return modelItemsList.size();
    }

    private void populateNativeAdView(NativeAd nativeAd, NativeAdView adView) {

        ((TextView) adView.getHeadlineView()).setText(nativeAd.getHeadline());
        ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        NativeAd.Image icon = nativeAd.getIcon();
        if (icon == null) {
            adView.getIconView().setVisibility(View.INVISIBLE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(icon.getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }

        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());
        }

        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView())
                    .setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }

        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);
        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }

        adView.setNativeAd(nativeAd);
    }
}
