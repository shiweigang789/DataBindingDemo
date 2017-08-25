package com.duoduolicai360.databindingdemo.ui;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ActivityFontBindingBinding;

/**
 * Created by swg on 2017/8/24.
 */

public class FontBindingActivity extends AppCompatActivity {

    ActivityFontBindingBinding mFontBinding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mFontBinding = DataBindingUtil.setContentView(this, R.layout.activity_font_binding);
    }
}
