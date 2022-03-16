package com.android;

import android.app.Application;

import com.android.db.MasterDataBase;
import com.android.until.PathUtil;

public class StartApplication extends Application {

    public static StartApplication instance;
    public MasterDataBase masterDataBase;

    public PathUtil pathUtil;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;

        pathUtil = new PathUtil(this);
        //建立目錄
        pathUtil.createExternalFolders();

        //建立主檔資料庫
        String preferencePath = pathUtil.getExternalFile(PathUtil.FolderType.Database,
                "MainDatabase.db").getAbsolutePath();
        masterDataBase = MasterDataBase.getInstance(this, preferencePath);
    }
}
