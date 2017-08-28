package com.duoduolicai360.databindingdemo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import com.duoduolicai360.databindingdemo.BR;
import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ItemSearchBinding;

/**
 * Created by swg on 2017/8/28.
 */

public class SearchAdapter extends BaseAdapter<String> {

    public SearchAdapter(Context context) {
        super(context);
    }

    @Override
    public int getMyItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        ItemSearchBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_search,parent,false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ItemViewHolder itemViewHolder = (ItemViewHolder) viewHolder;
        String value = getModel(position);
        itemViewHolder.getBinding().setVariable(BR.value, value);
        itemViewHolder.getBinding().executePendingBindings();
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder{

        private ItemSearchBinding mBinding;

        public ItemViewHolder(ItemSearchBinding binding) {
            super(binding.getRoot());
            this.mBinding = binding;
        }

        public ItemSearchBinding getBinding() {
            return mBinding;
        }
    }

}
