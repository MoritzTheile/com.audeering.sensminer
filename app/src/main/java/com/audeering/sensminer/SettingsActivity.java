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

/**
 * A {@link PreferenceActivity} that presents a set of application settings. On
 * handset devices, settings are presented as a single list. On tablets,
 * settings are split by category, with category headers shown to the left of
 * the list of settings.
 * <p>
 * See <a href="http://developer.android.com/design/patterns/settings.html">
 * Android Design: Settings</a> for design guidelines and the <a
 * href="http://developer.android.com/guide/topics/ui/settings.html">Settings
 * API Guide</a> for more information on developing a Settings UI.
 */
public class SettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setupGui();
    }

    private void setupGui() {
        setContentView(R.layout.settings);
        createSetting("Title", true, null);
        createSetting("Title2", false, null);
        createSetting("Title3", true, null);
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
