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
import android.widget.Toast;

import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.situation.SituationCRUDService;
import com.audeering.sensminer.model.trackconf.AbstrTrackConf;
import com.audeering.sensminer.model.trackconf.TrackConfCRUDService;

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

        for(AbstrTrackConf abstrTrackConf : TrackConfCRUDService.instance().fetchList(new Page(), new FetchQuery())){

            createSetting(abstrTrackConf);

        }

    }

    private void createSetting(final AbstrTrackConf abstrTrackConf){

        ViewGroup settingsLayout = (ViewGroup) findViewById(R.id.settingsLayout);
        View setting = LayoutInflater.from(this).inflate(R.layout.switch_preference, settingsLayout, false);
        TextView titleTv = (TextView) setting.findViewById(R.id.title);
        titleTv.setText(abstrTrackConf.getTrackType().name().toLowerCase());
        final Switch onOffSwitch = (Switch) setting.findViewById(R.id.onOffswitch);
        onOffSwitch.setChecked(abstrTrackConf.isEnabled());
        onOffSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked && !abstrTrackConf.isAvailable()){

                    Toast.makeText(SettingsActivity.this, "Track not available yet", Toast.LENGTH_LONG).show();
                    onOffSwitch.setChecked(false);

                }else {
                    abstrTrackConf.setEnabled(isChecked);
                    TrackConfCRUDService.instance().update(abstrTrackConf);
                }
            }
        });
        settingsLayout.addView(setting);

   }


}
