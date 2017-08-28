package com.duoduolicai360.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.adapter.SearchAdapter;
import com.duoduolicai360.databindingdemo.databinding.SearchDebounceBinding;
import com.duoduolicai360.databindingdemo.http.ApiServiceFactory;
import com.duoduolicai360.databindingdemo.http.SearchApi;
import com.duoduolicai360.databindingdemo.utils.DividerItemDecoration;
import com.duoduolicai360.databindingdemo.utils.RecyclerViewUtil;
import com.jakewharton.rxbinding.widget.RxTextView;
import com.jakewharton.rxbinding.widget.TextViewTextChangeEvent;

import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Action1;
import rx.schedulers.Schedulers;

/**
 * Created by swg on 2017/8/28.
 */

public class SearchDebounceActivity extends BaseActivity {

    private SearchApi mSearchApi = ApiServiceFactory.createService(SearchApi.class);
    private SearchAdapter mAdapter;
    private Subscription mSubscription;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        SearchDebounceBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_search_debounce);

        mAdapter = new SearchAdapter(this);
        RecyclerViewUtil.setLinearManagerAndAdapter(binding.recyclerView, mAdapter);
        binding.recyclerView.addItemDecoration(DividerItemDecoration.newVertical(this,
                R.dimen.list_divider_height, R.color.divider_color));
        //===========================@TODO
        //1,避免EditText没改变一次就请求一次.
        //2,避免频繁的请求,多个导致结果顺序错乱,最终的结果也就有问题.

        // 但是对于第二个问题,也不能彻底的解决. 比如停止输入400毫秒后,
        // 那么肯定会开始请求Search接口, 但是用户又会输入新的关键字,
        // 这个时候上个请求还没有返回, 新的请求又去请求Search接口.
        // 这个时候有可能最后的一个请求返回, 第一个请求最后返回,导致搜索结果不是想要的.
        //===========================@TODO

        mSubscription = RxTextView.textChangeEvents(binding.inputSearch)
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(getSearchObserver());


    }

    private Observer<TextViewTextChangeEvent> getSearchObserver(){
        return new Observer<TextViewTextChangeEvent>() {

            @Override
            public void onCompleted() {
                Log.d("getSearchObserver", "--------- onComplete");
            }

            @Override
            public void onError(Throwable e) {
                Log.d("getSearchObserver", e.getMessage());
            }

            @Override
            public void onNext(TextViewTextChangeEvent onTextChangeEvent) {
                String key = onTextChangeEvent.text().toString();
                Log.d("getSearchObserver", "--------- onNext:" + key);
                if (TextUtils.isEmpty(key)) {
                    mAdapter.removeAll();
                    return;
                }
                mSearchApi.search(key)
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Action1<List<String>>() {
                            @Override
                            public void call(List<String> strings) {
                                mAdapter.removeAll();
                                mAdapter.appendItems(strings);
                            }
                        }, new Action1<Throwable>() {
                            @Override
                            public void call(Throwable throwable) {
                                throwable.printStackTrace();
                            }
                        });
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (!mSubscription.isUnsubscribed()){
            mSubscription.unsubscribe();
        }
    }
}
