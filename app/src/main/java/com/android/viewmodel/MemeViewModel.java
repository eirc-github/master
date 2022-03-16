package com.android.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.model.Memes;
import com.android.model.ResultModel;
import com.android.repository.DownLoadData;
import com.android.repository.MemesRepository;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

public class MemeViewModel extends AndroidViewModel {
    public MemesRepository memesRepository;
    public MutableLiveData<String> download;
    public MutableLiveData<Boolean> insetDB;
    public MutableLiveData<ResultModel<List<Memes>>> initData;
    public MutableLiveData<ResultModel<List<Memes>>> searchMsg;
    public ObservableField<String> editId;

    public MemeViewModel(@NonNull Application application) {
        super(application);
        initializeViewModel();
    }

    public void initializeViewModel(){
        memesRepository = new MemesRepository();
        download = new MutableLiveData<>();
        insetDB = new MutableLiveData<>();
        initData = new MutableLiveData<>();
        searchMsg = new MutableLiveData<>();
        editId = new ObservableField<>();
    }


    //下載資料
    public void download(){
        ExecutorService executor = Executors.newCachedThreadPool();
        DownLoadData task = new DownLoadData();
        FutureTask<String> futureTask = new FutureTask<String>(task);
        executor.submit(futureTask);
        executor.shutdown();
        try {
            String s = futureTask.get();
            download.setValue(s);
        } catch (ExecutionException | InterruptedException e) {
            e.printStackTrace();
            download.setValue(e.getMessage());
        } catch ( Exception e){
            e.getMessage();
            download.setValue(e.getMessage());
        }
    }

    ///下載到DB
    public void insertDb(String sb){
        Callable ca2 = new Callable() {
            @Override
            public String call() throws Exception {
                try {
                   ResultModel<Void> e = memesRepository.insertDb(sb);
                   if(e.Code == 0){
                       insetDB.postValue(true);
                   }
                }catch (Exception e){
                    e.printStackTrace();
                    return e.getMessage();
                }
                return "成功";
            }
        };
        FutureTask<String> futureTask = new FutureTask<String>(ca2);
        new Thread(futureTask).start();
    }


    public void initAdapter(){
        initData.setValue(memesRepository.all());
    }


    public void searchBtn(){
       if(editId.get() == null || editId.get().isEmpty()){
           searchMsg.setValue(memesRepository.all());
       }else{
           searchMsg.setValue(memesRepository.search(editId.get()));
       }
    }

}
