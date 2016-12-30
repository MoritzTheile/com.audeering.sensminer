package com.audeering.sensminer.model.situation;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;
import com.audeering.sensminer.sensors.FileUtils;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class SituationCRUDService implements CRUDService<Situation, FetchQuery> {

    public void deleteAll() throws Exception {
        FileUtils.createNewSituationsFile();
    }

    @Override
    public DTOFetchList<Situation> fetchList(Page page, FetchQuery query) {

        DTOFetchList<Situation> fetchList = new DTOFetchList<>();

        Map<String, Situation> situationIndex = getSituationIndex();

        fetchList.setTotalSize(situationIndex.size());

        fetchList.addAll(situationIndex.values());

        return fetchList;

    }

    @Override
    public Situation get(String dtoId) {

        return getSituationIndex().get(dtoId);

    }

    @Override
    public Situation update(Situation dto) {

        System.out.println("asdfupdate updating dto " + dto.getId());

        Map<String, Situation> situationIndex = getSituationIndex();

        System.out.println("asdfupdate ids =  "+ situationIndex.keySet());

        if(situationIndex.containsKey(dto.getId())){

            System.out.println("asdfupdate replacing old dto with new dto");
            situationIndex.put(dto.getId(), dto);

        }else{

            System.out.println("asdfupdate nothung to update");

        }

        saveToFile(situationIndex);

        return dto;
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

            // String jsonInString = mapper.writerWithDefaultPrettyPrinter().writeValueAsString(indexedSituation.values());

            //  System.out.println(jsonInString);
            File file = FileUtils.createNewSituationsFile();

            mapper.writeValue(file, indexedSituation.values());
            System.out.println("asdf printing file: ");
            FileUtils.printFileToConsole(file);
        } catch (Exception e){

            e.printStackTrace();
        }
    }

    private Map<String, Situation> loadFromFile() {
        System.out.println("asdff trying to load from file");

        try {

            ObjectMapper mapper = new ObjectMapper();
            File file = FileUtils.getSituationsFile();
            System.out.println("asdf the file: " + file.getPath() + "/" + file.getName());
            List<Situation> situations = mapper.readValue(file , mapper.getTypeFactory().constructCollectionType(List.class, Situation.class));
            System.out.println("asdf situations.size() = " + situations.size());
            FileUtils.printFileToConsole(file);

            return indexList(situations);

        }catch(Exception e ) {
            System.out.println("asdf Exception: " + e.getMessage() );
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
        System.out.println("asdff ------getSituationIndex-----------------------");

        Map<String,Situation> result = loadFromFile();

        if (result == null || result.size() == 0) {

            System.out.println("asdff no data loaded create default situations" );
            result = loadDefault();
            saveToFile(result);

        }
        System.out.println("asdff result.size() = " + result.size());

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
