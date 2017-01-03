package com.audeering.sensminer.model.situation.view;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.audeering.sensminer.MainActivity;
import com.audeering.sensminer.R;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;

import java.util.List;

/**
 * Created by MoritzTheile on 03.01.2017.
 */

public class SituationBoxView extends LinearLayout {

    private Configuration configuration;

    public SituationBoxView(Context context) {
        super(context);
        init(null, 0);
    }

    public SituationBoxView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init(attrs, 0);
    }

    public SituationBoxView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init(attrs, defStyle);
    }

    private void init(AttributeSet attrs, int defStyle) {

        configuration = ConfigurationCRUDService.instance().fetchList(null, null).iterator().next();

        if(configuration.getSelectedSituation() == null){

            final DTOFetchList<Situation> situations = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

            if(situations.size()>0) {
                //having any situation as default:
                configuration.setSelectedSituation(situations.get(0));
                ConfigurationCRUDService.instance().update(configuration);
            }

        }

    }

    public void render(){

        { // rendering the chooser

            Spinner spinner = (Spinner) findViewById(R.id.situationSpinner);
            final DTOFetchList<Situation> situations = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

            final SituationArrayAdapter situationArrayAdapter = new SituationArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, situations);

            spinner.setAdapter(situationArrayAdapter);

            spinner.setSelection(situationArrayAdapter.getPosition(configuration.getSelectedSituation()));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + situationArrayAdapter.getItem(i).getName() + " clicked", Toast.LENGTH_LONG).show();
                    configuration.setSelectedSituation(situationArrayAdapter.getItem(i));
                    renderSituationForm();
                }

                @Override
                public void onNothingSelected(AdapterView<?> adapterView) {

                }
            });
        }

        renderSituationForm();

    }

    public void renderSituationForm(){ // rendering the form

        FrameLayout situationFormSlot =  (FrameLayout) findViewById(R.id.situationFormSlot);

        situationFormSlot.removeAllViews();

        SituationFormView situationFormView = (SituationFormView) LayoutInflater.from(getContext()).inflate(R.layout.situation_form_view, null);

        situationFormSlot.addView(situationFormView);

        situationFormView.bind(configuration.getSelectedSituation());

    }

    @Override
    protected void onAttachedToWindow() {

        super.onAttachedToWindow();

        findViewById(R.id.situationDeleteMe).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deleteCurrentSituation();
            }
        });
    }

    private void deleteCurrentSituation() {

        new AlertDialog.Builder(getContext())
                .setTitle("Delete")
                .setMessage("Do you really want to delete this  Situation")
                .setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                        deleteExecute();
                    }
                })
                .setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                }).show();
    }

    private void deleteExecute() {
        Toast.makeText(getContext(), "deleted...", Toast.LENGTH_SHORT).show();
    }


}
