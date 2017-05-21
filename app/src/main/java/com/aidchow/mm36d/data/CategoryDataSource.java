package com.aidchow.mm36d.data;

import com.aidchow.entity.FindFlowerEntity;

import java.util.List;

/**
 * Created by 78537 on 2017/4/9.
 */

public interface CategoryDataSource {
    interface LoadCategoryCallBack {
        void onCategoryLoad(List<FindFlowerEntity> findFlowerEntities);

        void onCategoryLoadFail(String msg);
    }

    void getLabels(int page, LoadCategoryCallBack categoryCallBack);

}
