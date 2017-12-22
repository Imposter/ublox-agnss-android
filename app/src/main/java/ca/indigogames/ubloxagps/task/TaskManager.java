package ca.indigogames.ubloxagps.task;

import android.content.Context;
import android.util.Log;

import java.util.Map;
import java.util.TreeMap;
import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class TaskManager {
    public static class TaskInfo {
        protected Context context;
        protected long id;
        protected UUID type;
    }

    public static final UUID DEFAULT_UUID = UUID.fromString("00000000-0000-0000-0000-000000000000");
    private static AtomicLong sIdCounter = new AtomicLong();
    private static final Map<AsyncProgressTask, TaskInfo> sTasks = new TreeMap<>();
    private static final ExecutorService sExecutor = Executors.newCachedThreadPool();

    public static synchronized AsyncProgressTask createTask(Context context, UUID type,
                                                            AsyncProgressTask task) {
        TaskInfo info = new TaskInfo();
        info.context = context;
        info.id = sIdCounter.getAndIncrement();
        info.type = type;

        // Store task
        sTasks.put(task, info);
        Log.v("TaskManager", String.format("Created task %d of type %s", info.id, type));

        // Execute
        task.executeOnExecutor(Task.getExecutor(), null);

        return task;
    }

    public static synchronized AsyncProgressTask createTask(Context context,
                                                            AsyncProgressTask task) {
        return createTask(context, DEFAULT_UUID, task);
    }

    public static synchronized AsyncProgressTask createTask(UUID type, AsyncProgressTask task) {
        return createTask(null, type, task);
    }

    public static synchronized AsyncProgressTask createTask(AsyncProgressTask task) {
        return createTask(null, DEFAULT_UUID, task);
    }

    public static synchronized AsyncTask createTask(Context context, UUID type, AsyncTask task) {
        return (AsyncTask)createTask(context, type, (AsyncProgressTask)task);
    }

    public static synchronized AsyncTask createTask(Context context, AsyncTask task) {
        return createTask(context, DEFAULT_UUID, task);
    }

    public static synchronized AsyncTask createTask(UUID id, AsyncTask task) {
        return createTask(null, id, task);
    }

    public static synchronized AsyncTask createTask(AsyncTask task) {
        return createTask(null, DEFAULT_UUID, task);
    }

    public static synchronized void cancelTask(AsyncProgressTask task) {
        if (!sTasks.containsKey(task))
            throw new IllegalArgumentException("Invalid task");

        TaskInfo info = sTasks.get(task);
        sTasks.remove(task);
        task.cancel();

        Log.v("TaskManager", String.format("Cancelled task %d", info.id));
    }

    public static synchronized void cancelTask(AsyncTask task) {
        cancelTask((AsyncProgressTask)task);
    }

    public static synchronized void cancelAllTasks(UUID type) {
        Log.v("TaskManager", String.format("Cancelling tasks of type %s", type));

        int killed = 0;
        for (Map.Entry<AsyncProgressTask, TaskInfo> entry : sTasks.entrySet()) {
            AsyncProgressTask task = entry.getKey();
            TaskInfo info = entry.getValue();
            if (info.type == type) {
                task.cancel();
                sTasks.remove(task);
                killed++;

                Log.v("TaskManager", String.format("Cancelled task %d", info.id));
            }
        }

        Log.v("TaskManager", String.format("Cancelled %d tasks", killed));
    }

    public static synchronized void cancelAllTasks(Context context) {
        Log.v("TaskManager", String.format("Cancelling tasks for context %s", context.toString()));

        int cancelled = 0;
        for (Map.Entry<AsyncProgressTask, TaskInfo> entry : sTasks.entrySet()) {
            AsyncProgressTask task = entry.getKey();
            TaskInfo info = entry.getValue();
            if (info.context == context) {
                task.cancel();
                sTasks.remove(task);
                cancelled++;

                Log.v("TaskManager", String.format("Cancelled task %d", info.id));
            }
        }

        Log.v("TaskManager", String.format("Cancelled %d tasks", cancelled));
    }
}
