package com.wanelo.wail;

import android.content.Context;

import com.wanelo.wail.executor.PriorityThreadPoolExecutor;

import java.util.concurrent.ExecutorService;

public class Config {
    private Context context;
    private int defaultThreadPriority = Defaults.DEFAULT_THREAD_POOL_PRIORITY;
    private int defaultThreadPoolSize = Defaults.DEFAULT_THREAD_POOL_SIZE;
    private ExecutorService executorService;

    public Config(Context context) {
        this.context = context;
    }

    public int getDefaultThreadPriority() {
        return defaultThreadPriority;
    }

    public void setDefaultThreadPriority(int defaultThreadPriority) {
        this.defaultThreadPriority = defaultThreadPriority;
    }

    public int getDefaultThreadPoolSize() {
        return defaultThreadPoolSize;
    }

    public void setDefaultThreadPoolSize(int defaultThreadPoolSize) {
        this.defaultThreadPoolSize = defaultThreadPoolSize;
    }

    public void setExecutorService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    ExecutorService getExecutorService() {
        if(executorService == null) {
            executorService = new PriorityThreadPoolExecutor(defaultThreadPoolSize, defaultThreadPriority);
        }
        return executorService;
    }

    Context getContext() {
        return context;
    }
}
