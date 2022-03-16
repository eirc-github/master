package com.android.until;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.test.R;

public class LoadingDialog extends BaseDialog{
    DialogInterface.OnCancelListener onCancelListener;
    Button btnCancel;
    TextView tvMessage;
    String message;

    public LoadingDialog(){
        super(R.layout.dialog_loading);
        this.setCancelable(false);
    }

    public static LoadingDialog getInstance(Context context){
        LoadingDialog loadingDialog = new LoadingDialog();
        loadingDialog.setContext(context);
        return loadingDialog;
    }

    @Override
    public void setTitle(String title) {

    }

    @Override
    public void setMessage(String message) {
        this.message = message;

        if(tvMessage != null) {
            tvMessage.setText(message);
        }
    }


    public LoadingDialog setLDisplayMessage(String message) {
        setMessage(message);
        return this;
    }

    public LoadingDialog setCancel(Boolean cancelable){
        this.setCancelable(cancelable);
        return this;
    }

    public LoadingDialog setOnCancelListener(DialogInterface.OnCancelListener listener){
        onCancelListener = listener;
        return this;
    }

    @Override
    public void initView() {
        tvMessage = bindView(R.id.tvMessage);
        btnCancel = bindView(R.id.btnCancel);
        if (this.message != null) {
            tvMessage.setText(this.message);
        }
        if(this.isCancelable()){
            btnCancel.setVisibility(View.VISIBLE);
        }else {
            btnCancel.setVisibility(View.GONE);
        }
    }

    @Override
    public void initEvent() {
        final Dialog dialog = getDialog();
        if(dialog != null){
            try{
                btnCancel.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.cancel();
                    }
                });
            }catch (Exception e){
                Log.e("loadingDialog",e.getMessage());
            }

        }
    }


    @Override
    public void onStart() {
        super.onStart();
        if(onCancelListener != null){
            getDialog().setOnCancelListener(onCancelListener);
        }
    }

    @Override
    public void show() throws Throwable {
        super.showAllowingStateLoss();
    }

    @Override
    public void dismiss() {
        try {
            super.dismissAllowingStateLoss();
        }catch (Exception ignored) {

        }
    }
}
