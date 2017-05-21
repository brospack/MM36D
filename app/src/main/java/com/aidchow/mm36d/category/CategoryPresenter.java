package com.aidchow.mm36d.category;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.mm36d.data.CategoryDataSource;
import com.aidchow.mm36d.data.remote.CategoryRemoteDataSource;

import java.util.List;

/**
 * Created by 78537 on 2017/4/9.
 */

public class CategoryPresenter implements CategoryContract.Presenter {
    private CategoryRemoteDataSource remoteDataSource;
    private final CategoryContract.View mView;
    private boolean firstLoad = true;

    public CategoryPresenter(CategoryContract.View mView) {
        this.mView = mView;
        this.remoteDataSource = new CategoryRemoteDataSource();
        this.mView.setPresenter(this);
    }

    @Override
    public void start() {
        if (firstLoad) {
            getLabels(1, false, false);
        }
    }


    @Override
    public void getLabels(final int page, final boolean refresh, final boolean loadMore) {
        if (!mView.isActive()) {
            return;
        }
        mView.showLoadingCategories(!loadMore);
        remoteDataSource.getLabels(page, new CategoryDataSource.LoadCategoryCallBack() {
            @Override
            public void onCategoryLoad(List<FindFlowerEntity> findFlowerEntities) {
                if (findFlowerEntities.isEmpty() && page == 1) {
                    mView.showNoCategories();
                }
                if (findFlowerEntities.isEmpty() && page > 1) {
                    mView.loadingFinish();
                } else if (page >= 1 && !findFlowerEntities.isEmpty()) {
                    mView.showCategories(findFlowerEntities, refresh, loadMore);
                }
                mView.showLoadingCategories(false);
            }

            @Override
            public void onCategoryLoadFail(String msg) {
                mView.showLoadingCategories(false);
                mView.showLoadingError(msg);
            }
        });
    }
}
