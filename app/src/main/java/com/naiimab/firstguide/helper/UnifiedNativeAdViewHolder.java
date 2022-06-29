package com.naiimab.firstguide.helper;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.gms.ads.nativead.NativeAdView;
import com.naiimab.firstguide.R;

public class UnifiedNativeAdViewHolder extends RecyclerView.ViewHolder {

    private NativeAdView adView;
    public NativeAdView getAdView() {
        return adView;
    }

    public UnifiedNativeAdViewHolder(@NonNull View itemView) {
        super(itemView);

        adView = itemView.findViewById(R.id.ad_view);

        adView.setMediaView(adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
    }
}
