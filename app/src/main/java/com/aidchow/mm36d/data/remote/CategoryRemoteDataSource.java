package com.aidchow.mm36d.data.remote;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.mm36d.App;
import com.aidchow.mm36d.data.CategoryDataSource;
import com.aidchow.mm3d.OnRequestListener;

import java.util.List;

/**
 * Created by 78537 on 2017/4/9.
 */

public class CategoryRemoteDataSource implements CategoryDataSource {

    @Override
    public void getLabels(int page, final LoadCategoryCallBack categoryCallBack) {
        App.getMm36D().labels(page, new OnRequestListener<List<FindFlowerEntity>>() {
            @Override
            public void onRequestSuccess(List<FindFlowerEntity> response) {
                categoryCallBack.onCategoryLoad(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                categoryCallBack.onCategoryLoadFail(failReason);
            }
        });

    }
}
