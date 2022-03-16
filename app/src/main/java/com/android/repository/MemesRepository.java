package com.android.repository;

import android.util.Base64;

import com.android.StartApplication;
import com.android.db.entity.MemesEntity;
import com.android.model.Memes;
import com.android.model.ResultModel;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Type;
import java.net.URL;
import java.util.List;

public class MemesRepository {


    //儲存資料庫
    public ResultModel<Void> insertDb(String sb){
        ResultModel<Void> memesResultModel = new ResultModel<>();
        Type listType = new TypeToken<List<Memes>>(){}.getType();
        //擷取第一段資料
        JSONObject jsonObject = null;
        try {
            jsonObject = new JSONObject(sb);
            String jsonArray = jsonObject.getString("data");
            //擷取第二段資料
            JSONObject jsonObject1 = new JSONObject(jsonArray);
            String jsonArray1 = jsonObject1.getString("memes");
            List<Memes> meme = new Gson().fromJson(jsonArray1, listType);
            for(int i = 0; i<meme.size();i++){
                MemesEntity memesEntity = new MemesEntity();
                memesEntity.id = meme.get(i).id;
                memesEntity.name = meme.get(i).name;
                memesEntity.url = getImageBytes(meme.get(i).url);
                memesEntity.height = String.valueOf(meme.get(i).height);
                memesEntity.width = String.valueOf(meme.get(i).width);
                memesEntity.box_count = String.valueOf(meme.get(i).box_count);
                memesEntity.remark = meme.get(i).remark;
                StartApplication.instance.masterDataBase.memesDao().insert(memesEntity);
            }
            memesResultModel.Msg = "成功";
            memesResultModel.Code = 0;
        } catch (JSONException | IOException e) {
            e.printStackTrace();
            memesResultModel.Msg = e.getMessage();
            memesResultModel.Code = -1;
        }
        return memesResultModel;


    }

    //轉成下載檔案轉成bas64
    private String getImageBytes(String imageUrl) throws IOException
    {
        URL url = new URL(imageUrl);

        ByteArrayOutputStream output = new ByteArrayOutputStream();

        try (InputStream stream = url.openStream())
        {
            byte[] buffer = new byte[4096];

            while (true)
            {
                int bytesRead = stream.read(buffer);
                if (bytesRead < 0) { break; }
                output.write(buffer, 0, bytesRead);
            }
        }
        return Base64.encodeToString(output.toByteArray(), Base64.DEFAULT);
    }


    public ResultModel<List<Memes>> all(){
        ResultModel<List<Memes>> memesResultModel = new ResultModel<>();
        try {

            List<Memes> memesList = StartApplication.instance.masterDataBase.memesDao().selectAll();
            memesResultModel.Msg = "成功";
            memesResultModel.data = memesList;
            memesResultModel.Code = 0;
            return memesResultModel;
        }catch (Exception e){
            memesResultModel.Msg = e.getMessage();
            memesResultModel.data = null;
            memesResultModel.Code = -1;
            return memesResultModel;
        }
    }


    //搜尋條碼
    public ResultModel<List<Memes>> search(String id){
        ResultModel<List<Memes>> memesResultModel = new ResultModel<>();
        try {

            List<Memes> memes = StartApplication.instance.masterDataBase.memesDao().searchById(id);
            memesResultModel.Msg = "成功";
            memesResultModel.data = memes;
            memesResultModel.Code = 0;
            return memesResultModel;
        }catch (Exception e){
            memesResultModel.Msg = e.getMessage();
            memesResultModel.data = null;
            memesResultModel.Code = -1;
          return memesResultModel;
        }

    }

    //更新Remark
    public ResultModel<Void> updateRemark(String remark,String id){
        ResultModel<Void> memesResultModel = new ResultModel<>();
        try {
            StartApplication.instance.masterDataBase.memesDao().upDate(remark,id);
            memesResultModel.Msg = "成功";
            memesResultModel.Code = 0;
            return memesResultModel;
        }catch (Exception e){
            memesResultModel.Msg = e.getMessage();
            memesResultModel.Code = -1;
            return memesResultModel;
        }
    }





}
