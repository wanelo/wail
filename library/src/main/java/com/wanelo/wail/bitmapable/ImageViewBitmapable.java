package com.wanelo.wail.bitmapable;

import android.graphics.Bitmap;
import android.widget.ImageView;

import java.lang.ref.Reference;
import java.lang.ref.WeakReference;

public class ImageViewBitmapable implements Bitmapable {
    private Reference<ImageView> imageViewRef;

    public ImageViewBitmapable() {
    }

    public ImageViewBitmapable(ImageView imageView) {
        imageViewRef = createRef(imageView);
    }

    @Override
    public void setBitmap(Bitmap bitmap) {
        ImageView imageView = imageViewRef.get();
        if(imageView != null) {
            imageView.setImageBitmap(bitmap);
        }
    }

    @Override
    public boolean isValid(String url) {
        return imageViewRef != null && imageViewRef.get() != null;
    }

    public void setImageView(ImageView imageView) {
        if(imageViewRef != null) {
            imageViewRef.clear();
        }
        this.imageViewRef = createRef(imageView);
    }

    public final static Reference<ImageView> createRef(ImageView imageView) {
        return new WeakReference<ImageView>(imageView);
    }

    @Override
    public int getWidth() {
        ImageView imageView = imageViewRef.get();
        if(imageView != null) {
            return imageView.getMeasuredWidth();
        }
        return 0;
    }

    @Override
    public int getHeight() {
        ImageView imageView = imageViewRef.get();
        if(imageView != null) {
            return imageView.getMeasuredHeight();
        }
        return 0;
    }

    @Override
    public int hashCode() {
        ImageView imageView = imageViewRef.get();
        if(imageView != null) {
            return imageView.hashCode();
        }
        return super.hashCode();
    }

    @Override
    public boolean equals(Object o) {
        if(o instanceof ImageViewBitmapable) {
            ImageViewBitmapable other = (ImageViewBitmapable) o;
            ImageView otherImageView = other.imageViewRef.get();
            ImageView imageView = imageViewRef.get();
            if(otherImageView != null && imageView != null) {
                return imageView.equals(otherImageView);
            } else {
                return false;
            }
        }
        return false;
    }
}
