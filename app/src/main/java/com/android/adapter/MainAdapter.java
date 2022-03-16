package com.android.adapter;

import androidx.databinding.library.baseAdapters.BR;

import com.android.model.Memes;
import com.android.test.R;

import java.util.List;

public class MainAdapter extends BaseAdapter<Memes>{

    List<Memes> list;
    public MainAdapter(List<Memes> dataList) {
        super(dataList, R.layout.main_item, BR.memesModel);
        list = dataList;
    }

    public void clear() {
        this.list.clear();
        this.notifyDataSetChanged();
    }


    public void addAll(List<Memes> mutableList) {
        this.list.addAll(mutableList);
        this.notifyDataSetChanged();
    }

    public void add(Memes model){
        this.list.add(model);
        this.notifyItemInserted(this.list.size() - 1);
        this.notifyDataSetChanged();
    }

    public Memes getPosition(int position){
        return list.get(position);
    }



}
