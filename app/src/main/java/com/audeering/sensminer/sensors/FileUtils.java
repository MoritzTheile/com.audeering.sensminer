package com.audeering.sensminer.sensors;

import android.os.Environment;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class FileUtils {
    public static String getFileForService(String service) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.wav";
    }
}
