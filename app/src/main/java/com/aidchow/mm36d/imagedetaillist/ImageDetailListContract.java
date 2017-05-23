package com.aidchow.mm36d.imagedetaillist;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.mm36d.BasePresenter;
import com.aidchow.mm36d.BaseView;

/**
 * Created by AidChow on 2017/4/11.
 */

public interface ImageDetailListContract {

    interface View extends BaseView<Presenter> {
        void showImageDetail(ImageDetailEntity imageDetailEntity, boolean refresh);

        void showLoadError(String msg);

        void showLoading(boolean isShow);

    }

    interface Presenter extends BasePresenter {
        void getImageDetail(String label, int page, boolean refresh);
    }
}
