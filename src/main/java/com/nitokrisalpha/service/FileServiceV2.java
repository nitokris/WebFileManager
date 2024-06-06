package com.nitokrisalpha.service;

import com.nitokrisalpha.controller.FileItemInfo;
import com.nitokrisalpha.entity.TaskInfo;
import com.nitokrisalpha.utils.FileUtils;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;

@Service("fileServiceV2")
@Slf4j
public class FileServiceV2 extends AbstractFileService implements FileService {

    @Resource
    @Qualifier("copyTaskExecutors")
    private ExecutorService executorService;


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
    public TaskInfo copyFile(final String sourceDir, final List<FileItemInfo> sourceFiles, final String targetDir) throws IOException {
        executorService.submit((Callable<Void>) () -> {
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
                        log.info("{} exists skip", file.getName());
                        continue;
                    }
                    org.apache.commons.io.FileUtils.copyFile(file, distFile);
                } else {
                    //目录下的文件，重建目录，再拷贝
                    String tmpTargetDir = fileParent.replaceAll(sourceDir, targetDir);
                    File tmpDir = new File(tmpTargetDir);
                    if (!tmpDir.exists()) {
                        tmpDir.mkdirs();
                    }
                    File distFile = new File(tmpTargetDir, srcFile.getLabel());
                    if (distFile.exists()) {
                        log.info("{} exists skip", file.getName());
                        continue;
                    }
                    org.apache.commons.io.FileUtils.copyFile(file, distFile);
                }
                log.info("{} ==> copy end", srcFile.getKey());
            }
            log.info("ALL FINISHED");
            return null;
        });
        return null;
    }

    @Override
    public void deleteFile(String parent, List<FileItemInfo> targetFiles) throws IOException {

    }
}
