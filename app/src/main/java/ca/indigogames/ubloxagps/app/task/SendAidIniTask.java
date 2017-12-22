package ca.indigogames.ubloxagps.app.task;

import android.bluetooth.BluetoothSocket;

import ca.indigogames.ubloxagps.task.AsyncTask;

public class SendAidIniTask extends AsyncTask<Boolean> {
    private BluetoothSocket mBluetoothSocket;

    public SendAidIniTask(BluetoothSocket socket) {
        mBluetoothSocket = socket;
    }

    @Override
    protected Boolean process() throws Exception {
        // ...

        return null;
    }
}
