package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class AbstrTrackConfCRUDService implements CRUDService<AbstrTrackConf, FetchQuery> {

    @Override
    public DTOFetchList<AbstrTrackConf> fetchList(Page page, FetchQuery query) {
        return null;
    }

    @Override
    public AbstrTrackConf get(String dtoId) {
        return null;
    }

    @Override
    public AbstrTrackConf update(AbstrTrackConf dto) {
        return null;
    }

    @Override
    public AbstrTrackConf create(AbstrTrackConf dto) {
        return null;
    }

    @Override
    public void delete(String dtoId) {

    }

    //SINGLETON

    private static AbstrTrackConfCRUDService instance;

    public static AbstrTrackConfCRUDService instance(){
        if(instance == null){
            instance = new AbstrTrackConfCRUDService();
        }
        return instance;
    }

}
