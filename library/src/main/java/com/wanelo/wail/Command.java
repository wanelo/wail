package com.wanelo.wail;

import com.wanelo.wail.bitmapable.Bitmapable;

public class Command implements Comparable<Command> {
    long id;
    String url;
    Bitmapable bitmapable;
    int bitmapableCode;
    int width;
    int height;
    boolean valid = true;

    public Command(String url, Bitmapable bitmapable) {
        this.url = url;
        this.bitmapable = bitmapable;
        this.bitmapableCode = bitmapable.hashCode();
    }

    public boolean isValid() {
        return valid && bitmapable != null && bitmapable.isValid(url);
    }

    public int getWidth() {
        if(width != 0) {
            return width;
        }

        width = bitmapable.getWidth();

        if(width != 0) {
            return width;
        }

        return 0;
    }

    public int getHeight() {
        if(height != 0) {
            return height;
        }

        height = bitmapable.getHeight();

        if(height != 0) {
            return height;
        }

        return 0;
    }

    @Override
    public int compareTo(Command another) {
        return 0;
    }

    public void invalidate() {
        valid = false;
        bitmapable = null;
        //return bitmapable?
    }
}
