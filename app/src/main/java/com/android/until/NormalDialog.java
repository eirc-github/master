package com.android.until;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.test.R;

public class NormalDialog extends BaseDialog{
    TextView textMessage;
    TextView textTitle;
    ImageView buttonClose;
    String message;
    String title;

    public NormalDialog(){
        super(R.layout.dialog_information);
        this.setCancelable(false);
    }

    public static NormalDialog getInstance(Context context){
        NormalDialog dialog = new NormalDialog();
        dialog.setContext(context);
        return dialog;
    }

    @Override
    public void setTitle(String title) {
        this.title = title;

        if(textTitle != null) {
            textTitle.setText(title);
        }
    }

    @Override
    public void setMessage(String message) {
        this.message = message;

        if(textMessage != null) {
            textMessage.setText(message);
        }
    }

    @Override
    public BaseDialog setContext(Context ctx) {
        super.setContext(ctx);
        return this;
    }

    @Override
    public void initView() {
        textMessage = bindView(R.id.text_message);
        textTitle = bindView(R.id.text_title);
        buttonClose = bindView(R.id.button_close);

        if (textMessage != null) {
            textMessage.setText(this.message);
        }

        if (textTitle != null) {
            textTitle.setText(this.title);
        }

    }

    @Override
    public void initEvent() {
        final Dialog dialog = getDialog();
        if(dialog != null){
            try{
                buttonClose.setOnClickListener(new View.OnClickListener() {
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
