package com.audeering.sensminer.model.record;

import com.audeering.sensminer.model.FileUtils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by MoritzTheile on 05.01.2017.
 */

public class FileService {

    private static final String DIR_SUFFIX = "_Record";

    public static List<String> getRecordIds(){

        List<String> names = new ArrayList<>();

        for(File file : FileUtils.getDataDir().listFiles()){
            if(file.isDirectory()){
                String name = file.getName();
                if(name.endsWith(DIR_SUFFIX)){
                    names.add(name.replace(DIR_SUFFIX,""));
                }
            }
        }

        return names;

    }

    public static File createNewRecordFile(String recordId) throws Exception{

        File file = getExistingRecordFile(recordId);
        if(file.exists()){
            file.delete();
        }
        new File(file.getParent()).mkdirs();
        file.createNewFile();
        return file;
    }

    public static File getExistingRecordFile(String recordId) throws Exception{

        String path =  getRecordDir(recordId)+"/record.json";

        File file = new File(path);

        if(!file.exists()){

            new File(file.getParent()).mkdirs();
            file.createNewFile();

        }

        return file;

    }

    protected static String getRecordDir(String recordId) {
        return FileUtils.getDataDir().getPath() + "/"+recordId+DIR_SUFFIX;
    }

    public static void deleteRecordDir(String recordId){
        File recordDir = new File(getRecordDir(recordId));
        if(recordDir.exists()){
            deleteDirectory(recordDir);
        }
    }

    private static void deleteDirectory(File directory) {

        // being careful not to delete outside the home directory:
        if(!directory.getPath().startsWith(FileUtils.getHomeDirPath())){
            throw new RuntimeException("Stopped attempt to delete outside home dir: "+directory.getPath() + " (homeDir=" + FileUtils.getHomeDirPath() + ")");
        }

        File[] files = directory.listFiles();

        for(int i=0; i<files.length; i++) {

            if(files[i].isDirectory()) {
                deleteDirectory(files[i]);
            } else {
                files[i].delete();
            }

        }

        directory.delete();

    }

}
