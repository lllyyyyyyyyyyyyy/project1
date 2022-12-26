package com.vss.lynt.service.impliment;

import com.vss.lynt.file.UserExcelImport;
import com.vss.lynt.model.User;
import com.vss.lynt.repository.UserRoleRepository;
import com.vss.lynt.repository.UserRepository;
import com.vss.lynt.service.ExcelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
@Service
public class ExcelServiceImpl implements ExcelService {
    @Autowired
    UserRoleRepository userRoleRepository;

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
