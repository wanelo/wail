package com.wanelo.wail;

import android.graphics.Bitmap;
import android.os.Handler;
import android.util.Log;

import com.wanelo.wail.executor.PriorityRunnable;

public class CommandProcessor implements PriorityRunnable {

    private static final String TAG = CommandProcessor.class.getSimpleName();

    private Command command;
    private Decoder decoder;
    private Handler handler;
    private CommandQueue queue;

    public CommandProcessor(CommandQueue queue, Handler handler, Decoder decoder, Command command) {
        this.command = command;
        this.decoder = decoder;
        this.handler = handler;
        this.queue = queue;
    }

    @Override
    public void run() {
        if(command.isValid()) {
            Log.d("YYY", "Runnable running " + command.bitmapableCode + " - " + command.url);
            try {
                Bitmap bitmap = decoder.decode(command);

                if(command.isValid()) {
                    Log.d("YYY", "Runnable posting - " + command.bitmapableCode + " - " + command.url);
                    handler.post(new DisplayProcessor(command, bitmap));
                } else {
                    Log.d("YYY", "Runnable canceld - invalid2 - " + command.bitmapableCode + " - " + command.url);
                }
            } catch (Throwable t) {
                Log.e(TAG, "", t);
            }
        } else {
            Log.d("YYY", "Runnable canceled - invalid - " + command.bitmapableCode + " - " + command.url);
        }

        queue.end(command);
        //else command canceled, invalid?
    }

    private static class DisplayProcessor implements Runnable {
        private Bitmap bitmap;
        private Command command;

        private DisplayProcessor(Command command, Bitmap bitmap) {
            this.bitmap = bitmap;
            this.command = command;
        }

        @Override
        public void run() {
            if(command.isValid()) {
                Log.d("YYY", "Display success - " + command.bitmapableCode + " - " + command.url);
                command.bitmapable.setBitmap(bitmap);
            } else {
                Log.d("YYY", "Display canceled - " + command.bitmapableCode + " - " + command.url);
            }
        }
    }

    @Override
    public int getPriority() {
        return Defaults.DEFAULT_THREAD_POOL_PRIORITY;
    }
}
