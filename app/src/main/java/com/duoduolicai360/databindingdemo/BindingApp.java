package com.duoduolicai360.databindingdemo;

import android.app.Application;

/**
 * Created by swg on 2017/8/24.
 */

public class BindingApp extends Application {

    public static BindingApp mInstance;

    public static BindingApp getmInstance() {
        return mInstance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mInstance = this;
    }
}
