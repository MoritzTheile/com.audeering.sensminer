package com.audeering.sensminer.model.situation.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.audeering.sensminer.MainActivity;
import com.audeering.sensminer.R;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;

/**
 * Created by MoritzTheile on 03.01.2017.
 */

public class SituationFormView extends LinearLayout {

    public SituationFormView(Context context) {
        super(context);
        init(null, 0);
    }


    public SituationFormView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SituationFormView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }
    public void bind(final Situation situation) {

        Configuration configuration = ConfigurationCRUDService.instance().fetchList(null, null).iterator().next();
        {
            Spinner spinner = (Spinner) findViewById(R.id.activitySpinner);
            final String[] values = new String[configuration.getSituationActivityValues().size()];
            configuration.getSituationActivityValues().toArray(values);
            String current = situation.getActivity();
            int selectedIndex = -1;
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(current)) {
                    selectedIndex = i;
                    break;
                }
            }
            final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, values);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(selectedIndex);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + arrayAdapter.getItem(i) + " clicked", Toast.LENGTH_LONG).show();

                    situation.setActivity((String) arrayAdapter.getItem(i));
                    SituationCRUDService.instance().update(situation);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        {
            Spinner spinner = (Spinner) findViewById(R.id.positionSpinner);
            final String[] values = new String[configuration.getSituationMobileStorageValues().size()];
            configuration.getSituationMobileStorageValues().toArray(values);
            String current = situation.getMobileStorage();
            int selectedIndex = -1;
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(current)) {
                    selectedIndex = i;
                    break;
                }
            }
            final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, values);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(selectedIndex);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + arrayAdapter.getItem(i) + " clicked", Toast.LENGTH_LONG).show();

                    situation.setMobileStorage((String) arrayAdapter.getItem(i));
                    SituationCRUDService.instance().update(situation);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }
        {
            Spinner spinner = (Spinner) findViewById(R.id.environmentSpinner);
            final String[] values = new String[configuration.getSituationEnvironmentValues().size()];
            configuration.getSituationEnvironmentValues().toArray(values);
            String current = situation.getEnvironment();
            int selectedIndex = -1;
            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(current)) {
                    selectedIndex = i;
                    break;
                }
            }
            final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, values);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(selectedIndex);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + arrayAdapter.getItem(i) + " clicked", Toast.LENGTH_LONG).show();

                    situation.setEnvironment((String) arrayAdapter.getItem(i));
                    SituationCRUDService.instance().update(situation);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        {
            Spinner spinner = (Spinner) findViewById(R.id.auxiliarySpinner);
            final String[] values = new String[configuration.getSituationAuxiliaryValues().size()];
            configuration.getSituationAuxiliaryValues().toArray(values);
            String current = situation.getAuxiliary();

            int selectedIndex = -1;

            for (int i = 0; i < values.length; i++) {
                if (values[i].equals(current)) {
                    selectedIndex = i;
                    break;
                }
            }

            final ArrayAdapter arrayAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, values);
            spinner.setAdapter(arrayAdapter);
            spinner.setSelection(selectedIndex);
            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
                @Override
                public void onItemSelected(AdapterView adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + arrayAdapter.getItem(i) + " clicked", Toast.LENGTH_LONG).show();

                    situation.setAuxiliary((String) arrayAdapter.getItem(i));
                    SituationCRUDService.instance().update(situation);

                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }


    }
    private void init(AttributeSet attrs, int defStyle) {

        //TODO

    }



}
