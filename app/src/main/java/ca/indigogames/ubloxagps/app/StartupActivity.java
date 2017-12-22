package ca.indigogames.ubloxagps.app;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import ca.indigogames.ubloxagps.R;
import ca.indigogames.ubloxagps.task.Task;

public class StartupActivity extends AppCompatActivity {
    public static final int REQUEST_PERMISSIONS = 10000;
    public static final String[] PERMISSIONS = {
            Manifest.permission.INTERNET,
            Manifest.permission.BLUETOOTH,
            Manifest.permission.BLUETOOTH_ADMIN,
            Manifest.permission.ACCESS_FINE_LOCATION
    };

    /**
     * Save away any dynamic instance state in activity into the given Bundle,
     * to be later received in onCreate(Bundle) if the activity needs to be re-created.
     * Initialize any UI or API systems and continue to the main activity
     *
     * @param savedInstanceState Last saved state
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        // Get required permissions (File and GPS permissions)
        List<String> toRequest = new ArrayList<>();
        for (String permission : PERMISSIONS) {
            if (ContextCompat.checkSelfPermission(this, permission)
                    != PackageManager.PERMISSION_GRANTED) {
                toRequest.add(permission);
            }
        }
        if (toRequest.size() == 0) {
            startup();
        } else {
            ActivityCompat.requestPermissions(this, toRequest.toArray(new String[0]),
                    REQUEST_PERMISSIONS);
        }
    }

    /**
     * Called after the requestPermission pop-up window has received input
     * from the user ('Allow' or 'Deny').
     * @param requestCode Identifying code for the permission request
     * @param permissions All requested permissions stored as a String[] array
     * @param grantResults All respective result codes for each permission
     */
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions,
                                           @NonNull int[] grantResults) {
        if (requestCode == REQUEST_PERMISSIONS && grantResults.length > 0) {
            for (int result : grantResults) {
                if (result != PackageManager.PERMISSION_GRANTED) {
                    // Fail, exit app
                    Toast.makeText(getBaseContext(), R.string.prompt_permission_request_denied,
                            Toast.LENGTH_LONG).show();
                    finish();
                    return;
                }
            }

            startup();
        }
    }

    private void startup() {
        Task.run(new Callable<Void>() {
            @Override
            public Void call() throws Exception {
                Thread.sleep(2500);

                // Start main activity
                Intent intent = new Intent(StartupActivity.this, MainActivity.class);
                startActivity(intent);
                finish();

                return null;
            }
        });
    }
}
