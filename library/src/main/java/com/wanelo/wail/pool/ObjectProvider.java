package com.wanelo.wail.pool;

import com.wanelo.wail.bitmapable.ImageViewBitmapable;

public class ObjectProvider {
    private ImageViewBitmapablePool imageViewBitmapablePool;

    public ObjectProvider() {
        imageViewBitmapablePool = new ImageViewBitmapablePool();
    }

    public ImageViewBitmapable getImageViewBitmapable() {
        return imageViewBitmapablePool.get();
    }
}
