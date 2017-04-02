package com.aidchow.retrofit;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Path;

/**
 * Created by AicChow on 2017/3/30.
 */
public interface RequestServices {

    @Headers("User-Agent: Chrome/57.0.2987.98")
    @GET("/home/0/{page}")
    Call<String> mm36dhome(@Path("page") int page);

    @Headers("User-Agent: Chrome/57.0.2987.98")
    @GET("/{type}/{page}")
    Call<String> mm36dothers(@Path("type") String type, @Path("page") int page);

    @Headers("User-Agent: Chrome/57.0.2987.98")
    @GET("/labels/{page}")
    Call<String> mm36dlabels(@Path("page") int page);

    @Headers("User-Agent: Chrome/57.0.2987.98")
    @GET("/toLabel/{category}/{page}")
    Call<String> mm36dtoLabel(@Path("category") String category, @Path("page") int page);

    @Headers("User-Agent: Chrome/57.0.2987.98")
    @GET("/belle/{menuId}/{toLabel}/{labelId}/{pages}")
    Call<String> mm36dDetails(@Path("menuId") String menuId,
                             @Path("toLabel") String toLabel,
                             @Path("labelId") String labelId,
                             @Path("pages") int pages);
}
