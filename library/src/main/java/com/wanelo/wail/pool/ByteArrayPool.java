package com.wanelo.wail.pool;

import android.util.Log;

import java.util.LinkedList;
import java.util.Queue;

public class ByteArrayPool implements Pool<byte[]> {
    public static final String TAG = ByteArrayPool.class.getSimpleName();

    public static int MAX_SIZE = 20;
    public static final int DEFAULT_BUFFER_SIZE = 32 * 1024; // 32 Kb
    private static final Queue<byte[]> queue = new LinkedList<byte[]>();

    @Override
    public byte[] makeObject() {
        return new byte[DEFAULT_BUFFER_SIZE];
    }

    @Override
    public byte[] get() {
        byte[] result = queue.poll();
        if(result == null) {
            result = makeObject();
        }
        return result;
    }

    public void release(byte[] byteArray) {
        if(byteArray != null && byteArray.length > 1 && queue.size() < MAX_SIZE) {
            queue.add(byteArray);
        } else  {
            Log.e(TAG, "ByteArray CANNOT be released - " + queue.size() + " - ");
        }
    }
}