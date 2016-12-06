package com.audeering.sensminer.model.record;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class RecordCRUDService implements CRUDService<Record, FetchQuery> {
    @Override
    public DTOFetchList<Record> fetchList(Page page, FetchQuery query) {
        return null;
    }

    @Override
    public Record get(String dtoId) {
        return null;
    }

    @Override
    public Record update(Record dto) {
        return null;
    }

    @Override
    public Record create(Record dto) {
        return null;
    }

    @Override
    public void delete(String dtoId) {

    }
    //SINGLETON

    private static RecordCRUDService instance;

    public static RecordCRUDService instance(){
        if(instance == null){
            instance = new RecordCRUDService();
        }
        return instance;
    }

}
