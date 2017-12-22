package ca.indigogames.ubloxagps.app;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.Marker;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.concurrent.Callable;

import ca.indigogames.ubloxagps.compat.UInteger;
import ca.indigogames.ubloxagps.compat.ULong;
import ca.indigogames.ubloxagps.compat.UShort;
import ca.indigogames.ubloxagps.task.AsyncTaskCallback;
import ca.indigogames.ubloxagps.task.AsyncTaskResult;
import ca.indigogames.ubloxagps.R;
import ca.indigogames.ubloxagps.task.Task;
import ca.indigogames.ubloxagps.task.TaskManager;
import ca.indigogames.ubloxagps.app.dialog.BluetoothDialog;
import ca.indigogames.ubloxagps.app.dialog.ProgressDialog;
import ca.indigogames.ubloxagps.app.task.BluetoothConnectTask;
import ca.indigogames.ubloxagps.io.BinaryStream;
import ca.indigogames.ubloxagps.io.MemoryStream;

public class MainActivity extends FragmentActivity implements OnMapReadyCallback {
    private static final int REQUEST_ENABLE_BLUETOOTH = 10000;

    private GoogleMap mMap;
    private Marker mMarker;

    private BluetoothDialog mBluetoothDialog;
    private BluetoothDevice mBluetoothDevice;
    private BluetoothSocket mBluetoothSocket;

    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Create map
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager()
                .findFragmentById(R.id.main_map);
        mapFragment.getMapAsync(this);

        // Show bluetooth selection dialog
        mBluetoothDialog = new BluetoothDialog(this, new BluetoothDialog.Callback() {
            @Override
            public void onDeviceReady(BluetoothDevice device) {
                mBluetoothDevice = device;

                // Open socket connection to device
                openBluetoothConnection();
            }

            @Override
            public void onError(int errorCode) {
                if (errorCode == BluetoothDialog.ERROR_BLUETOOTH_NOT_ENABLED) {
                    // Bluetooth not enabled
                    Toast.makeText(MainActivity.this, R.string.prompt_bluetooth_not_enabled,
                            Toast.LENGTH_SHORT).show();
                }

                finish();
            }

            @Override
            public void onCancel() {
                // Device doesn't support Bluetooth
                Toast.makeText(MainActivity.this, R.string.prompt_bluetooth_not_selected,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        });

        // Start bluetooth
        BluetoothAdapter adapter = BluetoothAdapter.getDefaultAdapter();
        if (adapter == null) {
            // Device doesn't support Bluetooth
            Toast.makeText(this, R.string.prompt_bluetooth_unsupported, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        // Start bluetooth listener
        if (!adapter.isEnabled()) {
            Intent intent = new Intent(BluetoothAdapter.ACTION_REQUEST_ENABLE);
            startActivityForResult(intent, REQUEST_ENABLE_BLUETOOTH);
        } else {
            // Show bluetooth dialog
            mBluetoothDialog.show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        // Close dialog
        if (mBluetoothDialog != null) {
            mBluetoothDialog.cancel();
        }

        // Cancel all tasks
        TaskManager.cancelAllTasks(this);

        // Close connection if open
        if (mBluetoothSocket != null && mBluetoothSocket.isConnected()) {
            try {
                mBluetoothSocket.close();
            } catch (IOException ex) {
                Log.e("MainActivity", "Unable to end bluetooth connection");
                ex.printStackTrace();
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_ENABLE_BLUETOOTH) {
            if (resultCode == RESULT_OK) {
                // Show bluetooth dialog
                mBluetoothDialog.show();
            } else {
                // Bluetooth not enabled
                Toast.makeText(this, R.string.prompt_bluetooth_not_enabled,
                        Toast.LENGTH_SHORT).show();
                finish();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public void onUpload(View view) {
        // Download A-GPS data
        /*
        TaskManager.createTask(TaskType.TASK_AGPS_DOWNLOAD,
                new DownloadAGPSTask(getString(R.string.ublox_api_key),
                        DownloadAGPSTask.GNSS_GPS, DownloadAGPSTask.DATA_TYPE_ALMANAC,
                        new AsyncTaskCallback<byte[]>() {
                            @Override
                            protected void onBegin() {
                                // TODO: Show progress dialog
                            }

                            @Override
                            protected void onEnd(AsyncTaskResult<byte[]> result) {
                                if (!result.hasError()) {
                                    byte[] data = result.getResult();

                                    // TODO: Split and spit to the bluetooth device
                                    Toast.makeText(MainActivity.this,
                                            String.format("Downloaded %d bytes", data.length),
                                            Toast.LENGTH_LONG
                                    ).show();
                                } else {
                                    Toast.makeText(MainActivity.this,
                                            String.format("Error: %s", result.getException()),
                                            Toast.LENGTH_LONG
                                    ).show();
                                }
                            }
                        }
                )
        );
        */
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        mMap = googleMap;

        // Configure the map settings
        mMap.setTrafficEnabled(true);
        mMap.setBuildingsEnabled(true);
        mMap.setMapType(GoogleMap.MAP_TYPE_NORMAL);

        // Enable the zoom controls
        mMap.getUiSettings().setZoomGesturesEnabled(true);
        mMap.getUiSettings().setIndoorLevelPickerEnabled(false);
        mMap.getUiSettings().setMapToolbarEnabled(false);

        mMap.setInfoWindowAdapter(new GoogleMap.InfoWindowAdapter() {
            @Override
            public View getInfoWindow(Marker arg0) {
                return null;
            }

            @Override
            public View getInfoContents(Marker marker) {
                Context context = getBaseContext();

                // Get dimension for default padding
                float padding = getResources().getDimension((R.dimen.location_padding));

                LinearLayout info = new LinearLayout(context);
                info.setOrientation(LinearLayout.VERTICAL);
                info.setPadding((int) padding, (int) padding, (int) padding, (int) padding);

                TextView title = new TextView(context);
                title.setTextColor(Color.BLACK);
                title.setGravity(Gravity.CENTER);
                title.setTypeface(null, Typeface.BOLD);
                title.setText(marker.getTitle());
                info.addView(title);

                String[] lines = marker.getSnippet().split("\r\n");
                for (String line : lines) {
                    TextView snippet = new TextView(context);
                    snippet.setTextColor(Color.GRAY);
                    snippet.setText(line);
                    info.addView(snippet);
                }

                return info;
            }
        });
    }

    private void openBluetoothConnection() {
        TaskManager.createTask(this, TaskType.TASK_BLUETOOTH_CONNECTION,
                new BluetoothConnectTask(mBluetoothDevice, new AsyncTaskCallback<BluetoothSocket>() {
                    @Override
                    protected void onBegin() {
                        mProgressDialog = new ProgressDialog(MainActivity.this,
                                String.format(
                                        getString(R.string.prompt_bluetooth_connecting),
                                        mBluetoothDevice.getName()
                                ),
                                new ProgressDialog.Callback() {
                                    @Override
                                    public void onCancel() {
                                        // Cancel any connection task
                                        TaskManager.cancelAllTasks(
                                                TaskType.TASK_BLUETOOTH_CONNECTION
                                        );

                                        // Exit
                                        finish();
                                    }
                                }
                        );

                        mProgressDialog.setCancellable(true);
                        mProgressDialog.show();
                    }

                    @Override
                    protected void onEnd(AsyncTaskResult<BluetoothSocket> result) {
                        // Hide progress dialog
                        mProgressDialog.dismiss();

                        if (!result.hasError()) {
                            // Get connected socket
                            mBluetoothSocket = result.getResult();

                            if (mBluetoothSocket.isConnected()) {
                                Toast.makeText(
                                        MainActivity.this,
                                        String.format(
                                                getString(
                                                        R.string.prompt_bluetooth_connection_success
                                                ),
                                                mBluetoothDevice.getName()
                                        ),
                                        Toast.LENGTH_LONG
                                ).show();

                                runBluetoothThread();
                            }
                        } else {
                            // Print exception
                            result.getException().printStackTrace();

                            Toast.makeText(
                                    MainActivity.this,
                                    String.format(
                                            getString(R.string.prompt_bluetooth_connection_error),
                                            mBluetoothDevice.getName()
                                    ),
                                    Toast.LENGTH_LONG
                            ).show();
                            finish();
                        }
                    }
                })
        );
    }

    // TODO: Do NOT push this repo to github, as it contains
    // 1. ublox logos
    // 2. ublox API key (make a private server which caches alm) and broadcasts it
    // TODO: Reverse engineer AID-INI/AID-ALM packets
    private void runBluetoothThread() {
        // Start background task to print to console
        Task.run(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                InputStream stream = mBluetoothSocket.getInputStream();
                InputStreamReader inputStreamReader = new InputStreamReader(stream);
                BufferedReader reader = new BufferedReader(inputStreamReader);

                try {
                    String line;
                    while (mBluetoothSocket.isConnected()
                            && (line = reader.readLine()) != null) {
                        Log.d("MainActivity", String.format("Received: %s", line));


                        //http://online-live1.services.u-blox.com/GetOnlineData.ashx?token=27RCcNZGPU6t-rhOlU7KUg;gnss=gps;datatype=alm;lat=43.750619;lon=-79.221536;alt=-22.190000;pacc=999999.000000;format=aid
                    }
                } catch (Exception ex) {
                    Log.e("MainActivity", "Unable to read bluetooth message");
                    ex.printStackTrace();
                }

                return null;
            }
        });
    }
}
