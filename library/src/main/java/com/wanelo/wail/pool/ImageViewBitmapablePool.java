package com.wanelo.wail.pool;

import com.wanelo.wail.bitmapable.ImageViewBitmapable;

public class ImageViewBitmapablePool extends BasePool<ImageViewBitmapable> {

    @Override
    public ImageViewBitmapable makeObject() {
        return new ImageViewBitmapable();
    }

}
