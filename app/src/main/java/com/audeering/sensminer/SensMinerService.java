package com.audeering.sensminer;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.provider.MediaStore;
import android.support.v7.app.NotificationCompat;
import android.util.Log;

import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;
import com.audeering.sensminer.model.situation.SituationCRUDService;
import com.audeering.sensminer.sensors.AudioSensor;
import com.audeering.sensminer.sensors.LocationSensor;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class SensMinerService extends Service {
    private static final String TAG = SensMinerService.class.getName();
    // Binder given to clients
    private final LocalBinder mBinder = new LocalBinder();
    private boolean isRunning;

    private Record record;

    public SensMinerService() {
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        Log.i(TAG, "onStartCommand: " + intent.getAction());
        if(Intent.ACTION_RUN.equals(intent.getAction())) {
            start();
            return START_STICKY;
        } else if (Intent.ACTION_DELETE.equals(intent.getAction())) {
            stop();
            return START_NOT_STICKY;
        }
        return START_NOT_STICKY;
    }

    private void stop() {
        stopAudioService();
        stopLocationService();
        {
            record.setEndTime(System.currentTimeMillis());
            RecordCRUDService.instance().update(record);
            record = null;
        }
        setIsRunning(false);
        stopForeground(true);
        stopSelf();
    }

    private void start() {
        startNotification();
        setIsRunning(true);
        record = RecordCRUDService.instance().create(getNewRecord());
        startAudioService();
        startLocationService();
    }


    private Record getNewRecord() {
        Record record = new Record();
        record.setStartTime(System.currentTimeMillis());
        record.setSituation(SituationCRUDService.instance().getLastSelectedSituation());
        return record;
    }

    private void stopLocationService() {
        LocationSensor.stopRecording();
    }

    private void startLocationService() {
        LocationSensor.startRecording(this, record);
    }


    private void stopAudioService() {
        AudioSensor.stopRecording();
    }

    private void startAudioService() {
        AudioSensor.startRecording(record);
    }

    private void setIsRunning(boolean b) {
        isRunning = b;
        mBinder.notifyListeners();
    }

    private void startNotification() {
        Notification notification = createNotification();
        startForeground(1, notification);
    }

    private Notification createNotification() {
        Intent startMainActivityIntent = new Intent(this, MainActivity.class);
        startMainActivityIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent startMainActivityPendingIntent = PendingIntent.getActivity(this, 2, startMainActivityIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        Intent killServiceIntent = new Intent(this, SensMinerService.class);
        killServiceIntent.setAction(Intent.ACTION_DELETE);
        PendingIntent killServicePendingIntent = PendingIntent.getService(this, 3, killServiceIntent, PendingIntent.FLAG_ONE_SHOT);
        return new NotificationCompat.Builder(this)
                .setSmallIcon(android.R.drawable.ic_notification_clear_all)
                .setContentTitle("SensMiner running")
                .addAction(android.R.drawable.ic_delete, "Kill service", killServicePendingIntent)
                .setContentIntent(startMainActivityPendingIntent)
                .build();
    }

    /**
     * Class used for the client Binder.  Because we know this service always
     * runs in the same process as its clients, we don't need to deal with IPC.
     */
    public class LocalBinder extends Binder {
        private ArrayList<OnStatusChangedListener> listeners = new ArrayList<>();

        SensMinerService getService() {
            // Return this instance of LocalService so clients can call public methods
            return SensMinerService.this;
        }

        public boolean getIsRunning() {
            return isRunning;
        }

        public void addStatusChangedListener(OnStatusChangedListener listener) {
            listeners.add(listener);
        }

        public void removeStatusChangedListener(OnStatusChangedListener listener) {
            listeners.remove(listener);
        }

        public void notifyListeners() {
            for (OnStatusChangedListener listener : listeners) {
                listener.statusChanged(isRunning);
            }
        }
    }

    @Override
    public IBinder onBind(Intent intent) {
        return mBinder;
    }


}
