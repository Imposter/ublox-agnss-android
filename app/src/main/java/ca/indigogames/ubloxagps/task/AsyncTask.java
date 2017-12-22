package ca.indigogames.ubloxagps.task;

/**
 * Wrapper for Android asynchronous tasks to simplify implementation of child tasks and added state
 * information
 * @param <TResult> Task result type
 */
public abstract class AsyncTask<TResult> extends AsyncProgressTask<Void, TResult> {
    /**
     * Default constructor
     */
    public AsyncTask() {
        super();
    }

    /**
     * Initialize async task with callback instead of traditional overrides
     * @param callback Task callback
     */
    public AsyncTask(AsyncTaskCallback<TResult> callback) {
        super(callback);
    }

    /**
     * Sets task callback for events
     * @param callback Task callback
     */
    public void setCallback(AsyncTaskCallback<TResult> callback) {
        setCallback((AsyncProgressTaskCallback<Void, TResult>)callback);
    }
}