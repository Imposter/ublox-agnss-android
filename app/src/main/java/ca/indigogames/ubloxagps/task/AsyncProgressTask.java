package ca.indigogames.ubloxagps.task;

import android.support.annotation.NonNull;

/**
 * Wrapper for Android asynchronous tasks to simplify implementation of child tasks and added state
 * information
 *
 * @param <TProgress> Task progress type
 * @param <TResult> Task result type
 */
public abstract class AsyncProgressTask<TProgress, TResult> extends android.os.AsyncTask<Void,
        TProgress, AsyncTaskResult<TResult>> implements Comparable {
    private boolean mRunning;
    private AsyncProgressTaskCallback<TProgress, TResult> mCallback;

    /**
     * Default constructor, initialize task
     */
    public AsyncProgressTask() {
        mRunning = false;
    }

    /**
     * Initialize task using callback instead of traditional anonymous overrides
     * @param callback Task callback
     */
    public AsyncProgressTask(AsyncProgressTaskCallback<TProgress, TResult> callback) {
        mRunning = false;
        mCallback = callback;
    }

    /**
     * Set task callback for events
     * @param callback Task callback
     */
    public void setCallback(AsyncProgressTaskCallback<TProgress, TResult> callback) {
        mCallback = callback;
    }

    /**
     * Compare instances to see if they're the same
     * @param o Other instance
     * @return Difference between instances
     */
    @Override
    public int compareTo(@NonNull Object o) {
        return o.equals(this) ? 0 : -1;
    }

    /**
     * Task to execute asynchronously (Internal)
     *
     * @param params Unused parameters
     * @return Task result
     */
    @Override
    protected AsyncTaskResult<TResult> doInBackground(Void... params) {
        try {
            return new AsyncTaskResult<>(process());
        } catch (Exception ex) {
            return new AsyncTaskResult<>(ex);
        }
    }

    /**
     * Called when the task is started (Internal)
     */
    @Override
    protected void onPreExecute() {
        mRunning = true;
        onBegin();
    }

    /**
     * Called while the task is running and updating its progress
     *
     * @param values Progress values, only first is used
     */
    @SafeVarargs
    @Override
    protected final void onProgressUpdate(TProgress... values) {
        onUpdate(values[0]);
    }

    /**
     * Called after the task is completed (Internal)
     *
     * @param result Task result
     */
    @Override
    protected void onPostExecute(AsyncTaskResult<TResult> result) {
        mRunning = false;
        onEnd(result);
    }

    /**
     * Called if the task is cancelled (Internal)
     *
     * @param result Task result
     */
    @Override
    protected void onCancelled(AsyncTaskResult<TResult> result) {
        mRunning = false;
        onCancelled();
    }

    /**
     * Called when the task is started
     */
    protected void onBegin() {
        if (mCallback != null) {
            mCallback.onBegin();
        }
    }

    /**
     * Called when the task's progress has been updated
     *
     * @param progress Task progress
     */
    protected void onUpdate(TProgress progress) {
        if (mCallback != null) {
            mCallback.onUpdate(progress);
        }
    }

    /**
     * Called when the task is completed
     *
     * @param result Task result
     */
    protected void onEnd(AsyncTaskResult<TResult> result) {
        if (mCallback != null) {
            mCallback.onEnd(result);
        }
    }

    /**
     * Called if the task is cancelled
     */
    @Override
    protected void onCancelled() {
        if (mCallback != null) {
            mCallback.onCancelled();
        }
    }

    /**
     * Task is running or not
     *
     * @return Whether the task is running or not
     */
    public boolean isRunning() {
        return mRunning;
    }

    /**
     * Task to execute asynchronously
     *
     * @return Task result
     */
    protected abstract TResult process() throws Exception;

    /**
     * Cancels the task
     */
    public void cancel() {
        super.cancel(true);
    }
}
