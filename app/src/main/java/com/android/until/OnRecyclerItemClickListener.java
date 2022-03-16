package com.android.until;


import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.core.view.GestureDetectorCompat;
import androidx.recyclerview.widget.RecyclerView;

//TODO 觸控監聽
public abstract class OnRecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
    private GestureDetectorCompat OnLongClick;//手勢探測器 長壓
    private RecyclerView recyclerView;
    private GestureDetectorCompat SingleTapUp; //點擊

    public OnRecyclerItemClickListener(RecyclerView recyclerView) {
        this.recyclerView = recyclerView;
        OnLongClick = new GestureDetectorCompat(recyclerView.getContext(),
                new ItemTouchHelperGestureListener());
        SingleTapUp = new GestureDetectorCompat(recyclerView.getContext(),
                new ItemClickTouchHelperGestureListener());
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView rv, MotionEvent e) {
        OnLongClick.onTouchEvent(e);
        SingleTapUp.onTouchEvent(e);
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView rv, MotionEvent e) {
        OnLongClick.onTouchEvent(e);
        SingleTapUp.onTouchEvent(e);
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean disallowIntercept) {
    }

    public abstract void onItemClick(RecyclerView.ViewHolder viewHolder,int position);

    public abstract void onLongClick(RecyclerView.ViewHolder viewHolder,int position);

    //TODO 長壓螢幕超過一定時間觸發
    private class ItemTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public void onLongPress(MotionEvent e) {
            View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childViewUnder);
                int position = recyclerView.getChildAdapterPosition(childViewUnder);
                onLongClick(childViewHolder,position);
            }
        }
    }

    //TODO 點擊觸發
    private class ItemClickTouchHelperGestureListener extends GestureDetector.SimpleOnGestureListener {
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            View childViewUnder = recyclerView.findChildViewUnder(e.getX(), e.getY());
            if (childViewUnder != null) {
                RecyclerView.ViewHolder childViewHolder = recyclerView.getChildViewHolder(childViewUnder);
                int position = recyclerView.getChildAdapterPosition(childViewUnder);
                onItemClick(childViewHolder,position);
            }
            return super.onSingleTapUp(e);
        }
    }
}
