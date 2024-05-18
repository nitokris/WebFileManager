package com.nitokrisalpha.runner;

import com.nitokrisalpha.controller.FileItemInfo;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.springframework.boot.CommandLineRunner;

import java.io.File;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

//@Component
@Slf4j
public class TestRunner implements CommandLineRunner {
    @Override
    public void run(String... args) throws Exception {
        String dir = "/Users/nitokris/Desktop/comic";
        String targetDir = "/Users/nitokris/Desktop/comicTarget";
        Collection collection = FileUtils.listFiles(new File(dir), null, true);
        List<FileItemInfo> fileItemInfos = new ArrayList<>();
        for (Object object : collection) {
            File srcFile = new File(String.valueOf(object));
            String parent = srcFile.getParent();
            String fileName = srcFile.getName();
            if (parent.equals(dir)) {
                File targetFile = new File(targetDir, fileName);
                FileUtils.copyFile(srcFile, targetFile);
            } else {
                String target = parent.replaceAll(dir, targetDir);
                File targetParent = new File(target);
                if (!targetParent.exists()) {
                    targetParent.mkdirs();
                }
                File targetFile = new File(target, fileName);
                FileUtils.copyFile(srcFile, targetFile);
            }
        }
        log.error("file copy end");
    }
}
