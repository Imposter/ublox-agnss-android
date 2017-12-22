package ca.indigogames.ubloxagps.app.adapter;

import android.bluetooth.BluetoothDevice;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.List;

import ca.indigogames.ubloxagps.R;
import ca.indigogames.ubloxagps.utility.Hash;

public class BluetoothDeviceAdapter extends BaseAdapter {
    private Context mContext;
    private List<BluetoothDevice> mDevices;

    public BluetoothDeviceAdapter(Context context, List<BluetoothDevice> devices) {
        mContext = context;
        mDevices = devices;
    }

    @Override
    public int getCount() {
        return mDevices.size();
    }

    @Override
    public Object getItem(int position) {
        return mDevices.get(position);
    }

    @Override
    public long getItemId(int position) {
        BluetoothDevice device = mDevices.get(position);
        return Hash.FNV1A_32(device.getAddress().getBytes());
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // Get device
        if (position >= mDevices.size()) {
            return null;
        }

        BluetoothDevice device = mDevices.get(position);

        LayoutInflater inflater = (LayoutInflater)mContext
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View view = inflater.inflate(R.layout.item_bluetooth_device, parent, false);
        TextView nameTextView = (TextView)view.findViewById(R.id.item_bluetooth_device_name);
        TextView addressTextView = (TextView)view.findViewById(R.id.item_bluetooth_device_address);
        TextView statusTextView = (TextView)view.findViewById(R.id.item_bluetooth_device_status);

        String name = device.getName();
        if (name == null || name.isEmpty()) {
            name = mContext.getString(R.string.prompt_dialog_bluetooth_unknown);
        }

        String status = mContext.getString(R.string.prompt_dialog_bluetooth_available);
        switch (device.getBondState()) {
            case BluetoothDevice.BOND_BONDED:
                status = mContext.getString(R.string.prompt_dialog_bluetooth_paired);
                break;
            case BluetoothDevice.BOND_BONDING:
                status = mContext.getString(R.string.prompt_dialog_bluetooth_pairing);
                break;
        }

        // Set information
        nameTextView.setText(name);
        addressTextView.setText(device.getAddress());
        statusTextView.setText(status);

        return view;
    }
}
