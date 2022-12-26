package com.vss.security.service.impliment;

import org.springframework.web.multipart.MultipartFile;

public interface ExcelService {
    void saveExcelToUser(MultipartFile file);
}
