package com.aidchow.mm36d.imagedetail;

import android.net.Uri;

import com.aidchow.mm36d.BasePresenter;
import com.aidchow.mm36d.BaseView;

/**
 * Created by AidChow on 2017/4/11.
 */

public interface ImageDetailContract {

    interface View extends BaseView<Presenter> {

        void downLoadComplete(String filePath);

        void downLoadFail(String msg);

        void showDownLoading(long downloaded, int total);

        void setShare(Uri uri);

    }

    interface Presenter extends BasePresenter {

        void downloadImage(String url);

        void downloadCancel();

        void setImageWalls(String url);

        void shareImage(String url);


    }


}
