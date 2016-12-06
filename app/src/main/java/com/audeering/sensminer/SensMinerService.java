package com.audeering.sensminer;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SensMinerService extends Service {
    public SensMinerService() {
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
