package ca.indigogames.ubloxagps.app.dialog;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;

import ca.indigogames.ubloxagps.R;

public class ProgressDialog {
    public interface Callback {
        void onCancel();
    }

    private Context mContext;
    private Callback mCallback;

    private String mTitle;
    private boolean mCancellable;

    private AlertDialog mDialog;
    private View mDialogRootView;
    private ProgressBar mDialogProgressBar;
    private TextView mDialogTextView;

    public ProgressDialog(Context context) {
        mContext = context;
        mTitle = "";
        mCancellable = false;
    }

    public ProgressDialog(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
        mTitle = "";
        mCancellable = true;
    }

    public ProgressDialog(Context context, String title) {
        mContext = context;
        mTitle = title;
        mCancellable = false;
    }

    public ProgressDialog(Context context, String title, Callback callback) {
        mContext = context;
        mTitle = title;
        mCallback = callback;
        mCancellable = true;
    }

    public void setTitle(String title) {
        mTitle = title;
        if (mDialogTextView != null) {
            mDialogTextView.setText(title);
        }
    }

    public void setCancellable(boolean cancellable) {
        mCancellable = cancellable;
        if (mDialog != null) {
            mDialog.setCancelable(cancellable);
        }
    }

    public void setCallback(Callback callback) {
        mCallback = callback;
    }

    public void cancel() {
        // Canceled
        if (mCallback != null)
            mCallback.onCancel();

        // Close dialog if any
        if (mDialog != null)
            mDialog.dismiss();
    }

    public void show() {
        // Inflate dialog contents
        mDialogRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_progress, null);
        mDialogProgressBar = mDialogRootView.findViewById(R.id.dialog_progress_bar);
        mDialogTextView = mDialogRootView.findViewById(R.id.dialog_progress_text);

        // Set title
        if (!mTitle.isEmpty())
            mDialogTextView.setText(mTitle);

        // Create dialog
        mDialog = new AlertDialog.Builder(mContext).setView(mDialogRootView).create();
        mDialog.setCancelable(mCancellable);

        // Set cancel listener
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel();
            }
        });

        // Show dialog
        mDialog.show();
    }

    public void dismiss() {
        // Close dialog if any
        if (mDialog != null)
            mDialog.dismiss();
    }
}
