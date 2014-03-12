package com.wanelo.wail.io;

import android.util.Log;

import com.wanelo.wail.pool.ByteArrayPool;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class RecyclableBufferedOutputStream extends BufferedOutputStream {
    private ByteArrayPool pool;

    public RecyclableBufferedOutputStream(OutputStream out, ByteArrayPool pool) {
        this(out, ByteArrayPool.DEFAULT_BUFFER_SIZE, pool);
    }

    public RecyclableBufferedOutputStream(OutputStream out, int size, ByteArrayPool pool) {
        super(out, 1);
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
            Log.e(ByteArrayPool.TAG, "RecyclableBufferedOutputStream stream leak!");
        }
    }
}
