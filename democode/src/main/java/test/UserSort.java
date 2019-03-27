package test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class UserSort {
    public static void main(String[] args) {
        User u1 = new User("s1", 5);
        User u2 = new User("s2", 4);
        User u3 = new User("s3", 20);
        User u4 = new User("s4", 10);
        User u5 = new User("s5", 1);
        List<User> userList = new ArrayList<>();
        userList.add(u1);
        userList.add(u2);
        userList.add(u3);
        userList.add(u4);
        userList.add(u5);
        Collections.sort(userList, new UserComparator());
        userList.forEach(user -> System.out.println(user.toString()));
        System.out.println("------------------------------------");

        User u6 = new User("s6", 40);
        User u7 = new User("s7", 13);
        User u8 = new User("s8", 2);
        User u9 = new User("s9", 45);
        List<User> users = new ArrayList<>();
        users.add(u6);
        users.add(u7);
        users.add(u8);
        users.add(u9);
        Collections.sort(users, new UserComparator());
        users.forEach(user -> System.out.println(user.toString()));
        System.out.println("------------------------------------");

        userList.addAll(users);
        userList.forEach(user -> System.out.println(user.toString()));
    }
}

class UserComparator implements Comparator<User> {

    @Override
    public int compare(User o1, User o2) {
        return o1.getSortId() - o2.getSortId();
    }

    @Override
    public boolean equals(Object obj) {
        return false;
    }
}

class User{
    private String username;
    private Integer sortId;

    public User(String username, Integer sortId) {
        this.username = username;
        this.sortId = sortId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public Integer getSortId() {
        return sortId;
    }

    public void setSortId(Integer sortId) {
        this.sortId = sortId;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", sortId=" + sortId +
                '}';
    }
}


