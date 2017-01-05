package com.audeering.sensminer.model;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

/**
 * Created by Matthias Laabs on 06.12.2016.
 */

public class FileUtils {

    public static String getHomeDirPath(){
        return Environment.getExternalStorageDirectory().getAbsolutePath() + "/SensMiner";
    }

    public static File getDataDir(){
        File dataDir = new File(FileUtils.getHomeDirPath() +"/data");
        dataDir.mkdirs();
        return dataDir;
    }


    public static void printFileToConsole(File file){
        BufferedReader in = null;
        try {
            in = new BufferedReader(new FileReader(file));
            String line = in.readLine();
            while(line != null)
            {
                System.out.println(line);
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
