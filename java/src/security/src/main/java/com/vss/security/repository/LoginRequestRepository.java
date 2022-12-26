package com.vss.security.repository;

import com.vss.security.model.LoginRequest;
import com.vss.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LoginRequestRepository extends JpaRepository<LoginRequest, Long> {


    Optional<LoginRequest> findByUserName(String userName);

}
