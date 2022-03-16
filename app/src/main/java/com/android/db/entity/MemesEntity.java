package com.android.db.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import org.jetbrains.annotations.NotNull;


@Entity(tableName = "Memes" ,indices = { @Index(value = {"Id"})})
public class MemesEntity {
    @NotNull
    @PrimaryKey
    @ColumnInfo(name = "Id")
    public String id;
    @NotNull
    @ColumnInfo(name = "Name")
    public String name;
    @NotNull
    @ColumnInfo(name = "Url")
    public String url;
    @NotNull
    @ColumnInfo(name = "Width")
    public String width;
    @NotNull
    @ColumnInfo(name = "Height")
    public String height;
    @NotNull
    @ColumnInfo(name = "BoxCount")
    public String box_count;

    @ColumnInfo(name = "Remark")
    public String remark;
}
