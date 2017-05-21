package com.aidchow.mm36d.category;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.mm36d.BasePresenter;
import com.aidchow.mm36d.BaseView;

import java.util.List;

/**
 * Created by 78537 on 2017/4/9.
 */

public class CategoryContract {
    interface View extends BaseView<Presenter> {
        void showCategories(List<FindFlowerEntity> list, boolean refresh, boolean loadMore);

        void showNoCategories();

        void showLoadingCategories(boolean isActive);

        void loadingFinish();

        void showLoadingError(String errorMsg);

        boolean isActive();
    }

    interface Presenter extends BasePresenter {
        void getLabels(int page, boolean refresh, boolean loadMore);
    }
}
