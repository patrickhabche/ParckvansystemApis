package com.example.ParckvanSystemBackend.DTOs;

import com.example.ParckvanSystemBackend.Entities.User;

public class UpdateUserProfileResponse {

    public User user;
    public int code;

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }
}
