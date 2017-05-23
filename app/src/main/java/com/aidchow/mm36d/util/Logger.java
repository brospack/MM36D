package com.aidchow.mm36d.util;

import android.util.Log;

/**
 * Created by 78537 on 2017/2/16.
 */

public class Logger {
    private static final int VERBOSE = 1;
    private static final int DEBUG = 6;
    private static final int INFO = 2;
    private static final int WARM = 3;
    private static final int ERROR = 4;
    private static final int ASSERT = 5;
    private static final int LEVEL = VERBOSE;


    public static void d(String tag, String msg) {
        if (LEVEL <= DEBUG) {
            Log.d(tag, msg);
        }
    }

    public void e(String tag, String msg) {
        if (LEVEL <= ERROR) {
            Log.e(tag, msg);
        }
    }

    public void v(String tag, String msg) {
        if (LEVEL <= VERBOSE) {
            Log.v(tag, msg);
        }
    }

    public void w(String tag, String msg) {
        if (LEVEL <= WARM) {
            Log.w(tag, msg);
        }
    }

    public void a(String tag, String msg) {
        if (LEVEL <= ASSERT) {
            Log.d(tag, msg);
        }
    }

    public static void i(String tag, String msg) {
        if (LEVEL <= INFO) {
            Log.i(tag, msg);
        }
    }
}
