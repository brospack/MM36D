package com.aidchow.mm36d.imagedetaillist;

import android.content.Context;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.data.remote.ImageDetailListRemoteDataSource;

/**
 * Created by AidChow on 2017/4/22.
 */

public class ImageDetailListPresenter implements ImageDetailListContract.Presenter {
    private ImageDetailListRemoteDataSource imageDetailListRemoteDataSource;
    private final ImageDetailListContract.View view;

    public ImageDetailListPresenter(Context context, ImageDetailListContract.View view) {
        this.view = view;
        this.imageDetailListRemoteDataSource = new ImageDetailListRemoteDataSource(context);
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

    @Override
    public void saveImage(String imageUrl) {
        imageDetailListRemoteDataSource.saveImages(imageUrl);

    }

    @Override
    public void delete(String imageUrl) {
        imageDetailListRemoteDataSource.deleteImage(imageUrl);
    }

    @Override
    public boolean loadImage(String imageUrl) {
        return imageDetailListRemoteDataSource.loadImage(imageUrl);
    }


}
