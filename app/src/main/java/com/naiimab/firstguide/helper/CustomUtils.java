package com.naiimab.firstguide.helper;

import android.app.Activity;
import android.content.Context;
import android.graphics.Point;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.view.WindowManager;

public class CustomUtils {

    public static final int TIME = 500; // 5000ms = 5s

    public static final String JSON_LINK = "http://192.168.0.213/tutorials/json/guide.json";
    public static final String JSON_GUIDE_DATA = "GuideData";
    public static final String JSON_ADS = "AdsController";
    public static final String JSON_ITEMS = "items";
    public static final String JSON_CONTENT = "content";

    public static final String JSON_TITLE  = "title";
    public static final String JSON_IMAGE  = "image";
    public static final String JSON_COLOR  = "color";
    public static final String JSON_TEXT_SIZE  = "text_size";
    public static final String JSON_IS_LINK  = "isLink";
    public static final String JSON_LINK_TITLE  = "link_title";
    public static final String JSON_SET_LINK  = "setLink";
    public static final String JSON_IMAGE_LINK  = "image_link";
    public static final String JSON_TEXT  = "text";
    public static final String JSON_IS_NATIVE  = "isNative";
    public static final String JSON_BACKGROUND  = "background";



    public static boolean isNetworkAvailable(Context context) {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }


    public static int getScreenSize(Activity context, boolean isWidth) {
        Point size = new Point();
        WindowManager w = context.getWindowManager();

        w.getDefaultDisplay().getSize(size);
        if(isWidth) {
            return size.x;
        }
        else {
            return size.y;
        }
    }

}
