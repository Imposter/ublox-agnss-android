package ca.indigogames.ubloxagps.app.dialog;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.IntentFilter;
import android.support.v4.widget.SwipeRefreshLayout;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import ca.indigogames.ubloxagps.R;
import ca.indigogames.ubloxagps.app.adapter.BluetoothDeviceAdapter;
import ca.indigogames.ubloxagps.app.utility.Color;

public class BluetoothDialog {
    public interface Callback {
        void onDeviceReady(BluetoothDevice device);
        void onError(int errorCode);
        void onCancel();
    }

    public static final int ERROR_BLUETOOTH_UNSUPPORTED = 10000;
    public static final int ERROR_BLUETOOTH_NOT_ENABLED = 10001;

    private BluetoothAdapter mBluetoothAdapter;
    private BluetoothDevice mBluetoothDevice;
    private List<BluetoothDevice> mBluetoothDevices;
    private int mBluetoothSelectedPosition;

    private BluetoothDeviceAdapter mBluetoothDeviceAdapter;
    private BroadcastReceiver mBluetoothBroadcastReceiver;
    private boolean mBluetoothBroadcastReceiverRegistered;
    private View mBluetoothSelectedView;

    private Context mContext;
    private Callback mCallback;
    private boolean mClosed;

    private AlertDialog mDialog;
    private View mDialogRootView;
    private SwipeRefreshLayout mDialogRefreshLayout;
    private ListView mDialogListView;
    private Button mDialogSelectButton;
    private Button mDialogCancelButton;

    public BluetoothDialog(Context context, Callback callback) {
        mContext = context;
        mCallback = callback;
    }

    public void cancel() {
        if (mClosed)
            return;

        // Trigger callback
        mCallback.onCancel();

        // Remove broadcast receiver
        unregisterBroadcasts();

        // Close dialog if any
        if (mDialog != null)
            mDialog.dismiss();

        // Set as closed
        mClosed = true;
    }

    public void show() {
        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
        if (mBluetoothAdapter == null) {
            // Device doesn't support Bluetooth
            mCallback.onError(ERROR_BLUETOOTH_UNSUPPORTED);
            return;
        }

        // Bluetooth is not enabled
        if (!mBluetoothAdapter.isEnabled())
            mCallback.onError(ERROR_BLUETOOTH_NOT_ENABLED);

        // Create broadcast receiver
        mBluetoothBroadcastReceiver = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String action = intent.getAction();
                if (action != null) {
                    if (action.equals(BluetoothDevice.ACTION_FOUND)) {
                        BluetoothDevice device = intent
                                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        // Store found device
                        if (!mBluetoothDevices.contains(device)) {
                            mBluetoothDevices.add(device);

                            // Notify adapter of changes
                            mBluetoothDeviceAdapter.notifyDataSetChanged();
                        }
                    } else if (action.equals(BluetoothDevice.ACTION_BOND_STATE_CHANGED)) {
                        // Retrieve the bond state and the device involved
                        int bondState = intent.getIntExtra(BluetoothDevice.EXTRA_BOND_STATE, -1);
                        BluetoothDevice device = intent
                                .getParcelableExtra(BluetoothDevice.EXTRA_DEVICE);

                        // Check changes in the bond state
                        if (bondState == BluetoothDevice.BOND_BONDED
                                && device.getAddress().equals(mBluetoothDevice.getAddress())) {
                            // Connect to device
                            connectBluetooth();
                        }

                        // Notify adapter of changes
                        mBluetoothDeviceAdapter.notifyDataSetChanged();
                    } else if (action.equals(BluetoothAdapter.ACTION_DISCOVERY_FINISHED)) {
                        // No longer refreshing
                        mDialogRefreshLayout.setRefreshing(false);
                    } else if (action.equals(BluetoothAdapter.ACTION_STATE_CHANGED)) {
                        int state = intent.getIntExtra(BluetoothAdapter.EXTRA_STATE,
                                BluetoothAdapter.ERROR);

                        if (state == BluetoothAdapter.STATE_TURNING_OFF) {
                            // Device turned off
                            mCallback.onError(ERROR_BLUETOOTH_NOT_ENABLED);

                            // Unregister broadcast receiver
                            unregisterBroadcasts();

                            // Close dialog
                            mDialog.dismiss();
                        }
                    }
                }
            }
        };

        // Setup devices list adapter
        mBluetoothDevices = new ArrayList<>();
        mBluetoothDeviceAdapter = new BluetoothDeviceAdapter(mContext, mBluetoothDevices);

        // Set no currently selected device
        mBluetoothSelectedPosition = -1;
        mBluetoothSelectedView = null;

        // Inflate dialog contents
        mDialogRootView = LayoutInflater.from(mContext).inflate(R.layout.dialog_bluetooth, null);
        mDialogRefreshLayout = mDialogRootView.findViewById(R.id.dialog_bluetooth_refresh);
        mDialogListView = mDialogRootView.findViewById(R.id.dialog_bluetooth_devices);
        mDialogSelectButton = mDialogRootView.findViewById(R.id.dialog_bluetooth_select);
        mDialogCancelButton = mDialogRootView.findViewById(R.id.dialog_bluetooth_cancel);

        // Add handlers
        mDialogRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Update devices list
                updateBluetooth();
            }
        });
        mDialogListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                // Stop discovering
                if (mBluetoothAdapter.isDiscovering()) {
                    mBluetoothAdapter.cancelDiscovery();

                    // Stop refreshing
                    mDialogRefreshLayout.setRefreshing(false);
                }

                if (mBluetoothSelectedView != null) {
                    TextView nameTextView = mBluetoothSelectedView
                            .findViewById(R.id.item_bluetooth_device_name);
                    nameTextView.setTextColor(Color.getThemeColor(mContext, R.attr.colorPrimary));
                }

                TextView nameTextView = view.findViewById(R.id.item_bluetooth_device_name);
                nameTextView.setTextColor(Color.getThemeColor(mContext, R.attr.colorAccent));

                // Set selected view
                mBluetoothSelectedView = view;
                mBluetoothSelectedPosition = position;
            }
        });
        mDialogSelectButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Check if device is selected
                if (mBluetoothSelectedPosition < 0) {
                    return;
                }

                // Set selected device
                mBluetoothDevice = mBluetoothDevices.get(mBluetoothSelectedPosition);

                // Reset selection
                mBluetoothSelectedPosition = -1;

                // Pair/connect to device
                openBluetooth();
            }
        });
        mDialogCancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mClosed)
                    return;

                // Trigger cancel
                mCallback.onCancel();

                // Unregister broadcast receiver
                unregisterBroadcasts();

                // Close dialog
                mDialog.dismiss();

                // Set as closed
                mClosed = true;
            }
        });

        // Set list view adapter
        mDialogListView.setAdapter(mBluetoothDeviceAdapter);

        // Create dialog
        mDialog = new AlertDialog.Builder(mContext).setView(mDialogRootView).create();

        // Set cancel listener
        mDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialog) {
                cancel();
            }
        });

        // Show dialog
        mDialog.show();

        // Register broadcast receiver
        registerBroadcasts();

        // Set as not closed
        mClosed = false;

        // Set as refreshing
        mDialogRefreshLayout.setRefreshing(true);

        // Update bluetooth
        updateBluetooth();
    }

    private void registerBroadcasts() {
        if (mBluetoothBroadcastReceiverRegistered)
            return;

        IntentFilter filter = new IntentFilter();
        filter.addAction(BluetoothDevice.ACTION_FOUND);
        filter.addAction(BluetoothDevice.ACTION_BOND_STATE_CHANGED);
        filter.addAction(BluetoothAdapter.ACTION_DISCOVERY_FINISHED);

        mContext.registerReceiver(mBluetoothBroadcastReceiver, filter);
        mBluetoothBroadcastReceiverRegistered = true;
    }

    private void unregisterBroadcasts() {
        if (!mBluetoothBroadcastReceiverRegistered)
            return;

        try {
            mContext.unregisterReceiver(mBluetoothBroadcastReceiver);
        } catch (IllegalArgumentException ex) {
            // Ignore
            ex.printStackTrace();
        }
    }

    private void updateBluetooth() {
        // Cancel discovering if possible
        if (mBluetoothAdapter.isDiscovering())
            mBluetoothAdapter.cancelDiscovery();

        // Refresh devices
        mBluetoothDevices.clear();
        mBluetoothDevices.addAll(mBluetoothAdapter.getBondedDevices());
        mBluetoothDeviceAdapter.notifyDataSetChanged();

        // Start discovering again
        mBluetoothAdapter.startDiscovery();
    }

    private void openBluetooth() {
        // Check if paired with device
        if (mBluetoothDevice.getBondState() != BluetoothDevice.BOND_BONDED) {
            mBluetoothDevice.createBond();
        } else {
            connectBluetooth();
        }
    }

    private void connectBluetooth() {
        // Unregister broadcast receiver
        unregisterBroadcasts();

        // Open socket connection
        mCallback.onDeviceReady(mBluetoothDevice);

        // Close dialog
        mDialog.dismiss();

        // Set as closed
        mClosed = true;
    }
}
