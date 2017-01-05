package com.audeering.sensminer.model;

import android.support.test.runner.AndroidJUnit4;

import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.configuration.ConfigurationCRUDService;
import com.audeering.sensminer.model.record.Record;
import com.audeering.sensminer.model.record.RecordCRUDService;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import static junit.framework.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

/**
 * Created by MoritzTheile on 05.12.2016.
 */
@RunWith(AndroidJUnit4.class)
public class RecordCRUDServiceTest {

    @Test
    public void testCRUD() throws Exception {

        DTOFetchList<Record> records =  RecordCRUDService.instance().fetchList(new Page(), new FetchQuery());
        assertNotNull(records);

        Record createdRecordOne = RecordCRUDService.instance().create(createRecord());
        Record createdRecordTwo = RecordCRUDService.instance().create(createRecord());

        DTOFetchList<Record> records_plusTwo =  RecordCRUDService.instance().fetchList(new Page(), new FetchQuery());

        Assert.assertEquals(records.size()+2 , records_plusTwo.size());

        long timestamp = System.currentTimeMillis();
        createdRecordOne.setEndTime(timestamp);

        RecordCRUDService.instance().update(createdRecordOne);

        Assert.assertEquals(timestamp,RecordCRUDService.instance().get(createdRecordOne.getId()).getEndTime() );

        RecordCRUDService.instance().delete(createdRecordOne.getId());
        RecordCRUDService.instance().delete(createdRecordTwo.getId());

        Assert.assertEquals(records.size() , RecordCRUDService.instance().fetchList(new Page(), new FetchQuery()).size());


    }

    private static Record createRecord(){
        Record record = new Record();

        return record;
    }


}