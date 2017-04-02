package com.aidchow.retrofit;

import okhttp3.ResponseBody;
import retrofit2.Converter;
import retrofit2.Retrofit;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;

/**
 * Created by 78537 on 2016/10/19.
 */
public class StringConverterFactory extends Converter.Factory {
    private static StringConverterFactory stringConverterFactory = new StringConverterFactory();

    public static StringConverterFactory create() {
        return stringConverterFactory;
    }

    @Override
    public Converter<ResponseBody, ?> responseBodyConverter(Type type, Annotation[] annotations, Retrofit retrofit) {
        if (type == String.class) {
            return new StringConverter();
        }
        return null;
    }


}

