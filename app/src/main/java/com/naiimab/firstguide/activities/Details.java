package com.naiimab.firstguide.activities;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.ironsource.mediationsdk.IronSource;
import com.naiimab.firstguide.Ads.AdmobAds;
import com.naiimab.firstguide.Ads.MaxAds;
import com.naiimab.firstguide.Models.ModelItems;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;
import com.naiimab.firstguide.helper.CustomUtils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Details extends AppCompatActivity {

    RelativeLayout detailsRelative;
    TextView titleTV, textTV;
    ImageView imageIV;
    Button linkBTN;
    int position = 0;

    LinearLayout nativeLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);

        detailsRelative = findViewById(R.id.detailsRelative);
        titleTV = findViewById(R.id.titleTV);
        textTV = findViewById(R.id.textTV);
        imageIV = findViewById(R.id.imageIV);
        linkBTN = findViewById(R.id.linkBTN);
        nativeLayout = findViewById(R.id.nativeLayout);


        position = getIntent().getIntExtra("position", 0);

        AdmobAds admobAds = new AdmobAds(this);
        MaxAds maxAds = new MaxAds(this);

        if(MyApp.NativeAd.equalsIgnoreCase("admob")) {
            admobAds.showNative(nativeLayout);
        }
        else if(MyApp.NativeAd.equalsIgnoreCase("max")) {
            maxAds.showNative(nativeLayout);
        }
        getData();
    }


    @SuppressLint("StaticFieldLeak")
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

                            List<ModelItems> itemsModelList = new ArrayList<>();
                            for(int i = 0; i < jsonArray.length(); i++) {
                                JSONObject itemObj = jsonArray.getJSONObject(i);

                                String title = itemObj.getString(CustomUtils.JSON_TITLE);
                                String image = itemObj.getString(CustomUtils.JSON_IMAGE);
                                String color = itemObj.getString(CustomUtils.JSON_COLOR);
                                String text_size = itemObj.getString(CustomUtils.JSON_TEXT_SIZE);
                                boolean is_link = itemObj.getBoolean(CustomUtils.JSON_IS_LINK);
                                String link_title = itemObj.getString(CustomUtils.JSON_LINK_TITLE);
                                String set_link = itemObj.getString(CustomUtils.JSON_SET_LINK);
                                String image_link = itemObj.getString(CustomUtils.JSON_IMAGE_LINK);
                                String text = itemObj.getString(CustomUtils.JSON_TEXT);
                                boolean is_native = itemObj.getBoolean(CustomUtils.JSON_IS_NATIVE);
                                String background = itemObj.getString(CustomUtils.JSON_BACKGROUND);

                                ModelItems modelItems = new ModelItems(i, title, image, color, text_size, is_link, link_title, set_link, image_link, text, is_native, background);
                                itemsModelList.add(modelItems);
                            }

                            final ModelItems modelItems1 = itemsModelList.get(position);

                            titleTV.setText(modelItems1.getTitle());

                            Glide.with(Details.this).load(modelItems1.getImage()).error(R.mipmap.ic_launcher).into(imageIV);

                            imageIV.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    if(!modelItems1.getImage_link().isEmpty()) {
                                        String url = modelItems1.getImage_link();
                                        Intent i = new Intent(Intent.ACTION_VIEW);
                                        i.setData(Uri.parse(url));
                                        startActivity(i);
                                    }
                                }
                            });

                            textTV.setText(modelItems1.getText());

                            if(!modelItems1.getColor().isEmpty()) {
                                titleTV.setTextColor(Color.parseColor(modelItems1.getColor()));
                                textTV.setTextColor(Color.parseColor(modelItems1.getColor()));
                            }

                            if(!modelItems1.getText_size().isEmpty()) {
                                textTV.setTextSize(Integer.parseInt(modelItems1.getText_size()));
                            }

                            if(modelItems1.getIsLink()) {
                                linkBTN.setVisibility(View.VISIBLE);
                                if(!modelItems1.getLink_title().isEmpty()) {
                                    linkBTN.setText(modelItems1.getLink_title());
                                }

                                linkBTN.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        if(!modelItems1.getSetLink().isEmpty()) {
                                            String url = modelItems1.getSetLink();
                                            Intent i = new Intent(Intent.ACTION_VIEW);
                                            i.setData(Uri.parse(url));
                                            startActivity(i);
                                        }
                                    }
                                });
                            }
                            else {
                                linkBTN.setVisibility(View.GONE);
                            }

                            if(!modelItems1.getBackground().isEmpty()) {
                                detailsRelative.setBackgroundColor(Color.parseColor(modelItems1.getBackground()));
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

    protected void onResume() {
        super.onResume();
        IronSource.onResume(this);
    }
    protected void onPause() {
        super.onPause();
        IronSource.onPause(this);
    }
}