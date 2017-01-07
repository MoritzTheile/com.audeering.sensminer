package com.audeering.sensminer.model.record;

import com.audeering.sensminer.model.FileUtils;
import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.configuration.Configuration;
import com.audeering.sensminer.model.situation.*;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class RecordCRUDService implements CRUDService<Record, FetchQuery> {


    @Override
    public DTOFetchList<Record> fetchList(Page page, FetchQuery query) {

        DTOFetchList<Record> result = new DTOFetchList<>();

        for (String recordId : FileService.getRecordIds()) {
            result.add(get(recordId));
        }

        return result;

    }

    @Override
    public Record get(String dtoId) {

        return loadFromFile(dtoId);

    }

    @Override
    public void update(Record dto) {
        saveToFile(dto);
    }

    @Override
    public Record create(Record dto) {

        if (dto.getId() != null) {
            throw new RuntimeException("Record dto can not be created because it has already an id: " + dto.getId());
        }

        // this is a dirty hack to prevent same ids, but in this case not critical
        try {
            Thread.sleep(1100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd_HHmmss");

        dto.setId(sdf.format(new Date()));

        saveToFile(dto);

        return dto;

    }

    @Override
    public void delete(String dtoId) {

        FileService.deleteRecordDir(dtoId);

    }

    public File getDataDir(Record record, Configuration.TRACKTYPE tracktype){
        File dataDir = new File(FileService.getRecordDir(record.getId())+ "/track_"+tracktype.name().toLowerCase());
        if(!dataDir.exists()){
            dataDir.mkdirs();
        }
        return dataDir;
    }

    private void saveToFile(Record record) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            File file = FileService.createNewRecordFile(record.getId());

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, record);

        } catch (Exception e) {

            e.printStackTrace();
        }
    }

    private Record loadFromFile(String recordId) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            File file = FileService.getExistingRecordFile(recordId);
            Record record = mapper.readValue(file, Record.class);

            return record;

        } catch (Exception e) {

            System.out.println("Exception: " + e.getMessage());
            e.printStackTrace();

        }

        return null;
    }


    //SINGLETON

    private static RecordCRUDService instance;

    public static RecordCRUDService instance() {
        if (instance == null) {
            instance = new RecordCRUDService();
        }
        return instance;
    }

}
