package com.vss.lynt.service;

import com.vss.lynt.model.UserRole;

import java.util.List;

public interface RoleService {

    UserRole insertRole(UserRole userRoleDTO);

    List<UserRole> getAllUserRoleByUserIdAndRoleId();

    boolean checkInsert(Integer userId, Long roleId);

    List<UserRole> find(String keyword);

    UserRole updateUserRole(Integer id);

    UserRole findByUserId(Integer userId);
    List<UserRole> getAllRoleByUserId(Integer userId);
}
