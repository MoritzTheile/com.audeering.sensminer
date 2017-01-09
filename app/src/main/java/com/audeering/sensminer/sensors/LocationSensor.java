package com.audeering.sensminer.sensors;

import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.util.Log;

import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class LocationSensor {
    private static final long MIN_TIME = 1000;
    private static final float MIN_DIST = 1;
    private static LocationManager locationManager;
    private static final String TAG = LocationSensor.class.getName();

    private static LocationListener listener = new LocationListener() {

        @Override
        public void onLocationChanged(Location location) {

            PrintStream printStream = new PrintStream(fos);
            printStream.print(formatTime(location.getTime())+";"+location.getLatitude()+";"+location.getLongitude()+";"+location.getAccuracy()+"\n");
        }

        private String formatTime(long time){
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");
            return sdf.format(new Date(time));
        }

        @Override
        public void onStatusChanged(String s, int i, Bundle bundle) {
            Log.d(TAG, "onStatusChanged() called with: s = [" + s + "], i = [" + i + "], bundle = [" + bundle + "]");
        }

        @Override
        public void onProviderEnabled(String s) {
            Log.d(TAG, "onProviderEnabled() called with: s = [" + s + "]");
        }

        @Override
        public void onProviderDisabled(String s) {
            Log.d(TAG, "onProviderDisabled() called with: s = [" + s + "]");
        }
    };
    private static FileOutputStream fos;

    @SuppressWarnings("MissingPermission")
    public static void startRecording(Context context, Record record) {
        try {

            fos = new FileOutputStream(RecordCRUDService.instance().getDataDir(record, Configuration.TRACKTYPE.LOCATION)+"/location.csv");

            locationManager = (LocationManager) context.getSystemService(Context.LOCATION_SERVICE);

            locationManager.requestLocationUpdates(LocationManager.GPS_PROVIDER, MIN_TIME, MIN_DIST, listener);

        } catch (Exception e) {
            Log.e(TAG, "startRecording: ", e);
        }
    }

    @SuppressWarnings("MissingPermission")
    public static void stopRecording() {
        try {
            locationManager.removeUpdates(listener);
            fos.flush();
            fos.close();
        } catch (Exception e) {
            Log.e(TAG, "stopRecording: ", e);
        }
    }
}
