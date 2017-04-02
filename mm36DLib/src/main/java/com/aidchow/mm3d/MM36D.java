package com.aidchow.mm3d;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.entity.ImageEntity;
import com.aidchow.retrofit.BaseRetrofit;

import java.util.List;

import okhttp3.OkHttpClient;

/**
 * Created by AicChow on 2017/3/30.
 * the only entrance to get image data resource
 */
public class MM36D {


    private static BaseRetrofit baseRetrofit = null;
    private volatile static MM36D INSTANCE = null;

    private MM36D() {
    }

    /**
     * Your application just need created MM36 once
     * custom your own okHttpClient usage cache or do others
     *
     * @param okHttpClient your custom's okHttpClient
     * @return MM36D
     */
    public static MM36D create(OkHttpClient okHttpClient) {
        if (INSTANCE == null) {
            synchronized (MM36D.class) {
                if (INSTANCE == null) {
                    baseRetrofit = new BaseRetrofit(okHttpClient);
                    INSTANCE = new MM36D();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * Your application just need created MM36 once
     * the default create
     * if you do not want to cache or do other things
     * you can use this to create MM36D
     *
     * @return MM36D
     */
    public static MM36D create() {
        if (INSTANCE == null) {
            synchronized (MM36D.class) {
                if (INSTANCE == null) {
                    baseRetrofit = new BaseRetrofit();
                    INSTANCE = new MM36D();
                }
            }
        }
        return INSTANCE;
    }

    /**
     * access that web site { http://www.mm36d.com } home page images list
     *
     * @param pages             current pages
     * @param onRequestListener the requestListener with a callback when request  has response
     */
    public void home(int pages, final OnRequestListener<List<ImageEntity>> onRequestListener) {
        MM36DHome.getMM36DHome(baseRetrofit.retrofit(), pages, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                onRequestListener.onRequestSuccess(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                onRequestListener.onRequestFail(failReason);
            }
        });
    }

    /**
     * access that web site { http://www.mm36d.com }  pop or like pages image list
     *
     * @param type              pop or like
     * @param pages             current pages
     * @param onRequestListener the requestListener with a callback when request  has response
     */
    public void others(String type, int pages, final OnRequestListener<List<ImageEntity>> onRequestListener) {
        MM36DOthers.getMM36DOthers(baseRetrofit.retrofit(), type, pages, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                onRequestListener.onRequestSuccess(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                onRequestListener.onRequestFail(failReason);
            }
        });
    }

    /**
     * access that web site { http://www.mm36d.com }  category pages image list
     *
     * @param pages             current pages
     * @param onRequestListener the requestListener with a callback when request  has response
     */
    public void labels(int pages, final OnRequestListener<List<FindFlowerEntity>> onRequestListener) {
        MM36DLabels.getMM36DLabels(baseRetrofit.retrofit(), pages, new OnRequestListener<List<FindFlowerEntity>>() {
            @Override
            public void onRequestSuccess(List<FindFlowerEntity> response) {
                onRequestListener.onRequestSuccess(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                onRequestListener.onRequestFail(failReason);
            }
        });
    }

    /**
     * access that web site { http://www.mm36d.com }  category info pages image list
     *
     * @param category          the category id
     * @param pages             current pages
     * @param onRequestListener the requestListener with a callback when request  has response
     */
    public void tolabel(String category, int pages, final OnRequestListener<List<ImageEntity>> onRequestListener) {
        MM36DToLabel.getMM36DToLable(baseRetrofit.retrofit(), category, pages, new OnRequestListener<List<ImageEntity>>() {
            @Override
            public void onRequestSuccess(List<ImageEntity> response) {
                onRequestListener.onRequestSuccess(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                onRequestListener.onRequestFail(failReason);
            }
        });
    }

    /**
     * access that web site { http://www.mm36d.com }  image details
     *
     * @param menuId            menuID home=0,pop=1,like=2,tolabel=3
     * @param toLabel           menuID home=0,pop=0,like=0,tolabel=toLabel
     * @param labelId           the list item id
     * @param page              current pages
     * @param onRequestListener the requestListener with a callback when request  has response
     */
    public void imageDetail(String menuId, String toLabel, String labelId, int page,
                            final OnRequestListener<ImageDetailEntity> onRequestListener) {
        MM36DImageDetail.getMM36DImageDetail(baseRetrofit.retrofit(), menuId, toLabel, labelId, page, new OnRequestListener<ImageDetailEntity>() {
            @Override
            public void onRequestSuccess(ImageDetailEntity response) {
                onRequestListener.onRequestSuccess(response);
            }

            @Override
            public void onRequestFail(String failReason) {
                onRequestListener.onRequestFail(failReason);
            }
        });
    }

}
