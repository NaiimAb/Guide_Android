package com.naiimab.firstguide.activities;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.ironsource.mediationsdk.IronSource;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.helper.CustomUtils;
import com.unity3d.ads.UnityAds;

public class SplashScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_spalsh);

        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if(MyApp.isJsonDone == 0) {
                    handler.postDelayed(this, CustomUtils.TIME);
                }
                else if(MyApp.isJsonDone == 1) {
                    IronSource.init(SplashScreen.this, MyApp.ironsource_appKey, IronSource.AD_UNIT.INTERSTITIAL, IronSource.AD_UNIT.BANNER);
                    UnityAds.initialize(SplashScreen.this, MyApp.unity_game_id, MyApp.unity_test);
                    Intent mainIntent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(mainIntent);
                    finish();
                }
                else if(MyApp.isJsonDone == 2) {
                    Toast.makeText(SplashScreen.this, "Error. Please try again later", Toast.LENGTH_SHORT).show();
                    finish();
                }
            }
        }, CustomUtils.TIME);

    }
}