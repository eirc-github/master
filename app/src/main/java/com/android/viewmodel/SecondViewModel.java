package com.android.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.databinding.ObservableField;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.android.model.Memes;
import com.android.model.ResultModel;
import com.android.repository.MemesRepository;

public class SecondViewModel extends AndroidViewModel {



    public ObservableField<Memes> memes;
    public MutableLiveData<ResultModel<Void>> saveMsg;
    public MemesRepository memesRepository;

    public void setMemes(Memes memes) {
        this.memes.set(memes);
        Log.e("test",this.memes.get().toString());
    }


    public SecondViewModel(@NonNull Application application) {
        super(application);
        init();
    }
    public void init(){
        memes = new ObservableField<>();
        saveMsg = new MutableLiveData<>();
        memesRepository = new MemesRepository();
    }

    public void confirm(){
        if(memes.get().remark != null){
            if(!memes.get().remark.isEmpty()){
                saveMsg.setValue(memesRepository.updateRemark(memes.get().remark,memes.get().id));
            }
        }

    }
}
