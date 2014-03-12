package com.wanelo.wail;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import com.wanelo.wail.io.MarkableInputStream;
import com.wanelo.wail.pool.ByteArrayPool;

import java.io.InputStream;

public class Decoder {

    private static final String TAG = Decoder.class.getSimpleName();
    private static final int MARKER = 65536;

    private ByteArrayPool pool;
    private Downloader downloader;

    public Decoder(Downloader downloader, ByteArrayPool pool) {
        this.pool = pool;
        this.downloader = downloader;
    }

    public Bitmap decode(Command command) {
        Bitmap result = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        byte[] bytes = pool.get();
        options.inTempStorage = bytes;

        MarkableInputStream markableIs = null;
        try {
            String url = command.url;
            int targetWidth = command.getWidth();
            int targetHeight = command.getHeight();

            InputStream is = downloader.load(url);
            markableIs = new MarkableInputStream(is, pool);

            long mark = markableIs.savePosition(MARKER);

            if(targetWidth != 0 && targetHeight != 0) {
                options.inJustDecodeBounds = true;
                BitmapFactory.decodeStream(markableIs, null, options);
                int outWidth = options.outWidth;
                int outHeight = options.outHeight;

                calculateInSampleSize(targetWidth, targetHeight, outWidth, outHeight, options);
                options.inJustDecodeBounds = false;

                try {
                    markableIs.reset(mark);
                } catch (Throwable t) {
                    closeQuietly(markableIs);
                    is = downloader.load(url);
                    Log.e(TAG, "", t);
                }
            }

            result = BitmapFactory.decodeStream(is, null, options);
        } catch (Throwable t) {
            Log.e(TAG, "", t);
            throw new RuntimeException(t);
        } finally {
            closeQuietly(markableIs);
            pool.release(bytes);
        }

        return result;
    }

    private static final void calculateInSampleSize(int targetWidth, int targetHeight, int outWidth, int outHeight, BitmapFactory.Options options) {
        int sampleSize = 1;
        if (outHeight > targetHeight || outWidth > targetWidth) {
            final int heightRatio = Math.round((float) outHeight / (float) targetHeight);
            final int widthRatio = Math.round((float) outWidth / (float) targetWidth);
            sampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        options.inSampleSize = sampleSize;
    }

    private static final void closeQuietly(InputStream is) {
        if(is != null) {
            try {
                is.close();
            } catch (Throwable ignore){
                //
            }
        }
    }

}
