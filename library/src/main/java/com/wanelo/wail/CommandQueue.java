package com.wanelo.wail;

import android.util.Log;

import com.wanelo.wail.executor.PausablePriorityBlockingQueue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Future;

public class CommandQueue {
    private final PausablePriorityBlockingQueue<Command> commandQueue;
    private final Map<Integer, Command> commandMap;
    private final Map<Integer, Future> futureMap;
    private CommandIdGenerator commandIdGenerator;

    public CommandQueue() {
        commandQueue = new PausablePriorityBlockingQueue<Command>();
        commandMap = new ConcurrentHashMap<Integer, Command>();
        futureMap = new ConcurrentHashMap<Integer, Future>();
        commandIdGenerator = new CommandIdGenerator();
    }

    public Command take() throws InterruptedException {
        Command command = commandQueue.take();
        commandMap.remove(command.bitmapableCode);
        return command;
    }

    void add(Command command) {
        Log.d("YYY", "Adding command " + command.bitmapableCode + " - " + command.url);
        long id = commandIdGenerator.createId();
        command.id = id;
        remove(command);
        commandQueue.add(command);
        commandMap.put(command.bitmapableCode, command);
    }

    private void remove(Command command) {
        int code = command.bitmapableCode;
        Command removedCommand = commandMap.remove(code);
        if(removedCommand != null) {
            Log.d("YYY", "Command exists - removed - " + removedCommand.bitmapableCode + " - " + removedCommand.url);
            removedCommand.invalidate();
            commandQueue.remove(command);
        }

        Future future = futureMap.get(code);
        if(future != null) {
            Log.d("YYY", "Command had future - canceling - " + code);
            future.cancel(true);
        }
    }

    void end(Command command) {
        Log.d("YYY", "Command complete " + command.bitmapableCode + " - " + command.url);
        futureMap.remove(command.bitmapableCode);
    }

    public void start(Command command, Future<?> future) {
        futureMap.put(command.bitmapableCode, future);
    }
}
