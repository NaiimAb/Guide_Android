package com.naiimab.firstguide.Ads;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.naiimab.firstguide.MyApp;
import com.naiimab.firstguide.R;


public class CPAAds {
    private Context mContext;

    public CPAAds(Context context) {
        this.mContext = context;
    }

    public void showBanner(RelativeLayout adContainer, int Width, int height) {
        ImageView imageView = new ImageView(mContext);
        adContainer.addView(imageView);
        Glide.with(mContext).load(MyApp.ImageBannerURL)
                .error(R.mipmap.ic_launcher)
                .override(Width, height)
                .into(imageView);

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(Intent.ACTION_VIEW);
                i.setData(Uri.parse(MyApp.ImageBannerURL));
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                mContext.startActivity(i);
            }
        });
    }

}
