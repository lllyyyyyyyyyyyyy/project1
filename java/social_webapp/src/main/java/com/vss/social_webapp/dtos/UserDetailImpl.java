package com.vss.social_webapp.dtos;

import com.vss.social_webapp.model.User;

public class UserDetailImpl implements UserDetail{
    private User user;

    public UserDetailImpl(User user){
        this.user = user;
    }

    @Override
    public boolean isEnabled() {
        return user.isEnabled();
    }
}
