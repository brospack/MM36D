package com.aidchow.mm3d;

/**
 * Created by AidChow on 2017/3/31.
 * The response interface
 */

public interface OnRequestListener<T> {
    void onRequestSuccess(T response);

    void onRequestFail(String failReason);
}
