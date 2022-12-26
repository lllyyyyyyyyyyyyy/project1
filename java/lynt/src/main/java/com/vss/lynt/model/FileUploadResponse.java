package com.vss.lynt.model;

import lombok.Data;

@Data
public class FileUploadResponse {
    private String fileName;
    private String downloadUri;
    private long size;

    // getters and setters are not shown for brevity

}