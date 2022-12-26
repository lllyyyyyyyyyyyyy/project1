package com.vss.projectstudent.Service.impl;


import com.vss.projectstudent.Model.Classroom;
import com.vss.projectstudent.Repository.ClassroomRepository;
import com.vss.projectstudent.Service.ClassroomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClassroomServiceImpl implements ClassroomService {

    @Autowired
    ClassroomRepository classroomRepository;

    @Override
    public List<Classroom> getListClass() {
        return classroomRepository.findAll();
    }
}
