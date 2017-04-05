package com.aidchow.mm36d;

import android.annotation.SuppressLint;
import android.app.Application;
import android.content.Context;

import com.aidchow.mm36d.networdk.MM36DOkHttpClient;
import com.aidchow.mm3d.MM36D;

/**
 * Created by AidChow on 2017/4/3.
 */

public class App extends Application {
    @SuppressLint("StaticFieldLeak")
    private static Context mContext;

    private static MM36D mm36D;

    @Override
    public void onCreate() {
        super.onCreate();
        mContext = getApplicationContext();
        mm36D = MM36D.create(MM36DOkHttpClient.getOkHttpClient());
    }

    public static Context getContext() {
        return mContext;
    }

    public static MM36D getMm36D() {
        return mm36D;
    }
}
