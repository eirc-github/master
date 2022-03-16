package com.android.adapter;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Base64;
import android.widget.ImageView;

import java.io.ByteArrayInputStream;

public class BindingAdapter {

@androidx.databinding.BindingAdapter("url")
public static void bindUrl(ImageView view, String base64) {
    byte[] byteArray = Base64.decode(base64, Base64.DEFAULT);
    ByteArrayInputStream byStream = new ByteArrayInputStream(byteArray);
    Bitmap d = BitmapFactory.decodeStream(byStream);
    view.setImageBitmap(d);
}

}
