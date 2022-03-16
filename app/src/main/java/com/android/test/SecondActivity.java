package com.android.test;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.android.model.Memes;
import com.android.model.ResultModel;
import com.android.test.databinding.ActivitySecondBinding;
import com.android.viewmodel.SecondViewModel;

public class SecondActivity extends AppCompatActivity {


    private SecondViewModel secondViewModel;
    private ActivitySecondBinding activitySecondBinding;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        activitySecondBinding = DataBindingUtil.setContentView(this, R.layout.activity_second);

        init();

        Intent intent = getIntent();
        Memes person = (Memes) intent.getSerializableExtra("Memes");
        Log.e("",person.toString());


        secondViewModel.setMemes(person);


    }
    public void init(){
        secondViewModel = new ViewModelProvider(this).get(SecondViewModel.class);
        activitySecondBinding.setSecondViewModel(secondViewModel);
        activitySecondBinding.setLifecycleOwner(this);

        secondViewModel.saveMsg.observe(this, new Observer<ResultModel<Void>>() {
            @Override
            public void onChanged(ResultModel<Void> voidResultModel) {
                if (voidResultModel.Code == 0){
                    finish();
                }
            }
        });
    }
}
