package com.duoduolicai360.databindingdemo.ui;

import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.duoduolicai360.databindingdemo.http.ApiServiceFactory;
import com.duoduolicai360.databindingdemo.http.GitHubApi;

/**
 * Created by swg on 2017/8/25.
 */

public class GitHubContributorsActivity extends BaseActivity {

    private GitHubApi mGitHubApi = ApiServiceFactory.createService(GitHubApi.class);

    public RecyclerView mRecyclerView;
    public TextView tvTip;


}
