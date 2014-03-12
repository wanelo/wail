package com.wanelo.wail;

import android.content.Context;
import android.os.Handler;
import android.util.Log;
import android.widget.ImageView;

import com.wanelo.wail.bitmapable.ImageViewBitmapable;
import com.wanelo.wail.pool.ByteArrayPool;
import com.wanelo.wail.pool.ObjectProvider;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

public class Wail {
    private static final String TAG = Wail.class.getSimpleName();

    private ExecutorService executorService;
    private Config config;
    private ObjectProvider objectProvider;
    private Downloader downloader;
    private Decoder decoder;
    private ByteArrayPool byteArrayPool;
    private Handler handler = null;
    private CommandQueue commandQueue;

    private static Wail instance = null;

    private Wail(Config config) {
        this.config = config;
        executorService = config.getExecutorService();
        byteArrayPool = new ByteArrayPool();
        objectProvider = new ObjectProvider();
        downloader = new Downloader(byteArrayPool);
        decoder = new Decoder(downloader, byteArrayPool);
        handler = new Handler(config.getContext().getMainLooper());
        commandQueue = new CommandQueue();
        new QueueConsumer().start();
    }

    public synchronized static Wail from(Context context) {
        if(instance == null) {
            instance = new Wail(new Config(context));
        }

        return instance;
    }

    private synchronized static Wail create(Config config) {
        if(instance != null) {
            return instance;
        }
        instance = new Wail(config);
        return instance;
    }

    public final void load(String url, ImageView imageView) {
        ImageViewBitmapable bitmapable = objectProvider.getImageViewBitmapable();
        bitmapable.setImageView(imageView);
        Command command = new Command(url, bitmapable);
        commandQueue.add(command);
    }

    private class QueueConsumer extends Thread {

        private QueueConsumer() {
        }

        @Override
        public void run() {
            super.run();
            try {
                while(true && !isInterrupted()) {
                    Command command = commandQueue.take();
                    if(command.isValid()) {
                        Log.d("YYY", "Took Command VALID " + command.bitmapableCode + " - " + command.url);
                        Future<?> future = executorService.submit(new CommandProcessor(commandQueue, handler, decoder, command));
                        commandQueue.start(command, future);
                    } else {
                        Log.d("YYY", "Took Command INVALID " + command.bitmapableCode + " - " + command.url);
                    }
                }
            } catch (InterruptedException e) {
                Log.e(TAG, "", e);
            }
        }
    }

    public static final class Builder {
        private Config config;

        public Builder(Context context) {
            config = new Config(context.getApplicationContext());
        }

        public Builder setDefaultThreadPriority(int defaultThreadPriority) {
            config.setDefaultThreadPriority(defaultThreadPriority);
            return this;
        }

        public Builder setDefaultThreadPoolSize(int defaultThreadPoolSize) {
            config.setDefaultThreadPoolSize(defaultThreadPoolSize);
            return this;
        }

        public Builder setExecutorService(ExecutorService executorService) {
            config.setExecutorService(executorService);
            return this;
        }

        public Wail build() {
            return Wail.create(config);
        }
    }
}