package com.aidchow.mm36d.home;

import android.support.annotation.NonNull;

import com.aidchow.entity.ImageEntity;
import com.aidchow.mm36d.data.ImageDataSource;
import com.aidchow.mm36d.data.remote.ImageRemoteDataSource;

import java.io.Serializable;
import java.util.List;

/**
 * Created by 78537 on 2017/4/3.
 */

public class HomePresenter implements HomeContract.Presenter, Serializable {
    private static final long serialVersionUID = 8871665432556701199L;
    private ImageRemoteDataSource mImageRemoteDataSource;
    private final HomeContract.View mHomeView;
    private final HomeFragment.HOME_TYPE home_type;

    public HomePresenter(HomeContract.View homeView, HomeFragment.HOME_TYPE home_type) {
        this.home_type = home_type;
        this.mImageRemoteDataSource = new ImageRemoteDataSource();
        this.mHomeView = homeView;
        mHomeView.setPresenter(this);

    }

    @Override
    public void start() {
        if (home_type == HomeFragment.HOME_TYPE.HOME) {
            loadHomeImages(1);
        }
        if (home_type == HomeFragment.HOME_TYPE.LIKE) {
            loadOthers("like", 1);
        }
        if (home_type == HomeFragment.HOME_TYPE.POP) {
            loadOthers("pop", 1);
        }
    }

    @Override
    public void loadHomeImages(@NonNull int page) {
        mHomeView.showLoadingImages(true);
        mImageRemoteDataSource.getHomeImages(page, new ImageDataSource.LoadImageCallBack() {
            @Override
            public void onImageLoaded(List<ImageEntity> imageEntities) {
                if (imageEntities.isEmpty()) {
                    mHomeView.showNoImages();
                } else {
                    mHomeView.showImages(imageEntities);
                }
                mHomeView.showLoadingImages(false);
            }

            @Override
            public void onImageLoadedError(String errorMsg) {
                if (!mHomeView.isActive()) {
                    return;
                }
                mHomeView.showLoadingImages(false);
                mHomeView.showLoadingError(errorMsg);
            }

        });
    }

    @Override
    public void loadOthers(@NonNull String type, @NonNull int page) {
        mHomeView.showLoadingImages(true);
        mImageRemoteDataSource.getOthers(type, page, new ImageDataSource.LoadImageCallBack() {
            @Override
            public void onImageLoaded(List<ImageEntity> imageEntities) {
                if (!mHomeView.isActive()) {
                    return;
                }
                if (imageEntities.isEmpty()) {
                    mHomeView.showNoImages();
                } else {
                    mHomeView.showImages(imageEntities);
                }
                mHomeView.showLoadingImages(false);
            }

            @Override
            public void onImageLoadedError(String errorMsg) {
                if (!mHomeView.isActive()) {
                    return;
                }
                mHomeView.showLoadingImages(false);
                mHomeView.showLoadingError(errorMsg);
            }
        });
    }


}
