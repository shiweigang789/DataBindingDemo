package com.duoduolicai360.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.databinding.ObservableArrayMap;
import android.databinding.ObservableMap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ActivityUpdateUserBinding;
import com.duoduolicai360.databindingdemo.model.User;
import com.duoduolicai360.databindingdemo.model.UserField;

/**
 * Created by swg on 2017/8/25.
 */

public class UpdateUserActivity extends BaseActivity {

    private ActivityUpdateUserBinding mBinding;
    private User mUser;
    private UserField mUserField = new UserField();
    private ObservableMap<String, Object> mMap = new ObservableArrayMap<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_update_user);
        mUser = new User("Chiclaim", "119");
        mUserField.realName.set("Chiclaim");
        mUserField.mobile.set("119");
        mMap.put("realName", "Chiclaim");
        mMap.put("mobile", "119");
        mBinding.setUser(mUser);
        mBinding.setFields(mUserField);
        mBinding.setCollection(mMap);
    }

    //如果(某个)字段发生变化.
    //1,通过User继承BaseObservable实现
    //2,通过ObservableField方式实现
    //3,通过Observable Collections的方式 如:ObservableArrayMap
    //4,当然可以通过binding.setUser(user) [相当于所有的View重新设置一遍]
    public void updateNameByPOJP(View view) {

        if ("Johnny".equals(mUser.getRealName())) {
            mUser.setRealName("Chiclaim");
            mUser.setMobile("110");
        } else {
            mUser.setRealName("Johnny");
            mUser.setMobile("119");
        }
        //当然可以通过binding.setUser(user)
//        binding.setUser(mUser);
    }

    public void updateNameByField(View view) {
        if ("Johnny".equals(mUserField.realName.get())) {
            mUserField.realName.set("Chiclaim");
            mUserField.mobile.set("110");
        } else {
            mUserField.realName.set("Johnny");
            mUserField.mobile.set("119");
        }
    }

    public void updateNameByCollection(View view) {
        if ("Johnny".equals(mMap.get("realName"))) {
            mMap.put("realName", "Chiclaim");
            mMap.put("mobile", "110");
        } else {
            mMap.put("realName", "Johnny");
            mMap.put("mobile", "119");
        }
    }

}
