package com.audeering.sensminer.model.situation;

import android.support.test.runner.AndroidJUnit4;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.HashMap;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.junit.Assert.assertNotNull;

/**
 * Created by MoritzTheile on 05.12.2016.
 */
@RunWith(AndroidJUnit4.class)
public class SituationCRUDServiceTest {

    @Test
    public void testCRUD() throws Exception {

        SituationCRUDService.instance().deleteAll();

        // TESTING fetch

        DTOFetchList<Situation> threeSituations = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

        assertNotNull(threeSituations);

        // there should be at least one Situation
        assertTrue(threeSituations.size()>0);

        // TESTING update

        Situation aSituation = threeSituations.iterator().next();

        aSituation.setLastUsageTimestamp(System.currentTimeMillis());

        SituationCRUDService.instance().update(aSituation );

        Situation aSituation_reloaded = SituationCRUDService.instance().get(aSituation.getId());

        Assert.assertNotNull(aSituation);
        Assert.assertNotNull(aSituation_reloaded);
        Assert.assertNotNull(aSituation_reloaded.getLastUsageTimestamp());

        assertEquals(aSituation.getLastUsageTimestamp(),aSituation_reloaded.getLastUsageTimestamp());

        // TESTING create

        Situation newSituation = new Situation();
        newSituation.setLastUsageTimestamp(System.currentTimeMillis());

        newSituation = SituationCRUDService.instance().create(newSituation);

        DTOFetchList<Situation> newResult = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

        assertEquals(threeSituations.size()+1, newResult.size());

        // TESTING delete

        SituationCRUDService.instance().delete(newSituation.getId());

        newResult = SituationCRUDService.instance().fetchList(new Page(), new FetchQuery());

        assertEquals(threeSituations.size(), newResult.size());


    }


}