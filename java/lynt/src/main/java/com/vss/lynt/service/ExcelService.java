package com.vss.lynt.service;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void saveExcelToUser(MultipartFile file);
}
