package com.android.test;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.adapter.MainAdapter;
import com.android.model.Memes;
import com.android.model.ResultModel;
import com.android.test.databinding.ActivityMainBinding;
import com.android.until.LoadingDialog;
import com.android.until.OnRecyclerItemClickListener;
import com.android.viewmodel.MemeViewModel;

import java.util.List;

public class MainActivity extends AppCompatActivity {



    private MemeViewModel memeViewModel;
    private ActivityMainBinding activityMainBinding;
    private LoadingDialog showLoad;
    private MainAdapter mainAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        init();
        initObserve();

    }

    public void init(){
        memeViewModel = new ViewModelProvider(this).get(MemeViewModel.class);
        activityMainBinding.setMemeViewModel(memeViewModel);
        activityMainBinding.setLifecycleOwner(this);


        // 設置格線
        activityMainBinding.recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this, RecyclerView.VERTICAL,false));
        activityMainBinding.recyclerView.addItemDecoration(new DividerItemDecoration(MainActivity.this,DividerItemDecoration.VERTICAL));

        activityMainBinding.recyclerView.addOnItemTouchListener(new OnRecyclerItemClickListener(activityMainBinding.recyclerView) {
            @Override
            public void onItemClick(RecyclerView.ViewHolder viewHolder, int position) {
                    Memes s = mainAdapter.getPosition(position);
                Intent intent = new Intent(MainActivity.this,SecondActivity.class);
                intent.putExtra("Memes", s);
                startActivity(intent);
            }

            @Override
            public void onLongClick(RecyclerView.ViewHolder viewHolder, int position) {

            }
        });
        downloadData();
    }


    public void initObserve(){
        memeViewModel.download.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                Log.e("observe",s);
                memeViewModel.insertDb(s);
            }
        });

        memeViewModel.insetDB.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if(aBoolean){
                    memeViewModel.initAdapter();
                }
            }
        });

        memeViewModel.initData.observe(this, new Observer<ResultModel<List<Memes>>>() {
            @Override
            public void onChanged(ResultModel<List<Memes>> listResultModel) {
                mainAdapter = new MainAdapter(listResultModel.data);
                activityMainBinding.recyclerView.setAdapter(mainAdapter);
                mainAdapter.notifyDataSetChanged();
                showLoad.dismiss();
            }
        });

        memeViewModel.searchMsg.observe(this, new Observer<ResultModel<List<Memes>>>() {
            @Override
            public void onChanged(ResultModel<List<Memes>> listResultModel) {
                mainAdapter.clear();
                mainAdapter.addAll(listResultModel.data);
            }
        });
    }

    ///範列讀取條
    public void downloadData(){
        showLoad = LoadingDialog.getInstance(MainActivity.this).setLDisplayMessage("下載中，請稍後...").setCancel(true);

        try {
            showLoad.setOnCancelListener(new DialogInterface.OnCancelListener() {
                @Override
                public void onCancel(DialogInterface dialogInterface) {
                    showLoad.dismiss();
                }
            }).show();
        } catch (Throwable throwable) {
            throwable.printStackTrace();
            Log.e("dailog",throwable.getMessage());
        }
        memeViewModel.download();
    }

    @Override
    protected void onResume() {
        super.onResume();

        memeViewModel.initAdapter();
    }
}