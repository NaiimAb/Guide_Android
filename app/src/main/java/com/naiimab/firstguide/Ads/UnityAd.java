package com.naiimab.firstguide.Ads;

import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.naiimab.firstguide.MyApp;
import com.unity3d.ads.IUnityAdsLoadListener;
import com.unity3d.ads.IUnityAdsShowListener;
import com.unity3d.ads.UnityAds;
import com.unity3d.services.banners.BannerErrorInfo;
import com.unity3d.services.banners.BannerView;
import com.unity3d.services.banners.UnityBannerSize;

public class UnityAd {

    public interface AdFinished {
        void onAdFinished();
    }

    private final AppCompatActivity mActivity;

    public UnityAd(AppCompatActivity activity) {
        this.mActivity = activity;
    }

    public void showBanner(RelativeLayout adContainer) {
        BannerView bannerView = new BannerView(mActivity, MyApp.unity_banner, new UnityBannerSize(320, 50));
        bannerView.setListener(new BannerView.Listener() {
            @Override
            public void onBannerLoaded(BannerView bannerAdView) {
                adContainer.addView(bannerAdView);
            }

            @Override
            public void onBannerFailedToLoad(BannerView bannerAdView, BannerErrorInfo errorInfo) {
                super.onBannerFailedToLoad(bannerAdView, errorInfo);
            }

            @Override
            public void onBannerClick(BannerView bannerAdView) {
                super.onBannerClick(bannerAdView);
            }

            @Override
            public void onBannerLeftApplication(BannerView bannerAdView) {
                super.onBannerLeftApplication(bannerAdView);
            }
        });
        bannerView.load();
    }

    public void showInterstitial(AdFinished adFinished) {
        IUnityAdsLoadListener adsLoadListener = new IUnityAdsLoadListener() {
            @Override
            public void onUnityAdsAdLoaded(String placementId) {
                UnityAds.show(mActivity, MyApp.unity_interstitial, new IUnityAdsShowListener() {
                    @Override
                    public void onUnityAdsShowFailure(String placementId, UnityAds.UnityAdsShowError error, String message) {
                        adFinished.onAdFinished();
                    }

                    @Override
                    public void onUnityAdsShowStart(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowClick(String placementId) {

                    }

                    @Override
                    public void onUnityAdsShowComplete(String placementId, UnityAds.UnityAdsShowCompletionState state) {
                        adFinished.onAdFinished();
                    }
                } );
            }

            @Override
            public void onUnityAdsFailedToLoad(String placementId, UnityAds.UnityAdsLoadError error, String message) {
                adFinished.onAdFinished();
            }
        };


        UnityAds.load(MyApp.unity_interstitial, adsLoadListener);
    }
}
