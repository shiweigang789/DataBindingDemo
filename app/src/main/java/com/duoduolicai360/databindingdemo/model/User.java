package com.duoduolicai360.databindingdemo.model;

import android.databinding.BaseObservable;
import android.databinding.Bindable;

import com.duoduolicai360.databindingdemo.BR;

/**
 * Created by swg on 2017/8/25.
 */

public class User extends BaseObservable{

    private String userName;
    private String realName;
    private String mobile;
    private int age;

    private boolean isFollow;

    public User(String realName, String mobile){
        this.realName = realName;
        this.mobile = mobile;
    }

    public User(){}

    @Bindable
    public boolean isFollow(){
        return isFollow;
    }

    public void setIsFollow(boolean isFollow){
        this.isFollow = isFollow;
        notifyPropertyChanged(BR.follow);
    }

    @Bindable
    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
        notifyPropertyChanged(BR.userName);
    }

    @Bindable
    public String getRealName() {
        return realName;
    }

    public void setRealName(String realName) {
        this.realName = realName;
        notifyPropertyChanged(BR.realName);
    }

    @Bindable
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
        notifyPropertyChanged(BR.mobile);
    }

    @Bindable
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
        notifyPropertyChanged(BR.age);
    }
}
