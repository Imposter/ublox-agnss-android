package ca.indigogames.ubloxagps.task;

/**
 * Used by AsyncTask to handle events occurring in the task instead of using traditional anonymous
 * methods
 * @param <TResult> Task result type
 */
public class AsyncTaskCallback<TResult> extends AsyncProgressTaskCallback<Void, TResult> {
}
