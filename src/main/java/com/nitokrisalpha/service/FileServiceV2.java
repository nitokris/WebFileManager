package com.nitokrisalpha.service;

import com.nitokrisalpha.controller.FileItemInfo;
import com.nitokrisalpha.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileServiceV2 extends AbstractFileService implements FileService {
    @Override
    public List<FileItemInfo> listDir(String dir) {
        checkDir(dir);
        File target = new File(dir);
        List<FileItemInfo> results = new ArrayList<>();
        for (File file : FileUtils.listFiles(target)) {
            FileItemInfo fileItemInfo = new FileItemInfo();
            fileItemInfo.setKey(file.getAbsolutePath());
            fileItemInfo.setLabel(file.getName());
            fileItemInfo.setDirectory(file.isDirectory());
            results.add(fileItemInfo);
        }
        return results;
    }

    @Override
    public void copyFile(String sourceDir, List<FileItemInfo> sourceFiles, String targetDir) throws IOException {

    }

    @Override
    public void deleteFile(String parent, List<FileItemInfo> targetFiles) throws IOException {

    }
}
