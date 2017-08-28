package com.duoduolicai360.databindingdemo.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.widget.TextView;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.adapter.BaseAdapter;
import com.duoduolicai360.databindingdemo.adapter.ContributorAdapter;
import com.duoduolicai360.databindingdemo.exception.AccessDenyException;
import com.duoduolicai360.databindingdemo.http.ApiServiceFactory;
import com.duoduolicai360.databindingdemo.http.GitHubApi;
import com.duoduolicai360.databindingdemo.http.NetErrorType;
import com.duoduolicai360.databindingdemo.model.AuthToken;
import com.duoduolicai360.databindingdemo.model.Contributor;
import com.duoduolicai360.databindingdemo.utils.DividerItemDecoration;
import com.duoduolicai360.databindingdemo.utils.RecyclerViewUtil;

import java.util.List;

import rx.Observable;
import rx.Observer;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by swg on 2017/8/25.
 */

public class GitHubContributorsActivity extends BaseActivity {

    private GitHubApi mGitHubApi = ApiServiceFactory.createService(GitHubApi.class);

    public RecyclerView mRecyclerView;
    public TextView tvTip;
    public BaseAdapter<Contributor> mAdapter;
    private boolean loading;

    private RecyclerView.OnScrollListener scrollListener;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_contributors);
        initViews();

        showLoadingDialog();
        requestContributes();

    }

    private void initViews() {
        tvTip = (TextView) findViewById(R.id.tv_tips);
        mRecyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        mAdapter = new ContributorAdapter(this);
        RecyclerViewUtil.setLinearManagerAndAdapter(mRecyclerView,mAdapter);
        mRecyclerView.addItemDecoration(DividerItemDecoration.newVertical(this,
                R.dimen.list_divider_height,R.color.divider_color));
        scrollListener = new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);
                //check for scroll down
                if (mAdapter.getModelSize() == 0) {
                    return;
                }
                if (dy > 0) {
//                    LinearLayoutManager mLayoutManager = (LinearLayoutManager)
//                            rvContent.getLayoutManager();
//                    int visibleItemCount = mLayoutManager.getChildCount();
//                    int totalItemCount = mLayoutManager.getItemCount();
//                    int pastVisibleItems = mLayoutManager.findFirstVisibleItemPosition();
//                    if (!isLast && !loading) {
//                        if ((visibleItemCount + pastVisibleItems) >= totalItemCount) {
//                            loading = true;
//                            adapter.showFooter();
//                            requestContributes();
//                        }
//                    }
                }
            }
        };

        mRecyclerView.addOnScrollListener(scrollListener);
    }

    private void requestContributes() {
        Observable<List<Contributor>> observable = mGitHubApi.contributors();
        observable.onErrorResumeNext(refreshTokenAndRetry(observable))
                .subscribeOn(Schedulers.immediate())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<Contributor>>() {
                    @Override
                    public void onCompleted() {
                        hideLoadingDialog();
                    }

                    @Override
                    public void onError(Throwable e) {
                        hideLoadingDialog();
                        e.printStackTrace();
                        loading = false;
                        tvTip.setText(e.getClass().getName() + "\n" + e.getMessage());
                        NetErrorType.ErrorType errorType = NetErrorType.getErrorType(e);
                        tvTip.append("\n" + errorType.msg);

                    }

                    @Override
                    public void onNext(List<Contributor> contributors) {
                        mAdapter.appendItems(contributors);
                    }
                });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        mRecyclerView.removeOnScrollListener(scrollListener);
    }

    public Observable<AuthToken> refreshToken(){
        return Observable.create(new Observable.OnSubscribe<AuthToken>() {
            @Override
            public void call(Subscriber<? super AuthToken> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()){
                        subscriber.onNext(mGitHubApi.refreshToken());
                        subscriber.onCompleted();
                    }
                } catch (Exception e){
                    subscriber.onError(e);
                }
            }
        }).subscribeOn(Schedulers.io());
    }

    private <T> Func1<Throwable, ? extends Observable<? extends T>> refreshTokenAndRetry(final Observable<T> toBeResumed){
        return new Func1<Throwable, Observable<? extends T>>() {
            @Override
            public Observable<? extends T> call(Throwable throwable) {
                throwable.printStackTrace();
                if (isHttp401Error(throwable)){
                    return refreshToken().flatMap(new Func1<AuthToken, Observable<? extends T>>() {
                        @Override
                        public Observable<? extends T> call(AuthToken authToken) {
                            return toBeResumed;
                        }
                    });
                }

                return Observable.error(throwable);
            }

            public boolean isHttp401Error(Throwable throwable) {
                return throwable instanceof AccessDenyException;
            }

        };
    }

}
