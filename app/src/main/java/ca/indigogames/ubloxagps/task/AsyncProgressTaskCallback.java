package ca.indigogames.ubloxagps.task;

/**
 * Used by AsyncTask to handle events occurring in the task instead of using traditional anonymous
 * methods
 * @param <TProgress> Task progress type
 * @param <TResult> Task result type
 */
public class AsyncProgressTaskCallback<TProgress, TResult> {
    /**
     * Called when the task is started
     */
    protected void onBegin() {
    }

    /**
     * Called when the task's progress has been updated
     * @param progress Task progress
     */
    protected void onUpdate(TProgress progress) {
    }


    /**
     * Called when the task is completed
     * @param result Task result
     */
    protected void onEnd(AsyncTaskResult<TResult> result) {
    }

    /**
     * Called if the task is cancelled
     */
    protected void onCancelled() {
    }
}
