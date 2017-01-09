package com.audeering.sensminer;

import android.Manifest;
import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.situation.view.SituationBoxView;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import static android.support.v4.content.PermissionChecker.PERMISSION_GRANTED;

public class MainActivity extends AppCompatActivity implements OnStatusChangedListener {

    private static final int REQUEST_PERMISSIONS = 1;
    private static final String TAG = MainActivity.class.getName();
    private SensMinerService mService;
    private TextView startStopButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        
        setupGui();

    }

    @Override
    protected void onResume() {
        super.onResume();
        askForPermissions();
        Intent serviceIntent = new Intent(this, SensMinerService.class);
        bindService(serviceIntent, mConnection, Context.BIND_AUTO_CREATE);
    }

    @Override
    protected void onPause() {

        super.onPause();
        unbindService(mConnection);

    }

    private void setupGui() {

        setupRecordDurationSpinner();

        startStopButton = (TextView) findViewById(R.id.startStopButton);
        startStopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(isServiceRunning)
                    stopRecording();
                else
                    startRecording();
            }
        });


        final FrameLayout situationBoxSlot = (FrameLayout) findViewById(R.id.situationBoxSlot);
        SituationBoxView situationBoxView = (SituationBoxView) LayoutInflater.from(this).inflate(R.layout.situation_box_view, null);
        situationBoxSlot.addView(situationBoxView);
        situationBoxView.render();

    }

    private void stopRecording() {

        Intent serviceIntent = new Intent(this, SensMinerService.class);
        serviceIntent.setAction(Intent.ACTION_DELETE);
        startService(serviceIntent);
    }

    private boolean isServiceRunning;
    private boolean mBound;

    /** Defines callbacks for service binding, passed to bindService() */
    private ServiceConnection mConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // We've bound to LocalService, cast the IBinder and get LocalService instance
            SensMinerService.LocalBinder binder = (SensMinerService.LocalBinder) service;
            mService = binder.getService();
            mBound = true;
            binder.addStatusChangedListener(MainActivity.this);
            isServiceRunning = binder.getIsRunning();
            statusChanged(isServiceRunning);
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            mBound = false;
        }

    };

    private void startRecording() {
        Intent serviceIntent = new Intent(this, SensMinerService.class);
        serviceIntent.setAction(Intent.ACTION_RUN);
        startService(serviceIntent);

    }



    private void setupRecordDurationSpinner() {

        Configuration configuration = ConfigurationCRUDService.instance().fetchList(null, null).iterator().next();

        Spinner spinner = (Spinner) findViewById(R.id.recordDurationSpinner);
        final String[] durations = new String[configuration.getRecordDurations().size()];
        configuration.getRecordDurations().keySet().toArray(durations);
        String current = configuration.getRecordDuration();
        int selectedIndex = -1;
        for (int i = 0; i < durations.length; i++) {
            if(durations[i].equals(current)) {
                selectedIndex = i;
                break;
            }
        }
        spinner.setAdapter(new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_spinner_dropdown_item, durations));
        spinner.setSelection(selectedIndex);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
//                Toast.makeText(MainActivity.this, "Item " + durations[i] + " clicked", Toast.LENGTH_LONG).show();
                Configuration configuration = ConfigurationCRUDService.instance().get(null);
                configuration.setRecordDuration(durations[i]);
                ConfigurationCRUDService.instance().update(configuration);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

   @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        //noinspection SimplifiableIfStatement
        switch (item.getItemId()) {
            case R.id.action_settings:
                Intent settingsIntent = new Intent(this, SettingsActivity.class);
                startActivity(settingsIntent);
                return true;
            case R.id.action_records:

                Intent recordsIntent = new Intent(this, RecordsActivity.class);
                startActivity(recordsIntent);

                return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void statusChanged(final boolean running) {

        isServiceRunning = running;

        runOnUiThread(new Runnable() {
                          @Override
                          public void run() {
                              startStopButton.setText(getString(running ? R.string.stop_recording : R.string.start_recording));
                          }
                      }
        );

    }

    private void askForPermissions() {
        List<String> requiredPermissions = new ArrayList<>();
        String[] allPermissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE, Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.ACCESS_FINE_LOCATION};
        for (String permission : allPermissions) {
            int permissionCheck = ContextCompat.checkSelfPermission(this, permission);
            if(permissionCheck != PERMISSION_GRANTED)
                requiredPermissions.add(permission);
        }
        if(requiredPermissions.size() == 0)
            return;
        String[] permissions = new String[requiredPermissions.size()];
        ActivityCompat.requestPermissions(this, requiredPermissions.toArray(permissions), REQUEST_PERMISSIONS);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG, "onActivityResult() called with: requestCode = [" + requestCode + "], resultCode = [" + resultCode + "], data = [" + data + "]");

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
                Toast.makeText(this, "RESULT CODE is "+ grantResults.length + (grantResults.length > 0 ? grantResults[0] : ""), Toast.LENGTH_LONG).show();
    }
}
