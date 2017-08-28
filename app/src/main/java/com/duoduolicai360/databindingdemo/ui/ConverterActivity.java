package com.duoduolicai360.databindingdemo.ui;

import android.databinding.BindingConversion;
import android.databinding.DataBindingUtil;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ConverterBinding;
import com.duoduolicai360.databindingdemo.event.UserFollowEvent;
import com.duoduolicai360.databindingdemo.model.User;

/**
 * Created by swg on 2017/8/28.
 */

public class ConverterActivity extends BaseActivity implements UserFollowEvent {

    private User mUser;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ConverterBinding binding  = DataBindingUtil.setContentView(this, R.layout.activity_converter);
        mUser = new User();
        binding.setUser(mUser);
        binding.setEvent(this);
    }

    @Override
    public void follow(View view) {
        mUser.setIsFollow(true);
    }

    @Override
    public void unFollow(View view) {
        mUser.setIsFollow(false);
    }

    //Note:最新版本的 dataBinding插件 在设置background 会自动把color转成ColorDrawable
    //所以不需要以下转换方法,如果创建了该方法,系统则会调用.
    @BindingConversion
    public static ColorDrawable convertColorToDrawable(int color){
        Log.d("BindingConversion", "convertColorToDrawable:" + color);
        return new ColorDrawable(color);
    }

}
