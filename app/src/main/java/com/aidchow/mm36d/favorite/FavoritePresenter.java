package com.aidchow.mm36d.favorite;

import android.content.Context;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.data.ImageDetailListDataSource;
import com.aidchow.mm36d.data.remote.ImageDetailListRemoteDataSource;
import com.aidchow.mm36d.imagedetaillist.ImageDetailListContract;

/**
 * Created by AidChow on 2017/4/22.
 */

public class FavoritePresenter implements FavoriteContract.Presenter {
    private ImageDetailListRemoteDataSource imageDetailListRemoteDataSource;
    private final FavoriteContract.View view;

    public FavoritePresenter(Context context, FavoriteContract.View view) {
        this.view = view;
        this.imageDetailListRemoteDataSource = new ImageDetailListRemoteDataSource(context);
        view.setPresenter(this);
    }

    @Override
    public void start() {
        loadImages(false);
    }


    @Override
    public void loadImages(final boolean refresh) {
        view.showLoading(true);
        imageDetailListRemoteDataSource.loadLocalImages(new ImageDetailListDataSource.LoadImageDetailCall() {
            @Override
            public void onImageDetailLoad(ImageDetailEntity imageDetailEntity) {
                view.showImageDetail(imageDetailEntity, refresh);
                view.showLoading(false);
            }

            @Override
            public void onImageDetailLoadError(String msg) {
                view.showLoadError(msg);
                view.showLoading(false);
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
