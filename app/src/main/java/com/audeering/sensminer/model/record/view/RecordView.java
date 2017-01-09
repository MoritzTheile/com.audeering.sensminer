package com.audeering.sensminer.model.record.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.audeering.sensminer.R;
import com.audeering.sensminer.RecordsActivity;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;
import com.audeering.sensminer.sensors.AudioSensor;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MoritzTheile on 03.01.2017.
 */

public class RecordView extends LinearLayout {

    public RecordView(Context context) {
        super(context);
        init(null, 0);
    }

    public RecordView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public RecordView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    public void bind(final Record record) {

        TextView titleTv = (TextView) this.findViewById(R.id.rec_startime);
        if(record == null  ){
            titleTv.setText("something went wrong");
        }else{
            titleTv.setText(getName(record) + "\n" + timestampToString(record.getStartTime()) + " (" + lengthLabel(record.getStartTime(), record.getEndTime()) + ")");
        }

        findViewById(R.id.recordDeleteMe).setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteRecord(record.getId());
            }
        });

//        findViewById(R.id.recordPlayAudio).setOnClickListener(new OnClickListener(){
//            @Override
//            public void onClick(View v) {
//                playAudio(record);
//            }
//        });
    }

//    private void playAudio(Record record) {
//        String audioFile = "file://" + AudioSensor.getFileName(record) + ".wav";
//        Uri uri = Uri.parse(audioFile);
//        Log.i("TAG", "playAudio: " + uri);
//        Intent playIntent = new Intent(Intent.ACTION_VIEW, uri);
//        playIntent.setType("audio/wav");
//        try {
//            getContext().startActivity(playIntent);
//        } catch (Exception ignore) {
//            Toast.makeText(getContext(), "No application found to play " + audioFile, Toast.LENGTH_LONG).show();
//        }
//    }

    private String getName(Record record) {
        if(record.getSituation()==null){
            return "no situation available";
        }
        return record.getSituation().getName();
    }


    private String lengthLabel(Long startTime, Long endTime) {

        String label = "";
        if(startTime == null || endTime == null){
            return label;
        }

        long seconds = (endTime-startTime)/1000;

        long minutes = seconds / 60;
        seconds = seconds % 60;

        if(minutes > 0){
            label += minutes + " min.";
        }

        label += " " + seconds + " sec.";


        return label;
    }
    private void deleteRecord(final String recordId) {

        new AlertDialog.Builder(getContext())
                .setTitle("Delete")
                .setMessage("Do you really want to delete the Record")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        RecordCRUDService.instance().delete(recordId);
                        //Hack: this shall be replaced by a clean MVC-Pattern.
                        RecordView.this.removeAllViews();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void init(AttributeSet attrs, int defStyle) {

        // nothing to do?

    }

    private static String timestampToString(Long timestamp){
        if(timestamp == null){
            return "no start time";
        }
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm dd.MM.yyyy");
        return sdf.format(new Date(timestamp));
    }



}
