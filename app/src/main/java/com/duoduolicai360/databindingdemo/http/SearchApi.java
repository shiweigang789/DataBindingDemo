package com.duoduolicai360.databindingdemo.http;

import java.util.List;

import retrofit.http.GET;
import retrofit.http.Query;
import rx.Observable;

/**
 * Created by swg on 2017/8/28.
 */

public interface SearchApi {

    @GET("/search")
    Observable<List<String>> search(@Query("key") String key);

}
