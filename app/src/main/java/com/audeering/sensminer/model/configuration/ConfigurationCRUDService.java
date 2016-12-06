package com.audeering.sensminer.model.configuration;

import com.audeering.sensminer.model.abstr.CRUDService;
import com.audeering.sensminer.model.abstr.DTOFetchList;
import com.audeering.sensminer.model.abstr.FetchQuery;
import com.audeering.sensminer.model.abstr.Page;

/**
 * Created by MoritzTheile on 05.12.2016.
 */

public class ConfigurationCRUDService implements CRUDService<Configuration, FetchQuery> {

    /**
     * there will always be only one Configuration
     */
    @Override
    public DTOFetchList<Configuration> fetchList(Page page, FetchQuery query) {

        DTOFetchList<Configuration> result = loadFromFile();
        if (result.size() == 0) {
            Configuration configuration = getDefault();
            saveToFile(configuration);
            result.add(configuration);
        }

        return result;
    }


    @Override
    public Configuration get(String dtoId) {
        // not needed because there is only one Configuration
        throw new RuntimeException("not implemented");

    }

    @Override
    public Configuration update(Configuration configuration) {
        saveToFile(configuration);
        return configuration;
    }

    @Override
    public Configuration create(Configuration dto) {

        // not needed because there is at least the default Configuration
        throw new RuntimeException("not implemented");

    }

    @Override
    public void delete(String dtoId) {
        throw new RuntimeException("not implemented");
    }

    private Configuration getDefault() {

        Configuration configuration = new Configuration();
        // initialize with default values

        configuration.getSituationAuxiliaryValues().add("Music");
        configuration.getSituationAuxiliaryValues().add("Speech");
        configuration.getSituationAuxiliaryValues().add("Speech two");
        configuration.getSituationAuxiliaryValues().add("Speech multiple");
        configuration.getSituationAuxiliaryValues().add("Radio");

        configuration.getSituationActivityValues().add("Sitting");
        configuration.getSituationActivityValues().add("Standing");
        configuration.getSituationActivityValues().add("Walking");
        configuration.getSituationActivityValues().add("Running");
        configuration.getSituationActivityValues().add("Driving Car");
        configuration.getSituationActivityValues().add("Co-Driving Car");

        configuration.getSituationEnvironmentValues().add("Car");
        configuration.getSituationEnvironmentValues().add("At Home");
        configuration.getSituationEnvironmentValues().add("City");
        configuration.getSituationEnvironmentValues().add("Restaurant");
        configuration.getSituationEnvironmentValues().add("Train");
        configuration.getSituationEnvironmentValues().add("Outside");

        configuration.getSituationMobileStorageValues().add("On Surface");
        configuration.getSituationMobileStorageValues().add("Holding in Hand");
        configuration.getSituationMobileStorageValues().add("In Pocket");
        configuration.getSituationMobileStorageValues().add("In Purse");
        configuration.getSituationMobileStorageValues().add("In Backpack");

        configuration.getRecordDurations().put("1 Minute", 60);
        configuration.getRecordDurations().put("5 Minutes", 60 * 5);
        configuration.getRecordDurations().put("10 Minutes", 60 * 10);
        configuration.getRecordDurations().put("30 Minutes", 60 * 30);
        configuration.getRecordDurations().put("1 Hour", 60 * 60);
        configuration.getRecordDurations().put("1,5 Hour", 60 * 90);
        configuration.getRecordDurations().put("2 Hours", 60 * 60 * 2);
        configuration.getRecordDurations().put("3 Hours", 60 * 60 * 3);
        configuration.getRecordDurations().put("4 Hours", 60 * 60 * 4);
        configuration.getRecordDurations().put("8 Hours", 60 * 60 * 8);
        configuration.getRecordDurations().put("Indefinitely", Integer.MAX_VALUE);

        configuration.setRecordDuration(configuration.getRecordDurations().keySet().iterator().next());

        return configuration;
    }



    private DTOFetchList<Configuration> loadFromFile() {
        DTOFetchList<Configuration> result = new DTOFetchList<>();
        //TODO
        return result;
    }

    private void saveToFile(Configuration configuration) {
        //TODO
    }


    //SINGLETON

    private static ConfigurationCRUDService instance;

    public static ConfigurationCRUDService instance() {
        if (instance == null) {
            instance = new ConfigurationCRUDService();
        }
        return instance;
    }

}
