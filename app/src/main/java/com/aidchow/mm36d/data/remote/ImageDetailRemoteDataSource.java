package com.aidchow.mm36d.data.remote;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm3d.OnRequestListener;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailRemoteDataSource implements ImageDetailDataSource {

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
}
