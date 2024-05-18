package com.nitokrisalpha.service;

import com.nitokrisalpha.controller.FileItemInfo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class FileServiceTest {

    private static final Logger log = LoggerFactory.getLogger(FileServiceTest.class);
    @Autowired
    private FileService fileService;

    @Test
    void listDir() {
        // empty test
        try {
            fileService.listDir(null);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        try {
            fileService.listDir("");
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        // 文件枚举测试
        try {
            String file = "/tmp/233";
            fileService.listDir(file);
        } catch (Exception e) {
            log.error(e.getMessage());
        }

        // 不存在的路径测试
        try {
            String notExists = "2332sfsdfssf";
            fileService.listDir(notExists);
        } catch (Exception e) {
            log.error(e.getMessage());
        }


        String target = "/tmp";
        List<FileItemInfo> fileItemInfos = fileService.listDir(target);
        Assertions.assertNotEquals(0, fileItemInfos.size());
    }

    @Test
    public void testCopyFile() throws IOException {
        ArrayList<FileItemInfo> items = new ArrayList<>();
        FileItemInfo info = new FileItemInfo();
        info.setKey("/Users/nitokris/Desktop/test/233.txt");
        info.setLabel("233.txt");
        items.add(info);
        fileService.copyFile("/Users/nitokris/Desktop/test", items, "/Users/nitokris/Desktop/ggg");
    }

    @Test
    public void deleteFile() throws IOException {
        ArrayList<FileItemInfo> items = new ArrayList<>();
        FileItemInfo info = new FileItemInfo();
        info.setKey("/Users/nitokris/Desktop/test/233.txt");
        info.setLabel("233.txt");
        items.add(info);
        FileItemInfo info1 = new FileItemInfo();
        info1.setKey("/Users/nitokris/Desktop/test/gg/k23");
        info1.setLabel("k23");
        items.add(info1);
        fileService.deleteFile("/Users/nitokris/Desktop/test", items);
    }

}