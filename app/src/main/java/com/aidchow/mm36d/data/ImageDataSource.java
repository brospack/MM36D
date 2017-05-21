package com.aidchow.mm36d.data;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;

import com.aidchow.entity.ImageEntity;

import java.util.List;

/**
 * Created by AidChow on 2017/4/3.
 */

public interface ImageDataSource {
    interface LoadImageCallBack {
        void onImageLoaded(List<ImageEntity> imageEntities);

        void onImageLoadedError(String errorMsg);
    }

    void getHomeImages(@NonNull int page, LoadImageCallBack callBack);

    void getOthers(@NonNull String type, @NonNull int page, LoadImageCallBack callBack);

    void getToLabels(String category, int page, LoadImageCallBack callBack);
}
