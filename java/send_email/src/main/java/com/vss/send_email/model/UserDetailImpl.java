package com.vss.send_email.model;

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
