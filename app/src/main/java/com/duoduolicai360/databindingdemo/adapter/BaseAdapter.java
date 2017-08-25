package com.duoduolicai360.databindingdemo.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.duoduolicai360.databindingdemo.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Administrator on 2017/8/25.
 */

public abstract class BaseAdapter<T> extends RecyclerView.Adapter {

    protected List<T> mList = new ArrayList<>();
    public boolean mShowFooter;
    public boolean mShowHead;
    public final static int FOOTER_TYPE = 99;
    protected Context mContext;

    protected LayoutInflater mInflater;

    public BaseAdapter(Context context){
        this.mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    public static class UnKnownViewHolder extends RecyclerView.ViewHolder{

        public UnKnownViewHolder(View itemView) {
            super(itemView);
        }
    }

    public static class Footer extends RecyclerView.ViewHolder {
        public Footer(View itemView) {
            super(itemView);
        }
    }

    public T getModel(int position){
        if (mList.size() == 0){
            return null;
        }
        return mList.get(position);
    }

    public int getModelSize(){
        return mList.size();
    }

    public void showFooter(){
        hideHead();
        mShowFooter = true;
        notifyDataSetChanged();
    }

    public void hideFooter(){
        mShowFooter = false;
        notifyDataSetChanged();
    }

    public void hideHead(){
        mShowHead = false;
    }

    @Override
    public int getItemViewType(int position) {
        if (mShowFooter && position == getMyItemCount()){
            return FOOTER_TYPE;
        }
        return getMyItemViewType(position);
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
       switch (viewType){
           case FOOTER_TYPE:
               return new Footer(getLayout(R.layout.footer_loading_layout,parent));
       }
        return onCreateViewHolder(parent, viewType);
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        switch (getItemViewType(position)){
            case FOOTER_TYPE:
                break;
            default:
                onMyBindViewHolder(holder, mShowHead ? position - 1 : position);
                break;
        }
    }

    @Override
    public int getItemCount() {
        return getMyItemCount() + (mShowFooter ? 1 : 0) + (mShowHead ? 1 : 0);
    }

    /**
     * 替代getItemViewType
     *
     * @return
     */
    public int getMyItemCount() {
        return mList.size();
    }

    /**
     * 替代getItemViewType
     *
     * @param position
     * @return
     */
    public abstract int getMyItemViewType(int position);

    /**
     * 替代onCreateViewHolder
     *
     * @param parent
     * @param viewType
     * @return
     */
    public abstract RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType);

    /**
     * 替代onBindViewHolder
     *
     * @param viewHolder
     * @param position
     */
    public abstract void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position);

    public void appendItems(List<T> items){
        if (items == null || items.isEmpty()){
            return;
        }

        mList.addAll(items);
        notifyDataSetChanged();
    }

    public void appendItem(T item){
        if (item == null){
            return;
        }
        appendItems(Arrays.asList(item));
    }

    public void removeAll(){
        mList.clear();
        hideFooter();
        notifyDataSetChanged();
    }

    public View getLayout(int layoutId, ViewGroup parent) {
        return mInflater.inflate(layoutId, parent, false);
    }


    public RecyclerView.ViewHolder getUnKnownViewHolder(ViewGroup parent) {
        return new UnKnownViewHolder(getLayout(R.layout.item_unknown, parent));
    }

}
