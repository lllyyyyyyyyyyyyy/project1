package com.vss.send_email.repository;

import com.vss.send_email.model.LoginRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LoginRequestRepository extends JpaRepository<LoginRequest, Long> {

    Optional<LoginRequest> findByEmail(String email);

}
