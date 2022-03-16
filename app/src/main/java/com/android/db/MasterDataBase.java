package com.android.db;

import android.app.Application;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.android.db.dao.MemesDao;
import com.android.db.entity.MemesEntity;

@Database(entities = {MemesEntity.class},version = 1)
public abstract class MasterDataBase extends RoomDatabase {

    private static MasterDataBase instance;


    public static MasterDataBase getInstance(Application app, String path) {
        if(instance == null){
            instance = Room.databaseBuilder(app.getApplicationContext(), MasterDataBase.class, path)
                    .allowMainThreadQueries()
//                .addMigrations(MIGRATION_1_2)
                    .fallbackToDestructiveMigration()
                    .setJournalMode(JournalMode.TRUNCATE)
                    .build();
        }
        return instance;
    }



    public MasterDataBase newInstance(Application app, String path){
        if (instance.isOpen()){
            instance.close();
        }
        instance = null;
        return getInstance(app,path);
    }

    public abstract MemesDao memesDao();
}
