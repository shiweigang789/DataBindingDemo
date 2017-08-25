package com.duoduolicai360.databindingdemo.utils;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Typeface;
import android.util.ArrayMap;

/**
 * Created by swg on 2017/8/24.
 */

public class FontCache {

    @SuppressLint("NewApi")
    private static ArrayMap<String, Typeface> fontCache = new ArrayMap<String, Typeface>();

    public static Typeface getTypeface(String fontName, Context context){
        Typeface typeface = fontCache.get(fontName);
        if (typeface == null){
            try {
                typeface = Typeface.createFromAsset(context.getAssets(), fontName);
            } catch (Exception e){
                return null;
            }
            fontCache.put(fontName,typeface);
        }
        return typeface;
    }

}
