package com.aidchow.mm36d.imagedetail;

import android.content.Context;
import android.net.Uri;

import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm36d.data.remote.ImageDetailRemoteDataSource;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailPresenter implements ImageDetailContract.Presenter {
    private ImageDetailRemoteDataSource imageDetailRemoteDataSource;
    private final ImageDetailContract.View view;

    public ImageDetailPresenter(Context context, ImageDetailContract.View view) {
        this.view = view;
        this.imageDetailRemoteDataSource = new ImageDetailRemoteDataSource(context);
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }


    @Override
    public void downloadImage(String url) {
        imageDetailRemoteDataSource.downloadImage(url, new ImageDetailDataSource.DownloadImageCall() {
            @Override
            public void downloadComplete(String filePath) {
                view.downLoadComplete(filePath);
            }

            @Override
            public void downloadError(String msg) {
                view.downLoadFail(msg);
            }

            @Override
            public void downloading(long downloaded, int total) {
                view.showDownLoading(downloaded, total);
            }

            @Override
            public void shareUri(Uri uri) {

            }
        });
    }

    @Override
    public void downloadCancel() {
        imageDetailRemoteDataSource.downloadCancel();
    }

    @Override
    public void setImageWalls(String url) {
        imageDetailRemoteDataSource.setImageWalls(url, new ImageDetailDataSource.DownloadImageCall() {
            @Override
            public void downloadComplete(String filePath) {
                view.downLoadComplete(filePath);
            }

            @Override
            public void downloadError(String msg) {
                view.downLoadFail(msg);
            }

            @Override
            public void downloading(long downloaded, int total) {
                view.showDownLoading(downloaded, total);
            }

            @Override
            public void shareUri(Uri uri) {

            }
        });
    }

    @Override
    public void shareImage(String url) {
        imageDetailRemoteDataSource.shareImage(url, new ImageDetailDataSource.DownloadImageCall() {
            @Override
            public void downloadComplete(String filePath) {
                view.downLoadComplete(filePath);
            }

            @Override
            public void downloadError(String msg) {
                view.downLoadFail(msg);
            }

            @Override
            public void downloading(long downloaded, int total) {
                view.showDownLoading(downloaded, total);
            }

            @Override
            public void shareUri(Uri uri) {
                view.setShare(uri);
            }
        });
    }


}
