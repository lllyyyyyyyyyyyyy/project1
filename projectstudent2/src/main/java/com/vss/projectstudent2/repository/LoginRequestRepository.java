package com.vss.projectstudent2.repository;

import com.vss.projectstudent2.model.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LoginRequestRepository extends JpaRepository<LoginRequest, Long>{

    Optional<LoginRequest> findByUserName(String userName);


}
