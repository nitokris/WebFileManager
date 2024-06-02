package com.nitokrisalpha.service;

import com.nitokrisalpha.controller.FileItemInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Service("fileService")
@Slf4j
public class DefaultFileService extends AbstractFileService implements FileService {

    public List<FileItemInfo> listDir(String dir) {
        checkDir(dir);
        File target = new File(dir);
        Collection<File> collection = FileUtils.listFiles(target, null, true);
        List<FileItemInfo> fileItemInfos = new ArrayList<>();
        for (File object : collection) {
            FileItemInfo fileItemInfo = new FileItemInfo();
            fileItemInfo.setKey(object.getAbsolutePath());
            fileItemInfo.setLabel(object.getName());
            fileItemInfos.add(fileItemInfo);
        }
        return fileItemInfos;
    }


    /**
     * @param sourceDir   源文件夹
     * @param sourceFiles 源文件列表
     * @param targetDir   目标文件夹
     */
    public void copyFile(String sourceDir, List<FileItemInfo> sourceFiles, String targetDir) throws IOException {
        if (StringUtils.isEmpty(sourceDir) || StringUtils.isEmpty(targetDir) || sourceFiles.isEmpty()) {
            throw new RuntimeException("部分信息为空");
        }
        if (sourceDir.endsWith("/") || targetDir.endsWith("/")) {
            throw new RuntimeException("路径不能以</>结束");
        }

        for (FileItemInfo srcFile : sourceFiles) {
            log.info("{} ==> copy start", srcFile.getKey());
            File file = new File(srcFile.getKey());
            String fileParent = file.getParent();
            // 文件本体，直接拷贝
            if (fileParent.equals(sourceDir)) {
                File distFile = new File(targetDir, file.getName());
                if (distFile.exists()) {
                    log.info("{} exists delete", file.getName());
                    distFile.delete();
                }

                FileUtils.copyFile(file, distFile);
            } else {
                //目录下的文件，重建目录，再拷贝
                String tmpTargetDir = fileParent.replaceAll(sourceDir, targetDir);
                File tmpDir = new File(tmpTargetDir);
                if (!tmpDir.exists()) {
                    tmpDir.mkdirs();
                }
                File distFile = new File(tmpTargetDir, srcFile.getLabel());
                if (distFile.exists()) {
                    log.info("{} exists delete", file.getName());
                    distFile.delete();
                }
                FileUtils.copyFile(file, distFile);
            }
            log.info("{} ==> copy end", srcFile.getKey());
        }
        log.info("ALL FINISHED");
    }

    /**
     * 文件删除方法，目前存在小bug，无法删除嵌套过多的文件夹的情况，但是暂时不影响使用，就先这样
     *
     * @param parent
     * @param targetFiles
     * @throws IOException
     */
    public void deleteFile(String parent, List<FileItemInfo> targetFiles) throws IOException {

        for (FileItemInfo srcFile : targetFiles) {
            if (!srcFile.getKey().startsWith(parent)) {
                log.error(" error path info ");
                continue;
            }
            log.info("{} ==> delete start", srcFile.getKey());
            File file = new File(srcFile.getKey());
            if (!file.exists()) {
                log.info("{} ==> not exists", srcFile.getKey());
                continue;
            }
            // 父路径是parent
            if (file.getParent().equals(parent)) {
                FileUtils.forceDelete(file);
            } else {
                File parentFile = file.getParentFile();
                FileUtils.forceDelete(parentFile);
            }
            log.info("{} ==> delete down", srcFile.getKey());
        }

    }

}
