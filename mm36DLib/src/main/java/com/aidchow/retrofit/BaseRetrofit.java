package com.aidchow.retrofit;

import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;

/**
 * Created by AicChow on 2017/3/30.
 */
public class BaseRetrofit {


    private Retrofit retrofit = null;
    private OkHttpClient okHttpClient = null;


    private static OkHttpClient getOkHttpClient() {
        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.readTimeout(10, TimeUnit.SECONDS)
                .writeTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();
        return builder.build();
    }


    public BaseRetrofit(OkHttpClient okHttpClient) {
        this.okHttpClient = okHttpClient;
    }

    public BaseRetrofit() {
        this(getOkHttpClient());
    }

    public Retrofit retrofit() {
        if (retrofit == null) {
            Retrofit.Builder builder = new Retrofit.Builder();
            retrofit = builder.baseUrl(BaseApi.BASE_API)
                    .client(okHttpClient)
                    .addConverterFactory(StringConverterFactory.create())
                    .build();
        }
        return retrofit;
    }
}
