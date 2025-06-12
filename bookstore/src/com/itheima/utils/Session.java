package com.itheima.utils;

import com.itheima.domain.User;

public class Session {
    private static User user;
    public static void setUser(User u) {
         user = u;
    }
    public static User getUser() {
        return user;
    }
}