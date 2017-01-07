package com.audeering.sensminer.model.record.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;

import com.audeering.sensminer.R;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;

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



    }
    private void init(AttributeSet attrs, int defStyle) {

        // nothing to do?

    }



}
