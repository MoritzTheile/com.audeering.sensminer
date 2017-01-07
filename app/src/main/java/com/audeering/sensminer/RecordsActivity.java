package com.audeering.sensminer;


import android.os.Bundle;
import android.preference.PreferenceActivity;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import android.widget.Switch;
import android.widget.TextView;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;


public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.records);


        DTOFetchList<Record> records = RecordCRUDService.instance().fetchList(new Page(), new FetchQuery());

        for(Record record : records){

            ViewGroup recordsLayout = (ViewGroup) findViewById(R.id.recordsLayout);

            View setting = LayoutInflater.from(this).inflate(R.layout.record, recordsLayout, false);
            TextView titleTv = (TextView) setting.findViewById(R.id.rec_startime);
            titleTv.setText("bibabu");

            recordsLayout.addView(setting);

        }



    }


}
