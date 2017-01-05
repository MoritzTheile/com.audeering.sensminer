package com.audeering.sensminer.model.situation;

import com.audeering.sensminer.model.FileUtils;

import java.io.File;

/**
 * Created by MoritzTheile on 05.01.2017.
 */

public class FileService {
    public static File createNewSituationsFile() throws Exception{

        File file = getExistingSituationsFile();
        if(file.exists()){
            file.delete();
        }
        new File(file.getParent()).mkdirs();
        file.createNewFile();
        return file;
    }

    public static File getExistingSituationsFile() throws Exception{

        String path =  FileUtils.getHomeDirPath()  + "/conf/situations.json";


        File file = new File(path);
        System.out.println("AbsolutePath to conf file: " +  file.getAbsolutePath() );


        if(!file.exists()){
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }

        return file;

    }
}
