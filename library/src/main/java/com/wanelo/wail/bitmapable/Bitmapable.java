package com.wanelo.wail.bitmapable;

import android.graphics.Bitmap;

public interface Bitmapable {
    public void setBitmap(Bitmap bitmap);
    public boolean isValid(String url);
    public int getWidth();
    public int getHeight();

}
