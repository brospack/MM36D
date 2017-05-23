package com.aidchow.mm36d.data.remote;

import android.app.WallpaperManager;
import android.content.Context;
import android.net.Uri;
import android.support.v4.content.FileProvider;

import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm36d.util.FileDownloader;
import com.aidchow.mm36d.util.Logger;
import com.aidchow.mm36d.util.Utils;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailRemoteDataSource implements ImageDetailDataSource {
    private Context mContext;

    public ImageDetailRemoteDataSource(Context context) {
        this.mContext = context;
    }

    @Override
    public void downloadImage(final String url, final DownloadImageCall downloadImageCall) {

        FileDownloader.getInstance().DownloadFile(url, new FileDownloader.DownloadListener() {
            @Override
            public void downLoadComplete(InputStream inputStream, int contentLength) {
                try {
                    File dir = Utils.getAlbumStorageDir("tasteFlower");

                    String fileName = url.substring(url.lastIndexOf("/") + 1, url.length()).trim();
                    Logger.d("FUCK", fileName);
                    File downloadFile = new File(dir, fileName);
                    if (!downloadFile.exists()) {
                        downloadFile.createNewFile();
                        Logger.d("FUCK", fileName + "create");
                    }
                    FileOutputStream out = new FileOutputStream(downloadFile);
                    byte[] buf = new byte[1024];
                    long fileSum = 0;
                    int len;
                    while ((len = inputStream.read(buf)) != -1) {
                        fileSum += len;
                        downloadImageCall.downloading(fileSum, contentLength);
                        out.write(buf, 0, len);
                        Logger.d("FUCK", len + " len");
                    }
                    downloadImageCall.downloadComplete(downloadFile.getCanonicalPath());
                    out.close();
                    inputStream.close();
                } catch (Exception e) {
                    downloadImageCall.downloadError(e.getMessage());
                }


            }

            @Override
            public void downLoadFail(String msg) {
                downloadImageCall.downloadError(msg);
            }
        });
    }

    @Override
    public void downloadCancel() {
        FileDownloader.getInstance().cancel();
    }

    @Override
    public void setImageWalls(String url, final DownloadImageCall downloadImageCall) {
        FileDownloader.getInstance().DownloadFile(url, new FileDownloader.DownloadListener() {
            @Override
            public void downLoadComplete(InputStream inputStream, int contentLength) {
                try {
                    downloadImageCall.downloading(contentLength, contentLength);
                    WallpaperManager.getInstance(mContext).setStream(inputStream);

                    downloadImageCall.downloadComplete(null);
                    inputStream.close();
                } catch (IOException e) {
                    downLoadFail(e.getMessage());
                }
            }

            @Override
            public void downLoadFail(String msg) {
                downloadImageCall.downloadError(msg);
            }
        });
    }

    @Override
    public void shareImage(final String url, final DownloadImageCall downloadImageCall) {
        FileDownloader.getInstance().DownloadFile(url, new FileDownloader.DownloadListener() {
            @Override
            public void downLoadComplete(InputStream inputStream, int contentLength) {
                try {
                    File dir = new File(mContext.getCacheDir(), "images");
                    if (!dir.exists()) {
                        dir.mkdirs();
                    }

                    String fileName = url.substring(url.lastIndexOf("/") + 1, url.length()).trim();
                    File file = new File(dir, fileName);
                    if (!file.exists()) {
                        file.createNewFile();
                    }
                    FileOutputStream out = new FileOutputStream(file);
                    byte[] buf = new byte[1024];
                    long fileSum = 0;
                    int len;
                    while ((len = inputStream.read(buf)) != -1) {
                        fileSum += len;
                        downloadImageCall.downloading(fileSum, contentLength);
                        out.write(buf, 0, len);
                    }
                    downloadImageCall.downloadComplete(file.getPath());
                    Uri contentUri = FileProvider.getUriForFile(mContext, "com.aidchow.mm36d.fileprovider", file);
                    downloadImageCall.shareUri(contentUri);
                } catch (Exception e) {
                    downloadImageCall.downloadError(e.getMessage());
                }
            }

            @Override
            public void downLoadFail(String msg) {
                downloadImageCall.downloadError(msg);
            }
        });
    }
}
