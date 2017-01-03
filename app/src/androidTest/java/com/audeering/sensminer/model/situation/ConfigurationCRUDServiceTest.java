package com.audeering.sensminer.model.situation;

import android.support.test.runner.AndroidJUnit4;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;

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
public class ConfigurationCRUDServiceTest {

    private static final String TESTSTRING ="teststring";

    @Test
    public void testCRUD() throws Exception {

        Configuration configuration =  ConfigurationCRUDService.instance().get(null);
        assertNotNull(configuration);
        configuration.setRecordDuration(TESTSTRING);
        ConfigurationCRUDService.instance().update(configuration);
        Configuration configuration_reloaded =  ConfigurationCRUDService.instance().get(null);
        assertNotNull(configuration_reloaded);
        assertEquals(TESTSTRING, configuration_reloaded.getRecordDuration());


    }


}