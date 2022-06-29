package com.naiimab.firstguide.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.applovin.mediation.MaxAd;
import com.ironsource.mediationsdk.IronSource;
import com.naiimab.firstguide.Ads.AdmobAds;
import com.naiimab.firstguide.Ads.CPAAds;
import com.naiimab.firstguide.Ads.IronsourceAd;
import com.naiimab.firstguide.Ads.MaxAds;
import com.naiimab.firstguide.Ads.UnityAd;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.helper.CustomUtils;

public class MainActivity extends AppCompatActivity {

    Button letStart;
    AdmobAds admobAds;
    CPAAds cpaAds;
    MaxAds maxAds;
    UnityAd unityAd;
    IronsourceAd ironsourceAd;
    RelativeLayout adLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        letStart = findViewById(R.id.letStart);
        adLayout = findViewById(R.id.adLayout);


        admobAds = new AdmobAds(this);
        cpaAds = new CPAAds(this);
        maxAds = new MaxAds(this);
        ironsourceAd = new IronsourceAd(this);
        unityAd = new UnityAd(this);

        admobAds.loadInter();

        if(MyApp.ImageBanner) {
            cpaAds.showBanner(adLayout, CustomUtils.getScreenSize(MainActivity.this, true), CustomUtils.getScreenSize(MainActivity.this, true)/2);
        }
        else {
            if (MyApp.BannerAd.equalsIgnoreCase("admob")) {
                admobAds.showBanner(adLayout);
            }
            else if (MyApp.BannerAd.equalsIgnoreCase("max")){
                maxAds.showBanner(adLayout);
            }
            else if(MyApp.BannerAd.equalsIgnoreCase("ironsource")) {
                ironsourceAd.showBanner(adLayout);
            }
            else if(MyApp.BannerAd.equalsIgnoreCase("unity")) {
                unityAd.showBanner(adLayout);
            }
        }
        letStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyApp.InterstitialAd.equalsIgnoreCase("admob")) {
                    admobAds.showInter(new AdmobAds.AdFinished() {
                        @Override
                        public void onAdFinished() {
                            Intent goIntent = new Intent(MainActivity.this, SecActivity.class);
                            startActivity(goIntent);
                        }
                    });
                }
                else if(MyApp.InterstitialAd.equalsIgnoreCase("max")){
                    maxAds.showInter(new MaxAds.AdFinished() {
                        @Override
                        public void onAdFinished() {
                            Intent goIntent = new Intent(MainActivity.this, SecActivity.class);
                            startActivity(goIntent);
                        }
                    });
                }
                else if(MyApp.InterstitialAd.equalsIgnoreCase("ironsource")){
                    ironsourceAd.showInterstitial(new IronsourceAd.AdFinished() {
                        @Override
                        public void onAdFinished() {
                            Intent goIntent = new Intent(MainActivity.this, SecActivity.class);
                            startActivity(goIntent);
                        }
                    });
                }

                else if(MyApp.InterstitialAd.equalsIgnoreCase("unity")){
                    unityAd.showInterstitial(new UnityAd.AdFinished() {
                        @Override
                        public void onAdFinished() {
                            Intent goIntent = new Intent(MainActivity.this, SecActivity.class);
                            startActivity(goIntent);
                        }
                    });
                }

            }
        });

    }

    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }
}