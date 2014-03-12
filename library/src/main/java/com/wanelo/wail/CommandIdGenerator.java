package com.wanelo.wail;

public class CommandIdGenerator {

    private long id = 0l;
    private static final long MAX = Long.MAX_VALUE-10;

    public synchronized long createId() {
        long result = ++id;
        if(result == MAX) {
            id = MAX;
        }

        return id;
    }
}
