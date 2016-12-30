package com.audeering.sensminer.sensors;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class FileUtils {

    public static String getFileForService(String service) {
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/audiorecordtest.wav";
    }

    public static File createNewSituationsFile() throws Exception{

        File file = getSituationsFile();
        if(file.exists()){
            file.delete();
        }
        new File(file.getParent()).mkdirs();
        file.createNewFile();
        return file;
    }

    public static File getSituationsFile() throws Exception{

        String path =  Environment.getExternalStorageDirectory().getAbsolutePath() + "/conf/situations.json";

        File file = new File(path);

        if(!file.exists()){
            file.createNewFile();
        }

        return file;

    }

    public static void printFileToConsole(File file){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while(line != null)
            {
                System.out.println("asdf    " + line);
                line = in.readLine();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally{
            try{
                in.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }



    }

}
