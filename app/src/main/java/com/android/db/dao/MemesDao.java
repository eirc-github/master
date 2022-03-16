package com.android.db.dao;

import androidx.room.Dao;
import androidx.room.Query;

import com.android.db.entity.MemesEntity;
import com.android.model.Memes;

import java.util.List;

@Dao
public interface MemesDao extends BaseDao<MemesEntity> {

    @Query("select Id as id ," +
            " Name as name," +
            " Url as url , " +
            "Width as width , " +
            "Height as height , " +
            "BoxCount as box_count," +
            "Remark as remark from Memes")
    List<Memes> selectAll();


    @Query("select Id as id ,\n" +
            "Name as name,\n" +
            "Url as url ,\n" +
            "Width as width ,\n" +
            "Height as height , \n" +
            "BoxCount as box_count,\n" +
            "Remark as remark from Memes \n" +
            "where id =:id")
    List<Memes> searchById(String id);



    @Query("UPDATE Memes\n" +
            "SET Remark =:remark \n" +
            "WHERE id =:id;")
    void upDate(String remark,String id);

}
