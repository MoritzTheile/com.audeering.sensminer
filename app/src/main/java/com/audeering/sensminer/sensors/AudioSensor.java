package com.audeering.sensminer.sensors;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.util.Log;

import com.audeering.sensminer.model.record.FileService;

import java.io.FileOutputStream;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class AudioSensor {
    private static final int SAMPLE_RATE = 44100;
    private static final int BUFSIZE = 8 * 2048;
    private static final String TAG = AudioSensor.class.getName();
    private static final int CHANNEL_CONFIG = AudioFormat.CHANNEL_IN_MONO;
    private static AudioRecord mAudioRecorder;
    private static boolean running;

    public static void stopRecording() {
        running = false;
    }
    public static void startRecording() {
        try {
            String mFileName = FileService.getPathToFile("audio");
            final FileOutputStream fos = new FileOutputStream(mFileName);
            int bufSize = 8 * AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, AudioFormat.ENCODING_PCM_16BIT);
            Log.i(TAG, "startRecording: bufsize = " + bufSize);
            mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, AudioFormat.ENCODING_PCM_16BIT, bufSize);
            int state = mAudioRecorder.getState();
            Log.i(TAG, "startRecording: " + state);
            mAudioRecorder.startRecording();
            running = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: Thread started");
                    byte[] buf = new byte[BUFSIZE];
                    while (running) {
                        try {
                            int len = mAudioRecorder.read(buf, 0, BUFSIZE);
                            if(len > 0) {
                                fos.write(buf, 0, len);
                                Log.i(TAG, "run: wrote byte: " + len);
                            } else {
                                Thread.sleep(100);
                            }
                        } catch (Exception e) {
                            Log.e(TAG, "run: ", e);
                            break;
                        }
                    }
                    Log.i(TAG, "run: end of while");
                    // cleanup
                    try {
                        mAudioRecorder.stop();
                        fos.flush();
                        fos.close();
                    } catch (Exception e) {
                        Log.e(TAG, "run: ", e);
                    }
                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, "startRecording: ", e);
        }
    }

}
