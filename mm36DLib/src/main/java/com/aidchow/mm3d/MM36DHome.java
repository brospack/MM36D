package com.aidchow.mm3d;

import com.aidchow.entity.ImageEntity;
import com.aidchow.retrofit.MM36DCallBack;
import com.aidchow.retrofit.RequestServices;
import com.aidchow.util.ParseUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by AicChow on 2017/3/30.
 * Access the home page data
 */
class MM36DHome {

    static void getMM36DHome(Retrofit retrofit, int page, final OnRequestListener<List<ImageEntity>> onRequestListener) {
        RequestServices requestServices = retrofit.create(RequestServices.class);
        Call<String> call = requestServices.mm36dhome(page);
        call.enqueue(new MM36DCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                onRequestListener.onRequestSuccess(ParseUtil.parseImageList(response));
            }

            @Override
            public void onFail(String failMsg) {
                onRequestListener.onRequestFail(failMsg);
            }
        });

    }

}
