package com.aidchow.mm36d.imagedetail;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.data.ImageDetailDataSource;
import com.aidchow.mm36d.data.remote.ImageDetailRemoteDataSource;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailPresenter implements ImageDetailContract.Presenter {
    private ImageDetailRemoteDataSource imageDetailRemoteDataSource;
    private final ImageDetailContract.View view;

    public ImageDetailPresenter(ImageDetailContract.View view) {
        this.view = view;
        this.imageDetailRemoteDataSource = new ImageDetailRemoteDataSource();
        view.setPresenter(this);
    }

    @Override
    public void start() {

    }

    @Override
    public void getImageDetail(String label, int page, final boolean refresh) {
        view.showLoading(true);
        imageDetailRemoteDataSource.getImageDetail(label, page, new ImageDetailDataSource.LoadImageDetailCall() {
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
