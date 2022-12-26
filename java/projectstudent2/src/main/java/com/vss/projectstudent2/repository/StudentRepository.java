package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {


//    public static final String getALL =;

    //    @Query(value = "SELECT * " +
//            "FROM khoahoc k, studenttkb.lophoc l, monhoc m, sinhvien s \n" +
//            "WHERE k.id_khoa_hoc = l.id_khoa_hoc AND l.id_lop_hoc = s.id_lop_hoc AND m.id_lop_hoc = l.id_lop_hoc", nativeQuery = true)
//    List<AllInforDTO> getAllInformation(AllInforDTO allInforDTO);
    List<Student> getAllByCode(String code);
}
