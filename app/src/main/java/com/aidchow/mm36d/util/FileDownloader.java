package com.aidchow.mm36d.util;

import com.aidchow.mm36d.networdk.MM36DOkHttpClient;

import java.io.IOException;
import java.io.InputStream;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by aidchow on 17-5-22.
 */

public class FileDownloader {
    public interface DownloadListener {
        void downLoadComplete(InputStream inputStream, int contentLength);

        void downLoadFail(String msg);
    }

    private static volatile FileDownloader INSTANCE = null;
    private Call call;
    private OkHttpClient okHttpClient;

    private FileDownloader() {
        okHttpClient = MM36DOkHttpClient.getOkHttpClient();
    }

    public static FileDownloader getInstance() {
        if (INSTANCE == null) {
            synchronized (FileDownloader.class) {
                if (INSTANCE == null) {
                    INSTANCE = new FileDownloader();
                }
            }
        }
        return INSTANCE;
    }

    public void DownloadFile(String url, final DownloadListener downloadListener) {
        Request request = new Request.Builder()
                .url(url).build();
        call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, final IOException e) {
                downloadListener.downLoadFail(e.getMessage());
            }

            @Override
            public void onResponse(Call call, final Response response) throws IOException {
                if (response.isSuccessful()) {
                    InputStream inputStream = response.body().byteStream();
                    int contentLength = Integer.parseInt(response.headers().get("Content-Length"));
                    downloadListener.downLoadComplete(inputStream, contentLength);
                } else {
                    downloadListener.downLoadFail(response.message());
                }
            }
        });
    }


    public void cancel() {
        if (call != null) {
            call.cancel();
        }
    }


}
