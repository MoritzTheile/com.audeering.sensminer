package com.audeering.sensminer.model.trackconf;

import com.audeering.sensminer.model.FileUtils;
import com.audeering.sensminer.model.configuration.Configuration;

import java.io.File;

/**
 * Created by MoritzTheile on 05.01.2017.
 */

public class FileService {

    public static File createNewTrackConfFile(Configuration.TRACKTYPE tracktype) throws Exception{

        File file = getExistingTrackConfFile(tracktype);

        if(file.exists()){
            file.delete();
        }

        new File(file.getParent()).mkdirs();

        file.createNewFile();

        return file;

    }

    public static File getExistingTrackConfFile(Configuration.TRACKTYPE tracktype) throws Exception{

        String path =  FileUtils.getHomeDirPath()  + "/conf/track_"+tracktype.name()+".conf.json";

        File file = new File(path);

        if(!file.exists()){
            new File(file.getParent()).mkdirs();
            file.createNewFile();
        }

        return file;

    }

}
