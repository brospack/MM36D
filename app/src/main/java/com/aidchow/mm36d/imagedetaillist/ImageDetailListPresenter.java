package com.aidchow.mm36d.imagedetaillist;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm36d.data.remote.ImageDetailListRemoteDataSource;
import com.aidchow.mm36d.data.remote.ImageDetailRemoteDataSource;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailListPresenter implements ImageDetailListContract.Presenter {
    private ImageDetailListRemoteDataSource imageDetailListRemoteDataSource;
    private final ImageDetailListContract.View view;

    public ImageDetailListPresenter(ImageDetailListContract.View view) {
        this.view = view;
        this.imageDetailListRemoteDataSource = new ImageDetailListRemoteDataSource();
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getImageDetail(String label, int page, final boolean refresh) {
        view.showLoading(true);
        imageDetailListRemoteDataSource.getImageDetail(label, page, new ImageDetailListRemoteDataSource.LoadImageDetailCall() {
            @Override
            public void onImageDetailLoad(ImageDetailEntity imageDetailEntity) {
                view.showImageDetail(imageDetailEntity, refresh);
                view.showLoading(false);
            }

            @Override
            public void onImageDetailLoadError(String msg) {
                view.showLoading(false);
                view.showLoadError(msg);
            }
        });
    }


}
