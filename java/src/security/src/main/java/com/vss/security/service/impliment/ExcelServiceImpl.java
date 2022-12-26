package com.vss.security.service.impliment;

import com.vss.security.excel.UserExcelImport;
import com.vss.security.model.User;
import com.vss.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ExcelServiceImpl implements ExcelService {

    @Autowired
    UserRepository userRepository;

    @Override
    public void saveExcelToUser(MultipartFile file) {
        try {
            List<User> users = UserExcelImport.excelToUsers(file.getInputStream());
            userRepository.saveAll(users);
        } catch (IOException e) {
            throw new RuntimeException("fail to store excel data: " + e.getMessage());
        }
    }
}
