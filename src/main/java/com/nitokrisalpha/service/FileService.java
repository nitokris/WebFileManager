package com.nitokrisalpha.service;

import com.nitokrisalpha.controller.FileItemInfo;

import java.io.IOException;
import java.util.List;

public interface FileService {

    public List<FileItemInfo> listDir(String dir);

    /**
     * @param sourceDir   源文件夹
     * @param sourceFiles 源文件列表
     * @param targetDir   目标文件夹
     */
    public void copyFile(String sourceDir, List<FileItemInfo> sourceFiles, String targetDir) throws IOException;

    /**
     * 文件删除方法，目前存在小bug，无法删除嵌套过多的文件夹的情况，但是暂时不影响使用，就先这样
     *
     * @param parent
     * @param targetFiles
     * @throws IOException
     */
    void deleteFile(String parent, List<FileItemInfo> targetFiles) throws IOException;
}
