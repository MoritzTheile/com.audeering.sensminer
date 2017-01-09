package com.audeering.sensminer.sensors;

import android.media.AudioFormat;
import android.media.AudioRecord;
import android.media.MediaRecorder;
import android.support.annotation.NonNull;
import android.util.Log;

import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class AudioSensor {

    private static final int BITS_PER_SAMPLE = 16;
    private static int SAMPLE_RATE = 44100;
    private static int CHANNELS = 2;

    private static final String TAG = AudioSensor.class.getName();
    private static AudioRecord mAudioRecorder;
    private static boolean running;
    private static String mFileName;

    public static void stopRecording() {
        running = false;
    }

    private static int getBitsPerSampleToEncoding(int bitsPerSample) {
        switch (bitsPerSample) {
            case 16:
                return AudioFormat.ENCODING_PCM_16BIT;
            case 8:
                return AudioFormat.ENCODING_PCM_8BIT;
            default:
                throw new RuntimeException("unsupported bitspersample " + bitsPerSample);
        }
    }

    public static void startRecording(Record record) {

       /*

       {// init settings from filesystem

            AudioTrackConf audioTrackConf = (AudioTrackConf)TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name());
            if(audioTrackConf.getSampleRateInHz()!=null){
                SAMPLE_RATE = audioTrackConf.getSampleRateInHz();
            }
            if(audioTrackConf.getRecorderBPP()!=null){
                RECORDER_BPP = audioTrackConf.getRecorderBPP();
            }
            if(audioTrackConf.getNumberOfChannels()!=null){
                CHANNELS = audioTrackConf.getNumberOfChannels();
            }

        }
        */

        try {
            mFileName = getFileName(record, "audio");
            String csvFileName = getFileName(record, "audio.csv");
            final FileOutputStream fos = new FileOutputStream(mFileName);
            final FileOutputStream fosCsv = new FileOutputStream(csvFileName);
            final PrintStream printStream = new PrintStream(fosCsv);
            int CHANNEL_CONFIG = getChannelConfig(CHANNELS);
            final int encoding = getBitsPerSampleToEncoding(BITS_PER_SAMPLE);
            final int bufSize = 16 * AudioRecord.getMinBufferSize(SAMPLE_RATE, CHANNEL_CONFIG, encoding);
            Log.i(TAG, "startRecording: bufsize = " + bufSize);
            Log.i(TAG, "startRecording: SAMPLE_RATE = " + SAMPLE_RATE);
            Log.i(TAG, "startRecording: CHANNEL_CONFIG = " + CHANNEL_CONFIG);
            mAudioRecorder = new AudioRecord(MediaRecorder.AudioSource.MIC, SAMPLE_RATE, CHANNEL_CONFIG, encoding, bufSize);
            int state = mAudioRecorder.getState();
            Log.i(TAG, "startRecording: " + state);

            mAudioRecorder.startRecording();
            running = true;
            new Thread(new Runnable() {
                @Override
                public void run() {
                    Log.i(TAG, "run: Thread started");
                    byte[] buf = new byte[8 * 8192];
                    long bytesWritten = 0;
                    long startTime = System.currentTimeMillis();
                    long nextRelativeTimestampMs = 0;
                    final int bytesPerSample = BITS_PER_SAMPLE / 8 * CHANNELS;
                    final float bytesPerMs = 0.001f * SAMPLE_RATE * bytesPerSample;
                    final int recordingBufLengthMs = (int) (bufSize / bytesPerMs);
                    Log.i(TAG, "run: bpms " + bytesPerMs + " / rblms " + recordingBufLengthMs);
                    final int timestampStepMs = 1000;
                    int sampleOffset = 0;
                    while (running) {
                        try {
                            int len = mAudioRecorder.read(buf, 0, buf.length);
                            if(len > 0) {
                                fos.write(buf, 0, len);
                                bytesWritten += len;
                                long lastSampleTimeMs = (long) (bytesWritten / bytesPerMs);
                                Log.i(TAG, "run: wrote bytes" + len + " / "+ bytesWritten + " / " + lastSampleTimeMs + " " + nextRelativeTimestampMs + " ( " + bytesPerMs + ")");
                                if(lastSampleTimeMs >= nextRelativeTimestampMs) { // needs to write timestamp
                                    long sampleAtTimestamp = (long) (nextRelativeTimestampMs * bytesPerMs / bytesPerSample);
                                    printStream.print((sampleOffset + sampleAtTimestamp) + ";" + (startTime + nextRelativeTimestampMs) + "\n");
                                    Log.i(TAG, "run: timestamp " + sampleAtTimestamp + ";" + (startTime + nextRelativeTimestampMs));
                                    //write timestamp
                                    nextRelativeTimestampMs += timestampStepMs;
                                }
                                if(lastSampleTimeMs + startTime < System.currentTimeMillis() - recordingBufLengthMs - 100) { // detect overflow: reset
                                    Log.w(TAG, "run: overflow detected");
                                    sampleOffset += bytesWritten / bytesPerSample;
                                    bytesWritten = 0;
                                    startTime = System.currentTimeMillis();
                                    nextRelativeTimestampMs = 0;
                                }
//                                test code for forcing buffer overflow, DO NOT RELEASE
//                                else {
//                                    if(bytesWritten > 1700000) {
//                                        Log.w(TAG, "run: force overflow");
//                                        Thread.sleep(1500); // force overflow
//                                    }
//                                }
                            } else {
                                Thread.sleep(33);
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
                        mAudioRecorder.release();
                        fos.flush();
                        fos.close();
                        printStream.close();
                    } catch (Exception e) {
                        Log.e(TAG, "run: ", e);
                    }

                    copyWaveFile(mFileName, mFileName+".wav");

                    //new File(mFileName).delete();

                }
            }).start();
        } catch (Exception e) {
            Log.e(TAG, "startRecording: ", e);
        }
    }

    @NonNull
    public static String getFileName(Record record, String fileName) {
        return RecordCRUDService.instance().getDataDir(record, Configuration.TRACKTYPE.AUDIO) + "/" + fileName;
    }

    private static int getChannelConfig(int channels){

            if(channels==1){
                return AudioFormat.CHANNEL_IN_MONO;
            }
            if(channels==2){
                return AudioFormat.CHANNEL_IN_STEREO;
            }

        return -1;
    }

    private static void copyWaveFile(String inFilename, String outFilename){
        FileInputStream in = null;
        FileOutputStream out = null;
        long totalAudioLen = 0;
        long totalDataLen = totalAudioLen + 36;
        long longSampleRate = SAMPLE_RATE;
        int channels = CHANNELS;
        long byteRate = BITS_PER_SAMPLE * SAMPLE_RATE * channels/8;

        int BUFSIZE = 8 * 2048;


        byte[] data = new byte[BUFSIZE];

        try {

            in = new FileInputStream(inFilename);
            out = new FileOutputStream(outFilename);
            totalAudioLen = in.getChannel().size();
            totalDataLen = totalAudioLen + 36;

            WriteWaveFileHeader(out, totalAudioLen, totalDataLen,
                    longSampleRate, channels, byteRate);

            for(int len = in.read(data); len > 0; len = in.read(data)) {
                out.write(data, 0, len);
            }

            in.close();
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void WriteWaveFileHeader(
            FileOutputStream out, long totalAudioLen,
            long totalDataLen, long longSampleRate, int channels,
            long byteRate) throws IOException
    {
        byte[] header = new byte[44];

        header[0] = 'R';  // RIFF/WAVE header
        header[1] = 'I';
        header[2] = 'F';
        header[3] = 'F';
        header[4] = (byte) (totalDataLen & 0xff);
        header[5] = (byte) ((totalDataLen >> 8) & 0xff);
        header[6] = (byte) ((totalDataLen >> 16) & 0xff);
        header[7] = (byte) ((totalDataLen >> 24) & 0xff);
        header[8] = 'W';
        header[9] = 'A';
        header[10] = 'V';
        header[11] = 'E';
        header[12] = 'f';  // 'fmt ' chunk
        header[13] = 'm';
        header[14] = 't';
        header[15] = ' ';
        header[16] = 16;  // 4 bytes: size of 'fmt ' chunk
        header[17] = 0;
        header[18] = 0;
        header[19] = 0;
        header[20] = 1;  // format = 1
        header[21] = 0;
        header[22] = (byte) channels;
        header[23] = 0;
        header[24] = (byte) (longSampleRate & 0xff);
        header[25] = (byte) ((longSampleRate >> 8) & 0xff);
        header[26] = (byte) ((longSampleRate >> 16) & 0xff);
        header[27] = (byte) ((longSampleRate >> 24) & 0xff);
        header[28] = (byte) (byteRate & 0xff);
        header[29] = (byte) ((byteRate >> 8) & 0xff);
        header[30] = (byte) ((byteRate >> 16) & 0xff);
        header[31] = (byte) ((byteRate >> 24) & 0xff);
        header[32] = (byte) (2 * 16 / 8);  // block align
        header[33] = 0;
        header[34] = (byte) BITS_PER_SAMPLE;  // bits per sample
        header[35] = 0;
        header[36] = 'd';
        header[37] = 'a';
        header[38] = 't';
        header[39] = 'a';
        header[40] = (byte) (totalAudioLen & 0xff);
        header[41] = (byte) ((totalAudioLen >> 8) & 0xff);
        header[42] = (byte) ((totalAudioLen >> 16) & 0xff);
        header[43] = (byte) ((totalAudioLen >> 24) & 0xff);

        out.write(header, 0, 44);
    }

}
