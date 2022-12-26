package com.vss.social_webapp.repository;

import com.vss.social_webapp.model.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByCode(String roleCode);
//
//    @Query(value = "SELECT r.* FROM users_roles ur \" +\n" +
//            "            \"JOIN roles r ON ur.role_code = r.role_code \" +\n" +
//            "            \"WHERE ur.user_email = ?",nativeQuery = true)

//    List<Role> getAllByUserEmail(String userEmail);
}
