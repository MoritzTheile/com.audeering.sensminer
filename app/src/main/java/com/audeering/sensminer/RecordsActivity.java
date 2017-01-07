package com.audeering.sensminer;


import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;
import com.audeering.sensminer.model.record.view.RecordView;


public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        setContentView(R.layout.records);

        DTOFetchList<Record> records = RecordCRUDService.instance().fetchList(new Page(), new FetchQuery());

        for(Record record : records){

            ViewGroup recordsLayout = (ViewGroup) findViewById(R.id.recordsLayout);

            RecordView recordView = (RecordView) LayoutInflater.from(this).inflate(R.layout.record, recordsLayout, false);
            recordView.bind(record);
            recordsLayout.addView(recordView);

        }

    }


}
