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

        AbstrTrackConf accelerationTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.ACCELEROMETER.name());
        accelerationTrackConf.setEnabled(true);
        TrackConfCRUDService.instance().update(accelerationTrackConf);

        AbstrTrackConf audioTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name());
        audioTrackConf.setEnabled(true);
        TrackConfCRUDService.instance().update(audioTrackConf);

        AbstrTrackConf locationTrackConf = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.LOCATION.name());
        locationTrackConf.setEnabled(true);
        TrackConfCRUDService.instance().update(locationTrackConf);

        assertTrue(TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.ACCELEROMETER.name()).isEnabled());
        assertTrue(TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name()).isEnabled());
        assertTrue(TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.LOCATION.name()).isEnabled());

        // TESTING update

        audioTrackConf.setEnabled(false);
        TrackConfCRUDService.instance().update(audioTrackConf);
        AbstrTrackConf audioTrackConf_reloaded = TrackConfCRUDService.instance().get(Configuration.TRACKTYPE.AUDIO.name());
        assertTrue(!audioTrackConf_reloaded.isEnabled());

    }


}