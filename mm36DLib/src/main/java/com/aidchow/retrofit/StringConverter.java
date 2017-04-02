package com.aidchow.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Converter;

import java.io.IOException;

/**
 * Created by 78537 on 2016/10/19.
 */
public class StringConverter implements Converter<ResponseBody, String> {

    @Override

    public String convert(ResponseBody t) throws IOException {

        return t.string();
    }
}
