package com.vss.lynt.file;

import java.nio.file.Files;
import java.nio.file.Paths;

public class FileDownloadUtil {

    private static final String SAVED_FILE_FOLDER = "D:/lynt1/project/java/lynt/Files-Upload/";

    public static byte[] getFileAsResource(String fileName) {
        try {
            byte[] fileBytes = Files.readAllBytes(Paths.get(SAVED_FILE_FOLDER + fileName));
            return fileBytes;
        } catch (Exception e) {
            throw new RuntimeException("Fail to get file");
        }
    }
}