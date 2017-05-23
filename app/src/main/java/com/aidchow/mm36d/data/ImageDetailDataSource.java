package com.aidchow.mm36d.data;

import android.net.Uri;

/**
 * Created by 78537 on 2017/4/22.
 */

public interface ImageDetailDataSource {


    interface DownloadImageCall {
        void downloadComplete(String filePath);

        void downloadError(String msg);

        void downloading(long downloaded, int total);

        void shareUri(Uri uri);
    }


    void downloadImage(String url, DownloadImageCall downloadImageCall);

    void downloadCancel();

    void setImageWalls(String url, DownloadImageCall downloadImageCall);

    void shareImage(String url, DownloadImageCall downloadImageCall);

}
