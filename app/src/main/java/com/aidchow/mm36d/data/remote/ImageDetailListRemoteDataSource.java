package com.aidchow.mm36d.data.remote;

import android.content.Context;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm36d.data.ImageDetailListDataSource;
import com.aidchow.mm36d.data.local.ImageLocalDataSource;
import com.aidchow.mm36d.util.FileDownloader;
import com.aidchow.mm36d.util.Logger;
import com.aidchow.mm36d.util.Utils;
import com.aidchow.mm3d.OnRequestListener;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailListRemoteDataSource implements ImageDetailListDataSource {
    private ImageLocalDataSource imageLocalDataSource;

    public ImageDetailListRemoteDataSource(Context context) {
        imageLocalDataSource = ImageLocalDataSource.getInstance(context);
    }

    @Override
    public void getImageDetail(String label, int page, final LoadImageDetailCall loadImageDetailCall) {
        App.getMm36D().imageDetail("0", "0", label, page, new OnRequestListener<ImageDetailEntity>() {
            @Override
            public void onRequestSuccess(ImageDetailEntity response) {
                loadImageDetailCall.onImageDetailLoad(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                loadImageDetailCall.onImageDetailLoadError(failReason);
            }
        });
    }

    @Override
    public void saveImages(String imageUrl) {
        imageLocalDataSource.saveImages(imageUrl);
    }

    @Override
    public void loadLocalImages(final LoadImageDetailCall loadImageDetailCall) {
        imageLocalDataSource.loadLocalImages(new LoadImageDetailCall() {
            @Override
            public void onImageDetailLoad(ImageDetailEntity imageDetailEntity) {
                loadImageDetailCall.onImageDetailLoad(imageDetailEntity);
            }

            @Override
            public void onImageDetailLoadError(String msg) {
                loadImageDetailCall.onImageDetailLoadError(msg);
            }
        });
    }

    @Override
    public boolean loadImage(String url) {
        return imageLocalDataSource.loadImage(url);
    }

    @Override
    public void deleteImage(String imageUrl) {
        imageLocalDataSource.deleteImage(imageUrl);
    }


}
