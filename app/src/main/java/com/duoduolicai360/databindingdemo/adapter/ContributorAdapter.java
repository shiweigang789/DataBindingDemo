package com.duoduolicai360.databindingdemo.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.duoduolicai360.databindingdemo.BR;
import com.duoduolicai360.databindingdemo.R;
import com.duoduolicai360.databindingdemo.databinding.ItemContributorBinding;
import com.duoduolicai360.databindingdemo.model.Contributor;
import com.squareup.picasso.Picasso;

/**
 * Created by swg on 2017/8/28.
 */

public class ContributorAdapter extends BaseAdapter<Contributor> {

    public ContributorAdapter(Context context) {
        super(context);
    }

    @Override
    public int getMyItemViewType(int position) {
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onMyCreateViewHolder(ViewGroup parent, int viewType) {
        ItemContributorBinding binding = DataBindingUtil.inflate(mInflater, R.layout.item_contributor,parent,false);
        ContributorViewHolder holder = new ContributorViewHolder(binding.getRoot());
        holder.setBinding(binding);
        return holder;
    }

    @Override
    public void onMyBindViewHolder(RecyclerView.ViewHolder viewHolder, int position) {
        ContributorViewHolder holder = (ContributorViewHolder) viewHolder;
        ItemContributorBinding binding = holder.getBinding();
        Contributor contributor = getModel(position);
        binding.setVariable(BR.contributor,contributor);
        binding.executePendingBindings();
        Picasso.with(mContext)
                .load(contributor.getAvatar_url())
                .into(binding.ivAvatar);
    }

    public class ContributorViewHolder extends RecyclerView.ViewHolder{

        private ItemContributorBinding mBinding;

        public ContributorViewHolder(View itemView) {
            super(itemView);
        }

        public ItemContributorBinding getBinding() {
            return mBinding;
        }

        public void setBinding(ItemContributorBinding binding) {
            mBinding = binding;
        }
    }



}
