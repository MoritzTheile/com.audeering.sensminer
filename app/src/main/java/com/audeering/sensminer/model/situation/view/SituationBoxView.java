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

    }

    public void render(){

        { // rendering the chooser

            Spinner spinner = (Spinner) findViewById(R.id.situationSpinner);

           // spinner.removeAllViews();

            final DTOFetchList<Situation> situations = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

            final SituationArrayAdapter situationArrayAdapter = new SituationArrayAdapter(getContext(), android.R.layout.simple_spinner_dropdown_item, situations);

            spinner.setAdapter(situationArrayAdapter);

            spinner.setSelection(situationArrayAdapter.getPosition(SituationCRUDService.instance().getLastSelectedSituation()));

            spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

                @Override
                public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {

                    Toast.makeText(getContext(), "Item " + situationArrayAdapter.getItem(i).getName() + " clicked", Toast.LENGTH_LONG).show();
                    SituationCRUDService.instance().setLastSelectedSituation(situationArrayAdapter.getItem(i));
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

        situationFormView.bind(SituationCRUDService.instance().getLastSelectedSituation());

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

        findViewById(R.id.situationCreate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createSituation();
            }
        });

    }

    private void createSituation() {

        Situation currentSituation = SituationCRUDService.instance().getLastSelectedSituation();

        Situation newSituation = new Situation();

        newSituation = SituationCRUDService.instance().create(newSituation);

        newSituation.setName("new Situation");

        newSituation.setMobileStorage(currentSituation.getMobileStorage());
        newSituation.setEnvironment(currentSituation.getEnvironment());
        newSituation.setAuxiliary(currentSituation.getAuxiliary());
        newSituation.setActivity(currentSituation.getActivity());

        SituationCRUDService.instance().update(newSituation);

        SituationCRUDService.instance().setLastSelectedSituation(newSituation);

        render();
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

        SituationCRUDService.instance().delete(SituationCRUDService.instance().getLastSelectedSituation().getId());
        render();

    }


}
