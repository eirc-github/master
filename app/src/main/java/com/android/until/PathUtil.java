package com.android.until;

import android.content.Context;
import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Environment;
import android.util.Log;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

public class PathUtil {
    private final Context context;
    public PathUtil(Context context){
        this.context = context;
    }

    public enum FolderType {
        Master("master"),
        Download("download"),
        Upload("upload"),
        Database("database"),
        Log("log"),
        Backup("backup"),
        Root("");

        private String folder;


        FolderType(String folder){
            this.folder = folder;
        }
    }


    public File getExternalFolder(FolderType folderType) {
        return new File(context.getExternalFilesDir(folderType.folder.toLowerCase()).toURI());
    }


    public File getExternalFile(FolderType folderType , String fileName){
        return new File(getExternalFolder(folderType).getAbsolutePath() + File.separator + fileName);
    }

    public void createExternalFolders() {
        int len = FolderType.values().length;
        for(int i=0; i < len ;i++){
            FolderType folder = FolderType.values()[i];
            File folderPath = getExternalFolder(folder);
            if(!folderPath.exists()){
                folderPath.mkdir();
            }
        }
    }

    public File getExternalStorageFolder() {
        return Environment.getExternalStorageDirectory();
    }


    public Boolean write(String path,String content){
        try {
            File file = new File(path);
            FileUtils.write(file, content, Charset.defaultCharset());
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String read(String path){
        File file = new File(path);
        try {
            return FileUtils.readFileToString(file, StandardCharsets.UTF_8);
        } catch (IOException e) {
            return e.getMessage();
        }
    }


    public void delete(String path) {
        try {
            File file = new File(path);
            if(file.exists()){
                FileUtils.forceDelete(file);
            }
        } catch (Exception e) {
        }
    }


    public Boolean copy(String srcPath, String dstPath) {

        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        try {
            if (!srcFile.exists()) {
                return false;
            }
            FileUtils.copyFile(srcFile, dstFile);
            return dstFile.exists();
        } catch (Exception e) {
            return false;
        }

    }

    public Boolean move(String srcPath,String dstPath){

        File srcFile = new File(srcPath);
        File dstFile = new File(dstPath);
        try {
            if (!srcFile.exists()) {
                return false;
            }
            FileUtils.moveFile(srcFile, dstFile);
            return dstFile.exists();
        } catch (Exception e) {
            return false;
        }


    }

    /**
     * 掃描記憶卡檔案，讓電腦看的到最新資料
     */
    private void scanFile(String storageFile) {
        MediaScannerConnection.scanFile(context, new String[]{storageFile}, null, new MediaScannerConnection.MediaScannerConnectionClient() {
            @Override
            public void onMediaScannerConnected() {

            }

            @Override
            public void onScanCompleted(String s, Uri uri) {
                Log.i("ExternalStorage", "Scanned " + s + ":");
                Log.i("ExternalStorage", "-> uri=" + uri);
            }
        });
    }


    /**
     * 掃描記憶卡檔案，讓電腦看的到最新資料
     */
    public void mediaScan(String storageDir) {
        File file = new File(storageDir);
        try{
            if (file.isDirectory()) {
                Log.i("ExternalStorage", "directory:" + file.getCanonicalPath());
                File[] files = file.listFiles();
                if(files != null){
                    for(int i=0; file.length() < i; i++) {
                        mediaScan(files[i].getAbsolutePath());
                    }
                }
            } else {
                Log.i("ExternalStorage", "file:" + file.getCanonicalPath());
                scanFile(file.getAbsolutePath());
            }
        }catch (Exception e){
            Log.i("ExternalStorage", e.getMessage());
        }

    }
}
