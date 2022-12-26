package com.vss.lynt.service.impliment;

import com.vss.lynt.model.Role;
import com.vss.lynt.model.User;
import com.vss.lynt.model.UserRole;
import com.vss.lynt.repository.UserRoleRepository;
import com.vss.lynt.repository.RoleRepository;
import com.vss.lynt.repository.UserRepository;
import com.vss.lynt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    UserRoleRepository userRoleRepository;

    @Autowired
    RoleRepository role_repository;
    @Autowired
    UserRepository userRepository;

    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    @Override
    public UserRole insertRole(UserRole userRoleDTO) {

        return userRoleRepository.save(userRoleDTO);
    }

    @Override
    public List<UserRole> getAllUserRoleByUserIdAndRoleId() {

        List<UserRole> userRoles = userRoleRepository.findAll();

        for(UserRole i : userRoles){
            Optional<User> user = userRepository.findByUserId(i.getUserId());
            if(user.isPresent()){
                i.setUser(user.get());
            }
            else    i.setUser(null);
        }

        for(UserRole i: userRoles){
            Optional<Role> role = role_repository.findById(i.getRoleId());
            if (role.isPresent())   i.setRole(role.get());
            else i.setRole(null);
        }

        return userRoles;
    }

    @Override
    public boolean checkInsert(Integer userId, Long roleId) {
        Optional<UserRole> userRole = userRoleRepository.findByUserId(userId);
        if(userRole.isPresent() && userRole.get().getRoleId().equals(roleId))
            return true;
        else return false;
    }

    @Override
    public List<UserRole> find(String keyword) {
        keyword = '%'+ keyword + '%';
        return userRoleRepository.find(keyword);
    }

    @Override
    public UserRole updateUserRole(Integer id) {
        Optional<UserRole> userRole = userRoleRepository.findById(Integer.valueOf(id));
        return userRole.get();
    }

    @Override
    public UserRole findByUserId(Integer userId) {
        return userRoleRepository.findByUserId(userId).get();
    }

    @Override
    public List<UserRole> getAllRoleByUserId(Integer userId) {

        List<UserRole> userRoles = userRoleRepository.getAllByUserId(userId);
        for(UserRole i:userRoles){
            Optional<Role> role = role_repository.findById(i.getRoleId());
            if(role.isPresent()) i.setRole(role.get());
            else i.setRole(null);
        }

        return userRoles;
    }
}
