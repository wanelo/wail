package com.wanelo.wail.pool;

import java.util.concurrent.ConcurrentLinkedQueue;

public abstract class BasePool<T> implements Pool<T> {

    private ConcurrentLinkedQueue<T> queue;

    protected BasePool() {
        this.queue = new ConcurrentLinkedQueue<T>();
    }

    @Override
    public T get() {
        T object = queue.peek();
        if(object == null) {
            object = makeObject();
        }
        return object;
    }

    @Override
    public void release(T object) {
        queue.add(object);
    }
}
