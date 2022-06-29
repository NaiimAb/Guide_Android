package com.naiimab.firstguide;

import android.content.Context;

import androidx.multidex.MultiDex;
import androidx.multidex.MultiDexApplication;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.sdk.AppLovinSdk;
import com.applovin.sdk.AppLovinSdkConfiguration;
import com.google.android.gms.ads.MobileAds;
import com.naiimab.firstguide.helper.CustomUtils;

import org.json.JSONException;
import org.json.JSONObject;

public class MyApp extends MultiDexApplication {


    RequestQueue requestQueue;

    public static int isJsonDone = 0; // 0 not yet processed 1 json is done 2 error

    public static String BannerAd = "admob";
    public static String InterstitialAd = "admob";
    public static String NativeAd = "admob";

    public static String BannerAdmob = "ca-app-pub-3940256099942544/6300978111";
    public static String InterstitialAdmob = "ca-app-pub-3940256099942544/1033173712";
    public static String NativeAdmob = "ca-app-pub-3940256099942544/2247696110";

    public static String maxBanner = "";
    public static String maxInterstitial = "";
    public static String maxNative = "";

    public static String ironsource_appKey = "";

    public static String unity_game_id = "";
    public static boolean unity_test = false;
    public static String unity_banner = "";
    public static String unity_interstitial = "";

    public static boolean ImageBanner = false;
    public static String ImageBannerImg = "https://media3.giphy.com/media/igVvRDJ4EbhstQyEKh/giphy.gif";
    public static String ImageBannerURL = "https://www.google.com";

    @Override
    protected void attachBaseContext(Context base) {
        super.attachBaseContext(base);
        MultiDex.install(this);
    }

    @Override
    public void onCreate() {
        super.onCreate();

        requestQueue = Volley.newRequestQueue(this);

        AppLovinSdk.getInstance( this ).setMediationProvider("max");
        AppLovinSdk.initializeSdk( this);
        MobileAds.initialize(this);

        callAds();
    }

    private void callAds() {
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CustomUtils.JSON_LINK, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response)
                    {
                        try {
                            JSONObject GuideData = ((JSONObject) response).getJSONObject("GuideData");
                            JSONObject AdsController = GuideData.getJSONObject("AdsController");

                            BannerAd = AdsController.getString("BannerAd");
                            InterstitialAd = AdsController.getString("InterstitialAd");
                            NativeAd = AdsController.getString("NativeAd");
                            BannerAdmob = AdsController.getString("BannerAdmob");
                            InterstitialAdmob = AdsController.getString("InterstitialAdmob");
                            NativeAdmob = AdsController.getString("NativeAdmob");
                            maxBanner = AdsController.getString("maxBanner");
                            maxInterstitial = AdsController.getString("maxInterstitial");
                            maxNative = AdsController.getString("maxNative");
                            ironsource_appKey = AdsController.getString("ironsource_appKey");
                            unity_game_id = AdsController.getString("unity_game_id");
                            unity_test = AdsController.getBoolean("unity_test");
                            unity_banner = AdsController.getString("unity_banner");
                            unity_interstitial = AdsController.getString("unity_interstitial");
                            ImageBanner = AdsController.getBoolean("ImageBanner");
                            ImageBannerImg = AdsController.getString("ImageBannerImg");
                            ImageBannerURL = AdsController.getString("ImageBannerURL");

                            isJsonDone = 1;

                        } catch (JSONException e) {
                            isJsonDone = 2;
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {
                        isJsonDone = 2;
                    }
                });
        jsonObjectRequest.setShouldCache(false);
        requestQueue.add(jsonObjectRequest);
    }
}
