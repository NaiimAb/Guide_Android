package com.naiimab.firstguide.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ironsource.mediationsdk.IronSource;
import com.naiimab.firstguide.Ads.AdmobAds;
import com.naiimab.firstguide.Ads.CPAAds;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.helper.CustomUtils;

public class SecActivity extends AppCompatActivity {

    TextView rateBtn, shareBtn, moreBtn, Startbtn;
    AdmobAds admobAds;
    CPAAds cpaAds;
    RelativeLayout adLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sec);

        Startbtn = findViewById(R.id.Startbtn);
        rateBtn = findViewById(R.id.rateBtn);
        shareBtn = findViewById(R.id.shareBtn);
        moreBtn = findViewById(R.id.moreBtn);

        adLayout = findViewById(R.id.adLayout);


        admobAds = new AdmobAds(this);

        cpaAds = new CPAAds(this);

        admobAds.loadInter();

        if(MyApp.ImageBanner) {
            cpaAds.showBanner(adLayout, CustomUtils.getScreenSize(SecActivity.this, true), CustomUtils.getScreenSize(SecActivity.this, true)/2);
        }
        else {
            if (MyApp.BannerAd.equalsIgnoreCase("admob")) {
                admobAds.showBanner(adLayout);
            }
            else {

            }
        }

        Startbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(MyApp.InterstitialAd.equalsIgnoreCase("admob")) {

                    admobAds.showInter(new AdmobAds.AdFinished() {
                        @Override
                        public void onAdFinished() {
                            Intent goIntent = new Intent(SecActivity.this, ThirdActivity.class);
                            startActivity(goIntent);
                        }
                    });

                }
                else {
                    Intent goIntent = new Intent(SecActivity.this, ThirdActivity.class);
                    startActivity(goIntent);
                }


            }
        });

        rateBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                try {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + getPackageName())));
                } catch (android.content.ActivityNotFoundException anfe) {
                    startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                }
            }
        });

        shareBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share via"));
            }
        });

        moreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/dev?id=6456804357929516378")));
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