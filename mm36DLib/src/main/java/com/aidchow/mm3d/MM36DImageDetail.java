package com.aidchow.mm3d;

import com.aidchow.entity.ImageDetailEntity;
import com.aidchow.entity.ImageEntity;
import com.aidchow.retrofit.BaseApi;
import com.aidchow.retrofit.MM36DCallBack;
import com.aidchow.retrofit.RequestServices;
import com.aidchow.retrofit.StringConverter;
import com.aidchow.retrofit.StringConverterFactory;
import com.aidchow.util.ParseUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by AicChow on 2017/3/30.
 * Access the image details
 */
class MM36DImageDetail {


    static void getMM36DImageDetail(Retrofit retrofit, String menuId, String toLabel, String labelId, int page, final OnRequestListener<ImageDetailEntity> onRequestListener) {
        RequestServices requestServices = retrofit.create(RequestServices.class);
        Call<String> call = requestServices.mm36dDetails(menuId, toLabel, labelId, page);
        call.enqueue(new MM36DCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                onRequestListener.onRequestSuccess(ParseUtil.parseImageDetailEntity(response));
            }

            @Override
            public void onFail(String failMsg) {
                onRequestListener.onRequestFail(failMsg);
            }
        });

    }


}
