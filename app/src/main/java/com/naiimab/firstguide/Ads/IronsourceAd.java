package com.naiimab.firstguide.Ads;

import android.view.View;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.ironsource.mediationsdk.ISBannerSize;
import com.ironsource.mediationsdk.IronSource;
import com.ironsource.mediationsdk.IronSourceBannerLayout;
import com.ironsource.mediationsdk.logger.IronSourceError;
import com.ironsource.mediationsdk.sdk.BannerListener;
import com.ironsource.mediationsdk.sdk.InterstitialListener;
import com.ironsource.mediationsdk.sdk.l;

public class IronsourceAd {

    public interface AdFinished {
        void onAdFinished();
    }

    private final AppCompatActivity mActivity;

    public IronsourceAd(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    public void showBanner(RelativeLayout adContainer) {
        IronSourceBannerLayout banner = IronSource.createBanner(mActivity, ISBannerSize.SMART);

        banner.setBannerListener(new BannerListener() {
            @Override
            public void onBannerAdLoaded() {
                adContainer.removeAllViews();
                adContainer.addView(banner);
            }

            @Override
            public void onBannerAdLoadFailed(IronSourceError ironSourceError) {
                adContainer.setVisibility(View.GONE);
            }

            @Override
            public void onBannerAdClicked() {

            }

            @Override
            public void onBannerAdScreenPresented() {

            }

            @Override
            public void onBannerAdScreenDismissed() {

            }

            @Override
            public void onBannerAdLeftApplication() {

            }
        });
        IronSource.loadBanner(banner);
    }

    public void showInterstitial(AdFinished adFinished) {
        IronSource.setInterstitialListener(new InterstitialListener() {
            @Override
            public void onInterstitialAdReady() {
                IronSource.showInterstitial();
            }

            @Override
            public void onInterstitialAdLoadFailed(IronSourceError ironSourceError) {
                adFinished.onAdFinished();
            }

            @Override
            public void onInterstitialAdOpened() {

            }

            @Override
            public void onInterstitialAdClosed() {
                adFinished.onAdFinished();
            }

            @Override
            public void onInterstitialAdShowSucceeded() {

            }

            @Override
            public void onInterstitialAdShowFailed(IronSourceError ironSourceError) {
                adFinished.onAdFinished();
            }

            @Override
            public void onInterstitialAdClicked() {

            }
        });

        IronSource.loadInterstitial();
    }
}
