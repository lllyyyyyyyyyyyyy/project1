package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.Role;
import com.vss.social_webapp.model.UserRole;
import com.vss.social_webapp.repository.RoleRepository;
import com.vss.social_webapp.repository.UserRoleRepository;
import com.vss.social_webapp.service.UserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserRoleServiceImpl implements UserRoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository roleRepository;

    @Override
    public List<UserRole> getRoleByRoleCode(String roleCode) {

        List<UserRole> userRoles = userRoleRepository.findAllByRoleCode(roleCode);
        if (!userRoles.isEmpty()) {
            for (UserRole userRole : userRoles) {
                Optional<Role> role = roleRepository.findByCode(userRole.getRoleCode());
                userRole.setRole(role.get());
            }
        }
        return userRoles;
    }

    @Override
    public List<UserRole> getRoleByUserEmail(String email) {


        return null;
    }

    @Override
    public UserRole addRoleUser(String userEmail) {
        UserRole userRole = new UserRole();
        userRole.setUserEmail(userEmail);
        userRole.setRoleCode("user");
        return userRoleRepository.save(userRole);
    }

    @Override
    public void saveUserRole(UserRole userRole) {
        userRoleRepository.save(userRole);
    }

}