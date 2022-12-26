package com.vss.lynt.service.impliment;

import com.vss.lynt.model.Role;
import com.vss.lynt.model.User;
import com.vss.lynt.model.UserRole;
import com.vss.lynt.repository.RoleRepository;
import com.vss.lynt.repository.UserRepository;
import com.vss.lynt.repository.UserRoleRepository;
import com.vss.lynt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.collections4.CollectionUtils.isEmpty;

@Service
public class UserDetailsServiceImpl implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Autowired
    private RoleService roleService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUserName(username);
        if (user == null)
            throw new UsernameNotFoundException("Username not found");

        List<Role> roles = roleRepository.findAllByUserId(user.getUserId());
        if (isEmpty(roles))
            throw new RuntimeException("User has no role");

        List<SimpleGrantedAuthority> authorities = new ArrayList<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRoleName()));
        }
        return new MyUserDetails(user, authorities);
    }
}
