package com.audeering.sensminer.model.record.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Switch;
import android.widget.TextView;

import com.audeering.sensminer.R;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;

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
            titleTv.setText(timestampToString(record.getStartTime()));
        }

        ImageView situationDeleteMe = (ImageView) this.findViewById(R.id.situationDeleteMe);
        situationDeleteMe.setOnClickListener(new OnClickListener(){
            @Override
            public void onClick(View v) {
                deleteRecord(record.getId());
            }
        });
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
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss dd.MM.yyyy");
        return sdf.format(new Date(timestamp));
    }



}
