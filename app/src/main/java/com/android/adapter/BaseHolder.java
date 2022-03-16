package com.android.adapter;

import androidx.databinding.ViewDataBinding;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class BaseHolder extends RecyclerView.ViewHolder{

    ViewDataBinding dataBinding;

    public BaseHolder(@NonNull ViewDataBinding dataBinding) {
        super(dataBinding.getRoot());
        this.dataBinding = dataBinding;
    }


}
