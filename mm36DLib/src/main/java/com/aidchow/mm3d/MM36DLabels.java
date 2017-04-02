package com.aidchow.mm3d;

import com.aidchow.entity.FindFlowerEntity;
import com.aidchow.entity.ImageEntity;
import com.aidchow.retrofit.MM36DCallBack;
import com.aidchow.retrofit.RequestServices;
import com.aidchow.util.ParseUtil;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;

/**
 * Created by AicChow on 2017/3/30.
 * Access the Labels data
 */
class MM36DLabels {

    static void getMM36DLabels(Retrofit retrofit, int page, final OnRequestListener<List<FindFlowerEntity>> onRequestListener) {
        RequestServices requestServices = retrofit.create(RequestServices.class);
        final Call<String> call = requestServices.mm36dlabels(page);
        call.enqueue(new MM36DCallBack<String>() {
            @Override
            public void onSuccess(String response) {
                onRequestListener.onRequestSuccess(ParseUtil.parseFindFlowerList(response));
            }

            @Override
            public void onFail(String failMsg) {
                onRequestListener.onRequestFail(failMsg);
            }
        });

    }


}
