package com.aidchow.mm36d.networdk;

import com.aidchow.mm36d.App;
import com.aidchow.mm36d.util.Logger;
import com.aidchow.mm36d.util.Utils;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by 78537 on 2017/4/3.
 */

public class MM36DOkHttpClient {

    private static final int cacheSize = 10 * 1024 * 1024;
    private static final Interceptor REWRITE_CACHE_CONTROL_INTERCEPTOR = new Interceptor() {
        @Override
        public Response intercept(Chain chain) throws IOException {
            Response response = chain.proceed(chain.request());
            if (Utils.isOnline()) {
                Logger.d("FUCK", " response has network");
                int maxAge = 0;
                return response.newBuilder()
                        .removeHeader("Cache-Control")
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, max-age=" + maxAge)
                        .build();
            } else {
                Logger.d("FUCK", " response not network");

                int maxAge = 60 * 60 * 24 * 7;
                return response.newBuilder()
                        .removeHeader("Cache-Control")
                        .removeHeader("Pragma")
                        .header("Cache-Control", "public, only-if-cached, max-stale=" + maxAge)
                        .build();
            }

        }
    };

    public static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        if (builder.interceptors() != null) {
            builder.interceptors().clear();
        }
        builder.addInterceptor(new Interceptor() {
            @Override
            public Response intercept(Chain chain) throws IOException {
                Request request = chain.request();
                if (!Utils.isOnline()) {
                    Logger.d("FUCK", "not request network");
                    return chain.proceed(request.newBuilder()
                            .cacheControl(CacheControl.FORCE_CACHE)
                            .build());
                } else {
                    Logger.d("FUCK", " request has network");
                    return chain.proceed(request
                            .newBuilder()
                            .build());
                }

            }
        }).addNetworkInterceptor(REWRITE_CACHE_CONTROL_INTERCEPTOR)
                .cache(new Cache(App.getContext().getExternalCacheDir(), cacheSize))
                .connectTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(20, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS);
        return builder.build();


    }

}
