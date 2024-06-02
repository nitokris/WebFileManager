package com.nitokrisalpha.controller;

import com.nitokrisalpha.service.FileService;
import jakarta.annotation.Resource;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("file")
@Slf4j
public class FileController {

    @Resource
    @Qualifier("fileService")
    private FileService fileService;

    @RequestMapping(value = "", method = RequestMethod.GET)
    public List<FileItemInfo> listDirFile(String dir) {
        return fileService.listDir(dir);
    }

    @RequestMapping(value = "", method = RequestMethod.PUT)
    public void moveFile(@RequestBody NReqBody body) throws IOException {
        String sourceDir = body.getParent();
        List<FileItemInfo> srcFiles = body.getSrcFiles();
        String targetDir = body.getTargetDir();
        fileService.copyFile(sourceDir, srcFiles, targetDir);
    }

    @RequestMapping(value = "", method = RequestMethod.DELETE)
    public void deleteFiles(@RequestBody NReqBody body) throws Exception {
        String parent = body.getParent();
        List<FileItemInfo> srcFiles = body.getSrcFiles();
        fileService.deleteFile(parent, srcFiles);
    }

}
