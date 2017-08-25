package com.duoduolicai360.databindingdemo.http;

import com.duoduolicai360.databindingdemo.model.AuthToken;
import com.duoduolicai360.databindingdemo.model.Contributor;

import java.util.List;

import retrofit.http.GET;
import rx.Observable;

/**
 * Created by swg on 2017/8/25.
 */

public interface GitHubApi {

    @GET("/contributor/list")
    Observable<List<Contributor>> contributors();

    @GET("/token")
    AuthToken refreshToken();


}
