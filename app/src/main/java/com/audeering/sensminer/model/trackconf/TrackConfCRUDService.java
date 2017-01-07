package com.audeering.sensminer.model.trackconf;

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

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class TrackConfCRUDService implements CRUDService<AbstrTrackConf, FetchQuery> {

    @Override
    public DTOFetchList<AbstrTrackConf> fetchList(Page page, FetchQuery query) {

        DTOFetchList<AbstrTrackConf> result = new DTOFetchList<>();

        for(Configuration.TRACKTYPE tracktype : Configuration.TRACKTYPE.values()){
            result.add(get(tracktype.name()));
        }

        return result;

    }

    /**
     * Attention, this is a crud service on a fixed set of objects.
     * The id has to be one of the TRACKTYPE enum names.
     */
    @Override
    public AbstrTrackConf get(String nameOfTracktype) {

        Configuration.TRACKTYPE tracktype = Configuration.TRACKTYPE.valueOf(nameOfTracktype);

        AbstrTrackConf abstrTrackConf = loadFromFile(tracktype);

        if (abstrTrackConf == null) {

            if(Configuration.TRACKTYPE.ACCELEROMETER.equals(tracktype)){
                abstrTrackConf = new AccelerationTrackConf();
            }else  if(Configuration.TRACKTYPE.AUDIO.equals(tracktype)){
                abstrTrackConf = new AudioTrackConf();
            }else  if(Configuration.TRACKTYPE.LOCATION.equals(tracktype)){
                abstrTrackConf = new LocationTrackConf();
            }

            saveToFile(abstrTrackConf);

        }

        return abstrTrackConf;

    }

    @Override
    public void update(AbstrTrackConf dto) {

        saveToFile(dto);

   }

    @Override
    public AbstrTrackConf create(AbstrTrackConf dto) {
        throw new RuntimeException("This is a CRUD service on a fixed set of objects, so creation is not possible.");
    }

    @Override
    public void delete(String dtoId) {
        throw new RuntimeException("This is a CRUD service on a fixed set of objects, so deletion is not possible.");
    }


    private void saveToFile(AbstrTrackConf abstrTrackConf) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            File file = FileService.createNewTrackConfFile(abstrTrackConf.getTrackType());

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, abstrTrackConf);

        } catch (Exception e){

            e.printStackTrace();

        }

    }

    private AbstrTrackConf loadFromFile(Configuration.TRACKTYPE tracktype) {

        try {

            ObjectMapper mapper = new ObjectMapper();
            mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

            File file = FileService.getExistingTrackConfFile(tracktype);
            AbstrTrackConf loadedObject = mapper.readValue(file, AbstrTrackConf.class);

            return loadedObject;

        }catch(Exception e ) {

            System.out.println("Exception: " + e.getMessage() );
            e.printStackTrace();

        }

        return null;
    }

    //SINGLETON

    private static TrackConfCRUDService instance;

    public static TrackConfCRUDService instance(){
        if(instance == null){
            instance = new TrackConfCRUDService();
        }
        return instance;
    }

}
