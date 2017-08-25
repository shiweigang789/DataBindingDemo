package com.duoduolicai360.databindingdemo;

import android.app.Application;
import android.os.StrictMode;

import com.duoduolicai360.databindingdemo.utils.CrashHandler;

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
        if (BuildConfig.DEBUG){
            StrictMode.setThreadPolicy(new StrictMode.ThreadPolicy.Builder()
            .detectDiskReads()
            .detectDiskWrites()
            .detectNetwork()
            .penaltyLog()
            .build());
            StrictMode.setVmPolicy(new StrictMode.VmPolicy.Builder()
            .detectLeakedSqlLiteObjects()
            .detectLeakedClosableObjects()
            .penaltyLog()
            .penaltyDeath()
            .build());
        }
        super.onCreate();
        mInstance = this;
        CrashHandler.getInstance().init(this, "mvvm_crash_log");
    }
}
