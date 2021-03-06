package com.audeering.sensminer.model;

import android.support.test.runner.AndroidJUnit4;

import com.audeering.sensminer.model.situation.FileService;

import junit.framework.Assert;

import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;
import java.io.FileInputStream;
import java.io.PrintWriter;

/**
 * Created by MoritzTheile on 05.12.2016.
 */
@RunWith(AndroidJUnit4.class)
public class FileUtilsTest {

    private static String TESTSTRING = "asdfasdfHelloWorld asdfasdf";

    @Test
    public void testGetSituationsFile() throws Exception {

        // getting the file
        File file = FileService.getExistingSituationsFile();
        Assert.assertNotNull(file);

        Assert.assertEquals("", fileToString(file));

        //writing a String to the file
        PrintWriter out = new PrintWriter(file);
        out.print(TESTSTRING);
        out.close();

        // reloading the file
        file = FileService.getExistingSituationsFile();

        Assert.assertEquals(TESTSTRING, fileToString(file));



    }

    @Test
    public void testCreateNewSituationsFile() throws Exception {

        // getting the file
        File file = FileService.createNewSituationsFile();
        Assert.assertNotNull(file);

        Assert.assertEquals("", fileToString(file));

        //writing a String to the file
        PrintWriter out = new PrintWriter(file);
        out.print(TESTSTRING);
        out.close();

        // creating a new file
        file = FileService.createNewSituationsFile();

        // the file should have been replaced with a new file:
        Assert.assertEquals("", fileToString(file));



    }


    private static String fileToString(File file){

        byte[] encoded = fileToByteArray(file);
        return new String(encoded);

    }

    private static byte[] fileToByteArray(File file){

        FileInputStream fileInputStream=null;
        byte[] bFile = new byte[(int) file.length()];

        try {

            //convert file into array of bytes
            fileInputStream = new FileInputStream(file);
            fileInputStream.read(bFile);

        }catch(Exception e){

            e.printStackTrace();

        }finally{
            try{
                fileInputStream.close();
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return bFile;
    }
}