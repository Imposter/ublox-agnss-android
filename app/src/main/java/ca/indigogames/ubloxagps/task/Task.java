package ca.indigogames.ubloxagps.task;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class Task {
    private static final ExecutorService sExecutor = Executors.newCachedThreadPool();

    public static ExecutorService getExecutor() {
        return sExecutor;
    }

    /**
     * Starts a task on a new thread as long as the pool is not empty. However this should be used
     * carefully as deadlocks can be produced if a task is depended on and the pool is empty,
     * therefore the thread will not be created and the depending thread will wait indefinitely.
     * @param callable Callable to run on new thread
     * @param <TResult> Callable result type
     * @return Task created by executor
     */
    public static <TResult> Future<TResult> run(Callable<TResult> callable) {
        return sExecutor.submit(callable);
    }
}
