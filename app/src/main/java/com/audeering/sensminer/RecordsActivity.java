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


public class RecordsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGui();
    }

    private void setupGui() {
        setContentView(R.layout.settings);
        createSetting("Record1", true, null);
        createSetting("Record2", false, null);
        createSetting("Record3", true, null);
    }

    private void createSetting(String label, boolean defaultOn, CompoundButton.OnCheckedChangeListener listener) {
        ViewGroup settingsLayout = (ViewGroup) findViewById(R.id.settingsLayout);
        View setting = LayoutInflater.from(this).inflate(R.layout.switch_preference, settingsLayout, false);
        TextView titleTv = (TextView) setting.findViewById(R.id.title);
        titleTv.setText(label);
        Switch onOffSwitch = (Switch) setting.findViewById(R.id.onOffswitch);
        onOffSwitch.setChecked(defaultOn);
        onOffSwitch.setOnCheckedChangeListener(listener);
        settingsLayout.addView(setting);
    }


}
