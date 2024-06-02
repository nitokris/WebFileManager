package com.nitokrisalpha.utils;

import java.io.File;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileUtils {


    public static List<File> listFiles(File dir) {
        File[] files = dir.listFiles();
        if (files == null) {
            return Collections.emptyList();
        }
        return Arrays.asList(files);
    }



}
