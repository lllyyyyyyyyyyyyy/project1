package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole,Long> {
    List<UserRole> findAllByUserEmail(String userEmail);

    List<UserRole> findAllByRoleCode(String roleCode);
}
