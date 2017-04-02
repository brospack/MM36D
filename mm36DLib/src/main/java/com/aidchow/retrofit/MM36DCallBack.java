package com.aidchow.retrofit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by AicChow on 2017/3/30.
 */
public abstract class MM36DCallBack<T> implements Callback<T> {
    @Override
    public void onResponse(Call<T> call, Response<T> response) {
        if (response.isSuccessful()) {
            onSuccess(response.body());
        } else {
            onFail(response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        onFail(throwable.toString());
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(String failMsg);

}
