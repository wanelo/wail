package com.wanelo.wail.pool;

public interface Pool<T> {
    public T makeObject();
    public T get();
    public void release(T object);
}
