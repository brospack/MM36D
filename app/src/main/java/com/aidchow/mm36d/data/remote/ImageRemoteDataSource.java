package com.aidchow.mm36d.data.remote;

import com.aidchow.entity.ImageEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.data.ImageDataSource;
import com.aidchow.mm3d.OnRequestListener;

import java.util.List;

/**
 * Created by AidChow on 2017/4/3.
 */

public class ImageRemoteDataSource implements ImageDataSource {
    @Override
    public void getHomeImages(int page, final LoadImageCallBack callBack) {
        App.getMm36D().home(page, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                callBack.onImageLoaded(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                callBack.onImageLoadedError(failReason);
            }
        });
    }

    @Override
    public void getOthers(String type, int page, final LoadImageCallBack callBack) {
        App.getMm36D().others(type, page, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                for (ImageEntity imageEntity : response) {

                }
                callBack.onImageLoaded(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                callBack.onImageLoadedError(failReason);
            }
        });
    }

    @Override
    public void getToLabels(String category, int page, final LoadImageCallBack callBack) {
        App.getMm36D().tolabel(category, page, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                callBack.onImageLoaded(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                callBack.onImageLoadedError(failReason);
            }
        });
    }
}
