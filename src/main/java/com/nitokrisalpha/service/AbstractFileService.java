package com.nitokrisalpha.service;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import java.io.File;

@Slf4j
public abstract class AbstractFileService {

    protected void checkDir(String dir) {
        if (StringUtils.isEmpty(dir)) {
            log.error("empty target");
            throw new RuntimeException("待枚举路径不能为空");
        }
        File target = new File(dir);

        if (!target.exists()) {
            log.error("{} not exists", dir);
            throw new RuntimeException("目标不存在");
        }

        if (!target.isDirectory()) {
            log.error("{} not a directory", dir);
            throw new RuntimeException("无法枚举文件");
        }
    }
}
