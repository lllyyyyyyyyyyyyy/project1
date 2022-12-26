package com.vss.social_webapp.service.impliment;

import com.vss.social_webapp.model.Role;
import com.vss.social_webapp.model.User;
import com.vss.social_webapp.model.UserRole;
import com.vss.social_webapp.repository.RoleRepository;
import com.vss.social_webapp.repository.UserRepository;
import com.vss.social_webapp.repository.UserRoleRepository;
import com.vss.social_webapp.service.UserRoleService;
import com.vss.social_webapp.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    UserService userService;

    @Autowired
    UserRoleService userRoleService;
    @Autowired
    UserRoleRepository userRoleRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByUsername(username);
        if (user == null) {
            throw new UsernameNotFoundException("USername not found");
        }

        List<UserRole> userRoles = userRoleRepository.findAllByUserEmail(user.getEmail());

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for(UserRole userRole:userRoles){
            Optional<Role> role = roleRepository.findByCode(userRole.getRoleCode());
            if(role.isPresent())    {
                authorities.add(new SimpleGrantedAuthority(role.get().getName()));
            }

        }
        return new MyUserDetails(user, authorities);
    }
}

