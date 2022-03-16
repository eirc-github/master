package com.android.adapter;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public abstract class BaseAdapter<U> extends RecyclerView.Adapter<BaseHolder>{

    private int ResId;
    private int BrId;
    List<U> dataList;
    public BaseAdapter(List<U> dataList, int resId, int brId) {
        this.ResId = resId;
        this.BrId = brId;
        this.dataList = dataList;
    }


    @Override
    public void onBindViewHolder(@NonNull BaseHolder holder, int position) {
        if (getItemCount() > 0 && getItemCount() > position && position >= 0) {
            U item = dataList.get(position);
            holder.dataBinding.setVariable(BrId,item);
            holder.dataBinding.executePendingBindings();
        }
    }

    @NonNull
    @Override
    public BaseHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        ViewDataBinding binding = DataBindingUtil.inflate(layoutInflater,ResId,parent,false);
        return new BaseHolder(binding);
    }

    @Override
    public int getItemCount() {
        return dataList.size();
    }
}
