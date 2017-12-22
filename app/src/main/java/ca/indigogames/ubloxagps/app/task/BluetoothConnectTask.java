package ca.indigogames.ubloxagps.app.task;

import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.os.ParcelUuid;

import java.util.UUID;

import ca.indigogames.ubloxagps.task.AsyncTask;
import ca.indigogames.ubloxagps.task.AsyncTaskCallback;

public class BluetoothConnectTask extends AsyncTask<BluetoothSocket> {
    private static final UUID DEFAULT_BLUETOOTH_UUID =
            UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    private BluetoothDevice mDevice;

    public BluetoothConnectTask(BluetoothDevice device,
                                AsyncTaskCallback<BluetoothSocket> callback) {
        super(callback);
        mDevice = device;
    }

    @Override
    protected BluetoothSocket process() throws Exception {
        // Get UUID for device
        UUID uuid = DEFAULT_BLUETOOTH_UUID;
        ParcelUuid[] parcelUuids = mDevice.getUuids();
        if (parcelUuids.length > 0) {
            uuid = parcelUuids[0].getUuid();
        }

        // Create socket
        BluetoothSocket socket = mDevice.createRfcommSocketToServiceRecord(uuid);

        // Connect to socket
        socket.connect();

        return socket;
    }
}
