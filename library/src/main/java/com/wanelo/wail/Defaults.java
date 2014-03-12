package com.wanelo.wail;

public class Defaults {
    static final int DEFAULT_THREAD_POOL_SIZE = 3;
    static final int DEFAULT_THREAD_POOL_PRIORITY = Thread.NORM_PRIORITY;
    static final String THREAD_PREFIX = "Wail-";
    static final String THREAD_IDLE_NAME = THREAD_PREFIX + "Idle";
    static final int DEFAULT_READ_TIMEOUT = 20 * 1000; // 20s
    static final int DEFAULT_CONNECT_TIMEOUT = 15 * 1000; // 15s
}
