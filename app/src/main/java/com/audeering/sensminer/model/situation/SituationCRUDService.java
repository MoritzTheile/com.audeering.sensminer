package com.audeering.sensminer.model.situation;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class SituationCRUDService implements CRUDService<Situation, FetchQuery> {

    @Override
    public DTOFetchList<Situation> fetchList(Page page, FetchQuery query) {
        DTOFetchList<Situation> result = loadFromFile();
        if (result.size() == 0) {
            result = loadDefault();
            saveToFile(result);
        }
        return result;
    }

    @Override
    public Situation get(String dtoId) {
        return null;
    }

    @Override
    public Situation update(Situation dto) {
        return null;
    }

    @Override
    public Situation create(Situation dto) {
        return null;
    }

    @Override
    public void delete(String dtoId) {

    }

    private void saveToFile(DTOFetchList<Situation> result) {
        //TODO
    }

    private DTOFetchList<Situation> loadFromFile() {
        DTOFetchList<Situation> result = new DTOFetchList<>();
        //TODO
        return result;
    }

    private DTOFetchList<Situation> loadDefault() {
        DTOFetchList<Situation> result = new DTOFetchList<>();
        result.add(createDefault1());
        result.add(createDefault2());
        result.add(createDefault3());
        return result;
    }

    private Situation createDefault1() {
        Situation situation = new Situation();
        situation.setName("Driving car");
        situation.setActivity("Driving Car");
        situation.setAuxiliary("Speech two");
        situation.setEnvironment("Car");
        situation.setMobileStorage("On Surface");
        return situation;
    }
    
    private Situation createDefault2() {
        Situation situation = new Situation();
        situation.setName("Lunch");
        situation.setActivity("Sitting");
        situation.setAuxiliary("Speech multiple");
        situation.setEnvironment("Restaurant");
        situation.setMobileStorage("On Surface");
        return situation;
    }

    private Situation createDefault3() {
        Situation situation = new Situation();
        situation.setName("Taking a walk");
        situation.setActivity("Walking");
        situation.setAuxiliary("Speech two");
        situation.setEnvironment("Outside");
        situation.setMobileStorage("In Pocket");
        return situation;
    }


    //SINGLETON

    private static SituationCRUDService instance;

    public static SituationCRUDService instance(){
        if(instance == null){
            instance = new SituationCRUDService();
        }
        return instance;
    }
}
