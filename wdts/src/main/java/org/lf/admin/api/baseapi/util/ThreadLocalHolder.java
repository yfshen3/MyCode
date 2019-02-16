package org.lf.admin.api.baseapi.util;

import org.lf.admin.api.baseapi.model.User;

public class ThreadLocalHolder {

    private static final ThreadLocal<User> threadLocal = new ThreadLocal<User>();

    public static User get() {
        return threadLocal.get();
    }

    public static void set(User user) {
        threadLocal.set(user);
    }
}
