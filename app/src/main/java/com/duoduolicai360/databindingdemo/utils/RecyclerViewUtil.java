package com.duoduolicai360.databindingdemo.utils;

import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

/**
 * Created by swg on 2017/8/28.
 */

public class RecyclerViewUtil {

    public static final int HORIZONTAL = LinearLayoutManager.HORIZONTAL;
    public static final int VERTICAL = LinearLayoutManager.VERTICAL;

    private RecyclerViewUtil(){}

    public static void setLinearManagerAndAdapter(RecyclerView recyclerView,
                                                  RecyclerView.Adapter adapter) {
        setLinearManagerAndAdapter(recyclerView, adapter, LinearLayoutManager.VERTICAL);
    }

    public static void setLinearManagerAndAdapter(RecyclerView recyclerView,
                                                  RecyclerView.Adapter adapter, int orientation) {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(recyclerView.getContext());
        linearLayoutManager.setOrientation(orientation);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);
    }


    public static void setGridManagerAndAdapter(RecyclerView recyclerView,
                                                RecyclerView.Adapter adapter, int spanCount) {
        GridLayoutManager gridLayoutManager = new GridLayoutManager(recyclerView.getContext(), spanCount);
        gridLayoutManager.setOrientation(VERTICAL);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(gridLayoutManager);
        recyclerView.setAdapter(adapter);
    }

}
