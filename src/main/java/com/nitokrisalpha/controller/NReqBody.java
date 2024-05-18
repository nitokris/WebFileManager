package com.nitokrisalpha.controller;

import lombok.Data;

import java.util.List;

@Data
public class NReqBody {

    private String parent;
    List<FileItemInfo> srcFiles;
    private String targetDir;

}
