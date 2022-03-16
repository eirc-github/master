package com.android.repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.Callable;

public class DownLoadData implements Callable<String> {

    @Override
    public String call() throws Exception {
        String url = "https://api.imgflip.com/get_memes";
        InputStream is = null;
        String result = null;
        try {
            is = new URL(url).openStream();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8)); //避免中文亂碼問題
            StringBuilder sb = new StringBuilder();
            int cp;
            while ((cp = rd.read()) != -1) {
                sb.append((char) cp);
            }
            result = sb.toString();

        } catch (IOException e) {
            e.printStackTrace();
        }
        return result;
    }
}