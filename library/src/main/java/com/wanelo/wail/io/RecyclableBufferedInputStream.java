package com.wanelo.wail.io;

import android.util.Log;

import com.wanelo.wail.pool.ByteArrayPool;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

public class RecyclableBufferedInputStream extends BufferedInputStream {
    private ByteArrayPool pool;

    public RecyclableBufferedInputStream(InputStream in, ByteArrayPool pool) {
        this(in, ByteArrayPool.DEFAULT_BUFFER_SIZE, pool);
    }

    public RecyclableBufferedInputStream(InputStream in, int size, ByteArrayPool pool) {
        super(in, 1);
        init(size, pool);
    }

    private void init(int size, ByteArrayPool pool) {
        this.pool = pool;
        buf = pool.get();
    }

    @Override
    public void close() throws IOException {
        pool.release(buf);
        super.close();
    }

    @Override
    public void finalize() {
        if(buf != null) {
            pool.release(buf);
            Log.e(ByteArrayPool.TAG, "RecyclableBufferedInputStream stream leak!");
        }
    }
}