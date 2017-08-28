package com.duoduolicai360.databindingdemo.event;

import android.view.View;

/**
 * Created by swg on 2017/8/28.
 */

public interface UserFollowEvent {
    void follow(View view);
    void unFollow(View view);
}
