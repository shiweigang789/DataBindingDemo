package com.duoduolicai360.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.SparseArray;
import android.view.View;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ActivityElBinding;
import com.duoduolicai360.databindingdemo.event.UserFollowEvent;
import com.duoduolicai360.databindingdemo.model.User;

/**
 * Created by swg on 2017/8/28.
 */

public class ELActivity extends BaseActivity implements UserFollowEvent {

    private ActivityElBinding mBinding;
    public User mUser;
    public int index;
    public SparseArray<String> mSparseArray = new SparseArray<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_el);
        mUser = new User();
        mUser.setUserName("Johnny");
        mUser.setRealName(null);
        mBinding.setUser(mUser);

        mSparseArray.put(0, "one");
        mSparseArray.put(1, "two");
        mSparseArray.put(2, "three");

        mBinding.setIndex(index);
        mBinding.setSparse(mSparseArray);
        mBinding.setEvent(this);

    }

    public void collectionSample(View view) {
        if (index >= mSparseArray.size() - 1) {
            index = 0;
        } else {
            index++;
        }
        mBinding.setIndex(index);
    }

    public void coalescingSample(View view) {
        if (mUser.getRealName() != null) {
            mUser.setRealName(null);
            mUser.setUserName("Johnny");
        } else {
            mUser.setRealName("张三");
            mUser.setUserName(null);
        }
    }

    @Override
    public void follow(View view) {
        mUser.setIsFollow(true);
    }

    @Override
    public void unFollow(View view) {
        mUser.setIsFollow(false);
    }
}
