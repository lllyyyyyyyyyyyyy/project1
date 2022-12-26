package com.vss.social_webapp.service;

import com.vss.social_webapp.model.UserRole;

import java.util.List;

public interface UserRoleService {

    List<UserRole> getRoleByRoleCode(String roleCode);

    List<UserRole> getRoleByUserEmail(String email);

    UserRole addRoleUser(String userEmail);

    void saveUserRole(UserRole userRole);
}
