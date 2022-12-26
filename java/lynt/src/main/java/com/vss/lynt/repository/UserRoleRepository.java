package com.vss.lynt.repository;


import com.vss.lynt.model.Role;
import com.vss.lynt.model.UserRole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRoleRepository extends JpaRepository<UserRole, Integer> {


    public Optional<UserRole> findByUserId(Integer id);

    public static final String FIND = "SELECT *\n" +
            "FROM users_roles ur, users u, roles r\n" +
            "WHERE ur.user_id = u.user_id And ur.role_id = r.id\n" +
            "  AND(ur.id like ?1 OR ur.user_id like ?1 OR ur.role_id like ?1 OR r.name like ?1  OR u.user_name like ?1 OR u.password like ?1 OR u.enabled like ?1)";

    @Query(value = FIND, nativeQuery = true)
    List<UserRole> find(String keyword);

    List<UserRole> findByRoleId(Integer roleId);

    @Query(value = "SELECT * from  users_roles ur WHERE ur.user_id = ?1", nativeQuery = true)
    List<UserRole> getAllByUserId(Integer userId);
}
