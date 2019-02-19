package com.syf.thread.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                User user = User.getThreadInstance();
                System.out.println(Thread.currentThread() + "  has put data:" + user.toString());
            });
        }
        Thread.sleep(3000);
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                new A().get();
                new B().get();
            });
        }
        es.shutdown();
    }

    static class A {
        public void get() {
            User user = User.getThreadInstance();
            System.out.println("A from" + Thread.currentThread().getName()
                    + " get " + user);
        }
    }

    static class B {
        public void get() {
            User user = User.getThreadInstance();
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get " + user);
        }
    }

}

class User {
    private static ThreadLocal<User> threadLocal = new ThreadLocal<>();

    private User() {}

    public static synchronized User getThreadInstance() {
        User user = threadLocal.get();
        if (user == null) {
            user = new User();
            threadLocal.set(user);
        }
        return user;
    }

    private String userName;
    private Integer age;

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

}
