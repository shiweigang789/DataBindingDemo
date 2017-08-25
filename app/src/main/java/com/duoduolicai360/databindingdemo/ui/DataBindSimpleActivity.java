package com.duoduolicai360.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ActivitySimpleBinding;
import com.duoduolicai360.databindingdemo.model.User;

/**
 * Created by swg on 2017/8/25.
 */

public class DataBindSimpleActivity extends BaseActivity {

    private ActivitySimpleBinding mBinding;
    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_simple);
        fetchData();
    }

    /**
     * 模拟获取数据
     */
    private void fetchData() {
        new AsyncTask<Void, Void, Void>() {

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
                showLoadingDialog();
            }

            @Override
            protected Void doInBackground(Void... params) {
                try {
                    Thread.sleep(2000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                return null;
            }

            @Override
            protected void onPostExecute(Void aVoid) {
                super.onPostExecute(aVoid);
                hideLoadingDialog();
                mUser = new User("Chiclaim", "13512341234");
                mBinding.setUser(mUser);
                //binding.setVariable(com.mvvm.BR.user, user);
            }
        }.execute();
    }

    public void changeInfo(View view){
        mUser.setRealName("zhangsan");
        mUser.setMobile("12222222222");
//        mUser.notifyPropertyChanged(BR._all);
    }

}
