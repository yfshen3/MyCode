package com.mmall.common;

public class Const {

    public static final String CURRENT_USER = "currentUser";

    /**
     * 0-普通用户，1-管理员
     */
    public interface Role {
        int ROLE_CUSTOMER = 0;
        int ROLE_ADMIN = 1;
    }

    public static final String EMAIL = "email";
    public static final String USERNAME = "username";
}
