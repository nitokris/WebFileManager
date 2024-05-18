package com.nitokrisalpha.controller;

import lombok.Data;

@Data
public class FileItemInfo {

    private String key;
    private String label;

    private boolean disabled = false;

}
