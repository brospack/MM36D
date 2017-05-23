package com.aidchow.mm36d.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Environment;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.View;
import android.view.ViewPropertyAnimator;
import android.widget.EditText;

import com.aidchow.mm36d.App;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * Created by AidChow on 2016/10/17.
 */

public class Utils {
    private final static int DEFAULT_DELAY = 0;

    public static boolean isOnline() {
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();
        return (networkInfo != null && networkInfo.isConnected());
    }

    public static boolean isWifiConnect() {
        ConnectivityManager cm = (ConnectivityManager) App.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        return (info != null && info.isConnected());
    }

    public static String editextUtiils(EditText editText) {
        String content = editText.getText().toString().trim();
        if (content.isEmpty()) {
            return "";
        } else {
            return content;
        }
    }

    public static float dp2px(Context context, float dp) {
        return dp * context.getResources().getDisplayMetrics().density;
    }

    /**
     * 像素转dp
     *
     * @param context
     * @param px
     * @return
     */
    public static int px2Dp(Context context, int px) {
        DisplayMetrics displayMetrics = context.getResources().getDisplayMetrics();
        int dp = Math.round(px / (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT));
        return dp;
    }


    public static String getNowDate() {
        @SuppressLint("SimpleDateFormat") SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("时间格式化：" + sdf.format(System.currentTimeMillis()));
        return sdf.format(System.currentTimeMillis());
    }

    public static ViewPropertyAnimator showViewByScale(View v) {
        return v.animate().setStartDelay(DEFAULT_DELAY)
                .scaleX(1).scaleY(1);
    }

    public static ViewPropertyAnimator hideViewByScaleXY(View v) {
        return hideViewByScale(v, DEFAULT_DELAY, 0, 0);
    }

    public static ViewPropertyAnimator hideViewByScaleX(View v) {
        return hideViewByScale(v, DEFAULT_DELAY, 0, 1);
    }

    public static ViewPropertyAnimator hideViewByScaleY(View v) {
        return hideViewByScale(v, DEFAULT_DELAY, 1, 0);
    }

    private static ViewPropertyAnimator hideViewByScale(View v, int delay, int x, int y) {
        return v.animate().setStartDelay(delay)
                .scaleX(x).scaleY(y);
    }

    public static File getAlbumStorageDir(String albuName) {
        File file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), albuName);
        if (!file.exists()) {
            file.mkdirs();
            Logger.i("Taste Flower", "directory not create");
        }
        return file;
    }

    private Utils() {
        throw new AssertionError("No Instances");
    }

}
