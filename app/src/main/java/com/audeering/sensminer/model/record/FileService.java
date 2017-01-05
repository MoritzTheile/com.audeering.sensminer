package com.audeering.sensminer.model.record;

import com.audeering.sensminer.model.FileUtils;

/**
 * Created by MoritzTheile on 05.01.2017.
 */

public class FileService {

    public static String getPathToFile(String service) {
        return FileUtils.getHomeDirPath() + "/audiorecordtest.wav";
    }
}
