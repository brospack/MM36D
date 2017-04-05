package com.aidchow.mm36d.home;

import android.support.annotation.NonNull;
import android.support.v7.util.SortedList;

import com.aidchow.entity.ImageEntity;
import com.aidchow.mm36d.BasePresenter;
import com.aidchow.mm36d.BaseView;

import java.util.List;

/**
 * Created by AidChow on 2017/4/3.
 */

public interface HomeContract {
    interface View extends BaseView<Presenter> {
        void showImages(List<ImageEntity> list);

        void showNoImages();

        void showLoadingImages(boolean isActive);


        void showLoadingError(String errorMsg);

        boolean isActive();

    }

    interface Presenter extends BasePresenter {
        void loadHomeImages(@NonNull int page);

        void loadOthers(@NonNull String type, @NonNull int page);
    }
}
