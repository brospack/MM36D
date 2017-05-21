package com.aidchow.mm36d.data;

import com.aidchow.entity.ImageDetailEntity;

/**
 * Created by 78537 on 2017/4/22.
 */

public interface ImageDetailDataSource {
    interface LoadImageDetailCall {
        void onImageDetailLoad(ImageDetailEntity imageDetailEntity);

        void onImageDetailLoadError(String msg);
    }

    void getImageDetail(String label, int page, LoadImageDetailCall loadImageDetailCall);
}
