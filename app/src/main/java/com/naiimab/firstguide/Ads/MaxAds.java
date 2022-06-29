package com.naiimab.firstguide.Ads;

import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.applovin.mediation.MaxAd;
import com.applovin.mediation.MaxAdListener;
import com.applovin.mediation.MaxAdViewAdListener;
import com.applovin.mediation.MaxError;
import com.applovin.mediation.ads.MaxAdView;
import com.applovin.mediation.ads.MaxInterstitialAd;
import com.applovin.mediation.nativeAds.MaxNativeAdListener;
import com.applovin.mediation.nativeAds.MaxNativeAdLoader;
import com.applovin.mediation.nativeAds.MaxNativeAdView;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;

public class MaxAds {

    public interface AdFinished {
        void onAdFinished();
    }

    private AppCompatActivity activity;

    public MaxAds(AppCompatActivity activity) {
        this.activity = activity;
    }

    public void showBanner(RelativeLayout adContainer) {
        MaxAdView adView = new MaxAdView(MyApp.maxBanner, activity );
        adView.setListener(new MaxAdViewAdListener() {
            @Override
            public void onAdExpanded(MaxAd ad) {

            }

            @Override
            public void onAdCollapsed(MaxAd ad) {

            }

            @Override
            public void onAdLoaded(MaxAd ad) {
                // Stretch to the width of the screen for banners to be fully functional
                int width = ViewGroup.LayoutParams.MATCH_PARENT;
                // Banner height on phones and tablets is 50 and 90, respectively
                int heightPx = activity.getResources().getDimensionPixelSize( R.dimen.banner_height );
                adView.setLayoutParams( new RelativeLayout.LayoutParams( width, heightPx ) );
                adView.setBackgroundColor(ContextCompat.getColor(activity, R.color.white));
                adContainer.addView( adView );

            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {

            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {

            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {

            }
        });
        // Load the ad
        adView.loadAd();
    }

    public void showInter(final AdFinished adFinished) {
        MaxInterstitialAd interstitialAd = new MaxInterstitialAd( MyApp.maxInterstitial, activity );
        interstitialAd.setListener(new MaxAdListener() {
            @Override
            public void onAdLoaded(MaxAd ad) {
                interstitialAd.showAd();
            }

            @Override
            public void onAdDisplayed(MaxAd ad) {

            }

            @Override
            public void onAdHidden(MaxAd ad) {
                adFinished.onAdFinished();
            }

            @Override
            public void onAdClicked(MaxAd ad) {

            }

            @Override
            public void onAdLoadFailed(String adUnitId, MaxError error) {
                adFinished.onAdFinished();
            }

            @Override
            public void onAdDisplayFailed(MaxAd ad, MaxError error) {
                adFinished.onAdFinished();
            }
        });

        // Load the first ad
        interstitialAd.loadAd();
    }

    public void showNative(LinearLayout linearLayout) {
        MaxNativeAdLoader maxNativeAdLoader = new MaxNativeAdLoader(MyApp.maxNative, activity);
        maxNativeAdLoader.setNativeAdListener(new MaxNativeAdListener() {
            @Override
            public void onNativeAdLoaded(@Nullable MaxNativeAdView maxNativeAdView, MaxAd maxAd) {
                linearLayout.removeAllViews();
                linearLayout.addView(maxNativeAdView);
            }

            @Override
            public void onNativeAdLoadFailed(String s, MaxError maxError) {
                linearLayout.setVisibility(View.GONE);
            }

            @Override
            public void onNativeAdClicked(MaxAd maxAd) {
                super.onNativeAdClicked(maxAd);
            }
        });
        maxNativeAdLoader.loadAd();
    }
}
