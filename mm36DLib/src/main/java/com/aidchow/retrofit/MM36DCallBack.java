package com.aidchow.retrofit;

import java.net.ConnectException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;
import java.net.UnknownServiceException;

import okhttp3.internal.tls.OkHostnameVerifier;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.HttpException;
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
            System.out.println(response.code() + " 错误码 ");
            System.out.println(response.message() + " 错误信息 ");
            onFail(response.message());
        }
    }

    @Override
    public void onFailure(Call<T> call, Throwable throwable) {
        String msg = "";
        if (throwable instanceof SocketTimeoutException) {
            msg = "request Time out";
        } else if (throwable instanceof ConnectException) {
            msg = "Connect error";
        } else if (throwable instanceof HttpException) {
            msg = "net work can not use";
        } else if (throwable instanceof UnknownHostException) {
            msg = throwable.getMessage();
        } else if (throwable instanceof UnknownServiceException) {
            msg = "net work service can not use";
        }
        onFail(msg);
    }

    public abstract void onSuccess(T response);

    public abstract void onFail(String failMsg);

}
