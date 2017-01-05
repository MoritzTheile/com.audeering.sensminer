package com.audeering.sensminer.model.configuration;

import com.audeering.sensminer.model.FileUtils;

import java.io.File;

/**
 * Created by MoritzTheile on 05.01.2017.
 */

public class FileService {

    public static File createNewConfigurationFile() throws Exception{

        File file = getExistingConfigurationFile();
        if(file.exists()){
            file.delete();
        }
        new File(file.getParent()).mkdirs();
        file.createNewFile();
        return file;
    }

    public static File getExistingConfigurationFile() throws Exception{

        String path =  FileUtils.getHomeDirPath()  + "/conf/conf.json";

        File file = new File(path);

        if(!file.exists()){

            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }

        return file;

    }

}
