package com.audeering.sensminer.model.situation;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.model.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class SituationCRUDService implements CRUDService<Situation, FetchQuery> {

    public void deleteAll() throws Exception {
        FileService.createNewSituationsFile();
    }

    @Override
    public DTOFetchList<Situation> fetchList(Page page, FetchQuery query) {

        DTOFetchList<Situation> fetchList = new DTOFetchList<>();

        Map<String, Situation> situationIndex = getSituationIndex();

        fetchList.setTotalSize(situationIndex.size());

        fetchList.addAll(situationIndex.values());

        Collections.sort(fetchList);

        return fetchList;

    }

    @Override
    public Situation get(String dtoId) {

        return getSituationIndex().get(dtoId);

    }


    public Situation getLastSelectedSituation() {

        DTOFetchList<Situation> fetchList = fetchList(new Page(), new FetchQuery());

        if(fetchList.size() == 0){
            return null;
        }

        Collections.sort(fetchList);

        Situation situation = fetchList.get(0);

        return situation;

    }

    public void setLastSelectedSituation(String situationId){

        Situation situation = get(situationId);

        situation.setLastUsageTimestamp(System.currentTimeMillis());

        update(situation);

    }


    @Override
    public void update(Situation dto) {

        System.out.println("asdfff Updating situationDTO");
        System.out.println("asdfff     name = " + dto.getName());
        System.out.println("asdfff     environment = " + dto.getEnvironment());

        Map<String, Situation> situationIndex = getSituationIndex();

        if(situationIndex.containsKey(dto.getId())){

            situationIndex.put(dto.getId(), dto);

        }

        saveToFile(situationIndex);

  }

    @Override
    public Situation create(Situation dto) {

        if(dto.getId() != null){
            throw new RuntimeException("DTO is not created because it has already an id: " + dto.getId());
        }

        dto.setId(dto.hashCode()+"");

        Map<String, Situation> situationIndex = getSituationIndex();

        situationIndex.put(dto.getId(), dto);

        saveToFile(situationIndex);

        return dto;

    }

    @Override
    public void delete(String dtoId) {

        Map<String, Situation> situationIndex = getSituationIndex();
        situationIndex.remove(dtoId);
        saveToFile(situationIndex);

    }

    private void saveToFile(Map<String, Situation> indexedSituation) {

        try {

            ObjectMapper mapper = new ObjectMapper();

            File file = FileService.createNewSituationsFile();

            mapper.writerWithDefaultPrettyPrinter().writeValue(file, indexedSituation.values());

        } catch (Exception e){

            e.printStackTrace();
        }
    }

    private Map<String, Situation> loadFromFile() {

        try {

            ObjectMapper mapper = new ObjectMapper();

            File file = FileService.getExistingSituationsFile();

            List<Situation> situations = mapper.readValue(file , mapper.getTypeFactory().constructCollectionType(List.class, Situation.class));

            FileUtils.printFileToConsole(file);

            return indexList(situations);

        }catch(Exception e ) {
            System.out.println("Exception: " + e.getMessage() );
            e.printStackTrace();

        }

        return null;
    }

    private  Map<String, Situation> indexList(List<Situation> situations){

        Map<String, Situation> result = new HashMap<>();

        for(Situation situation : situations){
            result.put(situation.getId(), situation);
        }

        return result;
    }

    private Map<String,Situation> loadDefault() {
        DTOFetchList<Situation> result = new DTOFetchList<>();
        result.add(createDefault1());
        result.add(createDefault2());
        result.add(createDefault3());
        return indexList(result);
    }

    private Situation createDefault1() {
        Situation situation = new Situation();
        situation.setId(situation.hashCode()+"");
        situation.setName("Driving car");
        situation.setActivity("Driving Car");
        situation.setAuxiliary("Speech two");
        situation.setEnvironment("Car");
        situation.setMobileStorage("On Surface");
        return situation;
    }

    private Situation createDefault2() {
        Situation situation = new Situation();
        situation.setId(situation.hashCode()+"");
        situation.setName("Lunch");
        situation.setActivity("Sitting");
        situation.setAuxiliary("Speech multiple");
        situation.setEnvironment("Restaurant");
        situation.setMobileStorage("On Surface");
        return situation;
    }

    private Map<String, Situation> getSituationIndex(){

        Map<String,Situation> result = loadFromFile();

        System.out.println("asdfff loading Situation index");
        if (result == null || result.size() == 0) {

            System.out.println("asdfff loading default");
           result = loadDefault();
           saveToFile(result);

        }else{
            System.out.println("asdfff loaded from file");

        }

        return result;

    }

    private Situation createDefault3() {
        Situation situation = new Situation();
        situation.setId(situation.hashCode()+"");
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
