package com.audeering.sensminer.model;

import android.support.test.runner.AndroidJUnit4;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.record.track.AbstrTrack;
import com.audeering.sensminer.model.situation.Situation;
import com.audeering.sensminer.model.situation.SituationCRUDService;
import com.audeering.sensminer.model.trackconf.AbstrTrackConf;
import com.audeering.sensminer.model.trackconf.AccelerationTrackConf;
import com.audeering.sensminer.model.trackconf.TrackConfCRUDService;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by MoritzTheile on 05.12.2016.
 */
@RunWith(AndroidJUnit4.class)
public class TrackConfCRUDServiceTest {

    @Test
    public void testCRUD() throws Exception {
  // TESTING fetch

        DTOFetchList<AbstrTrackConf> threeTrackConfs = TrackConfCRUDService.instance().fetchList(new Page(), new FetchQuery());

        assertNotNull(threeTrackConfs);

        // there should be at least one Situation
        assertTrue(threeTrackConfs.size()==3);

        System.out.println("abstrTrackConf printing threeTrackConfs ");
        for(AbstrTrackConf abstrTrackConf : threeTrackConfs){
            System.out.println("    abstrTrackConf = " +  abstrTrackConf);
            System.out.println("    abstrTrackConf.isEnabled() = " +  abstrTrackConf.isEnabled());
        }

        AbstrTrackConf accelerationTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.ACCELEROMETER.name());
        AbstrTrackConf audioTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name());
        AbstrTrackConf locationTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.LOCATION.name());

        assertTrue(accelerationTrackConf.isEnabled());
        assertTrue(audioTrackConf.isEnabled());
        assertTrue(locationTrackConf.isEnabled());

        // TESTING update

        audioTrackConf.setEnabled(false);
        TrackConfCRUDService.instance().update(audioTrackConf);
        AbstrTrackConf audioTrackConf_reloaded = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name());
        assertTrue(!audioTrackConf.isEnabled());

    }


}