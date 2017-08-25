package com.duoduolicai360.databindingdemo.utils;

import android.databinding.BindingAdapter;
import android.databinding.BindingConversion;
import android.graphics.Typeface;
import android.widget.TextView;

import com.duoduolicai360.databindingdemo.BindingApp;
import com.duoduolicai360.databindingdemo.R;

/**
 * Created by swg on 2017/8/24.
 */

public class FontBinding {

    @BindingConversion
    public static Typeface convertStringToFace(String fontName){
        try {
            return FontCache.getTypeface(fontName, BindingApp.getmInstance());
        } catch (Exception e){
            throw e;
        }
    }

    @BindingAdapter("android:text")
    public static void setText(TextView v, String s){
        v.setTypeface(convertStringToFace(BindingApp.getmInstance().getString(R.string.ruthie)));
        v.setText(s);
    }

}
