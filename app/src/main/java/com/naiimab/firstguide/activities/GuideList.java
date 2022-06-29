package com.naiimab.firstguide.activities;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.applovin.mediation.MaxAd;
import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacer;
import com.applovin.mediation.nativeAds.adPlacer.MaxAdPlacerSettings;
import com.applovin.mediation.nativeAds.adPlacer.MaxRecyclerAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.LoadAdError;
import com.google.android.gms.ads.nativead.NativeAd;
import com.ironsource.mediationsdk.IronSource;
import com.naiimab.firstguide.BuildConfig;
import com.naiimab.firstguide.Models.ModelItems;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.adapters.CustomAdapter;
import com.naiimab.firstguide.helper.CustomUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GuideList extends AppCompatActivity {

    RecyclerView itemList;
    CustomAdapter customAdapter;

    List<Object> itemsModelList = new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide_list);

        itemList = findViewById(R.id.itemList);
        itemList.setHasFixedSize(true);
        itemList.setLayoutManager(new LinearLayoutManager(this));

        getData();
    }

    private void getData() {
        RequestQueue requestQueue = Volley.newRequestQueue(this);
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, CustomUtils.JSON_LINK, null,
                new Response.Listener() {
                    @Override
                    public void onResponse(Object response)
                    {
                        try {
                            JSONObject responseJson = (JSONObject) response;
                            JSONObject GuideData = responseJson.getJSONObject("GuideData");
                            JSONArray jsonArray = GuideData.getJSONArray("items");

                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject itemObj = jsonArray.getJSONObject(i);

                                String title = itemObj.getString(CustomUtils.JSON_TITLE);
                                String image = itemObj.getString(CustomUtils.JSON_IMAGE);

                                ModelItems modelItems = new ModelItems(i, title, image);
                                itemsModelList.add(modelItems);
                            }

                            if (MyApp.NativeAd.equalsIgnoreCase("admob")) {
                                loadNatAdmob();
                            }
                            else if (MyApp.NativeAd.equalsIgnoreCase("max")) {
                                getMaxAdapter();
                            }
                            else {
                                getAdapterData();
                            }

                        }
                        catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error)
                    {

                    }
                });
        jsonObjectRequest.setShouldCache(false);
        requestQueue.add(jsonObjectRequest);
    }

    private void getMaxAdapter() {
        MaxAdPlacerSettings settings = new MaxAdPlacerSettings( MyApp.maxNative );
        settings.addFixedPosition(1);
        settings.setRepeatingInterval(2);

        customAdapter = new CustomAdapter(GuideList.this, itemsModelList);

        MaxRecyclerAdapter adAdapter = new MaxRecyclerAdapter( settings, customAdapter, this );

        itemList.setAdapter(adAdapter);
        adAdapter.loadAds();
        adAdapter.getAdPlacer().setAdSize(360, 300);
    }

    private List<NativeAd> mNativeAds = new ArrayList<>();
    private AdLoader adLoader;
    private void loadNatAdmob() {

        mNativeAds.clear();
        getAdapterData();
        AdLoader.Builder builder = new AdLoader.Builder(getApplicationContext(), MyApp.NativeAdmob);
        adLoader = builder.forNativeAd(new NativeAd.OnNativeAdLoadedListener() {
            @Override
            public void onNativeAdLoaded(NativeAd NativeAd) {
                mNativeAds.add(NativeAd);
                if (!adLoader.isLoading()) {
                    insertAdsInMenuItems();
                }
            }
        }).withAdListener(
                new AdListener() {
                    @Override
                    public void onAdFailedToLoad(LoadAdError adError) {
                        if (!adLoader.isLoading()) {
                            insertAdsInMenuItems();
                        }
                    }
                }).build();

        adLoader.loadAds(new AdRequest.Builder().build(), itemsModelList.size());
    }

    private void insertAdsInMenuItems() {
        if (mNativeAds.size() <= 0) {
            return;
        }

        try {
            int offset = (itemsModelList.size() / mNativeAds.size()) + 1;
            int index = 1;
            for (NativeAd  ad : mNativeAds) {
                itemsModelList.add(index, ad);
                index = index + offset;
            }
            customAdapter.notifyDataSetChanged();

        } catch (Exception e) {
            if (BuildConfig.DEBUG) {
                Log.d("motya", "Native not replace correct : " + e);
            }
        }
    }


    private void getAdapterData() {
        customAdapter = new CustomAdapter(GuideList.this, itemsModelList);
        itemList.setAdapter(customAdapter);
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