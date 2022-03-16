package com.android.until;

import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.IdRes;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentTransaction;

import com.android.test.R;


abstract public class BaseDialog extends DialogFragment {

    String TAG = this.getClass().getSimpleName();
    Context ctx;
    View view;
    public int layoutRes;

    public abstract void initView();

    public abstract void initEvent();

    public abstract void setTitle(String title);

    public abstract void setMessage(String message);

    public BaseDialog(int layoutRes){
        this.layoutRes = layoutRes;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        setStyle(DialogFragment.STYLE_NORMAL, R.style.AppTheme_Dialog_FullScreen);
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initView();
        initEvent();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(layoutRes,container,false);
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().getWindow().setBackgroundDrawable(null);
        getDialog().getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT);
        getDialog().getWindow().addFlags(View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);

    }

    protected <T extends View> T bindView(@IdRes int res){
        try{
            T View = getView().findViewById(res);
            return View;
        }catch (Exception e){
            Log.e("BaseDialog",e.getMessage());
            return null;
        }
    }


    public BaseDialog setContext(Context ctx){
        this.ctx = ctx;
        return this;
    }


    public void show() throws Throwable {
        if(((BaseDialog)this).ctx == null){
            throw (Throwable)(new NoSetContext());
        }else {
            this.show(((AppCompatActivity)ctx).getSupportFragmentManager(),TAG);
        }
    }


    public void showAllowingStateLoss() throws Throwable {
        if(((BaseDialog)this).ctx == null){
            throw (Throwable)(new NoSetContext());
        }else {
            FragmentTransaction ft = ((AppCompatActivity)ctx).getSupportFragmentManager().beginTransaction();
            ft.add(this,TAG);
            ft.commitAllowingStateLoss();
        }
    }

    public class NoSetContext extends Exception{
        public NoSetContext(){
            super("no set context, need to call setContext(ctx:Context) to set context");
        }
    }



}