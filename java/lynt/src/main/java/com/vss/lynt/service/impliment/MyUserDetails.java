package com.vss.lynt.service.impliment;

import com.vss.lynt.model.User;
import com.vss.lynt.repository.UserRoleRepository;
import com.vss.lynt.repository.UserRepository;
import com.vss.lynt.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

public class MyUserDetails implements UserDetails {

    private List<SimpleGrantedAuthority> authorities;

    @Autowired
    private UserRoleRepository userRoleRepository;


    @Autowired
    RoleService roleService;


    private User user;

    @Autowired
    UserRepository userRepository;

    public MyUserDetails(User user, List<SimpleGrantedAuthority> authorities ) {
        this.user = user;
        this.authorities = authorities;
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.authorities;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getUserName();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
