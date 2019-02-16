package org.lf.admin.api.baseapi.test;

import lombok.Data;

@Data
public class UserDemo {

    private int id;

    private String name;

    private String union;

    public UserDemo() {}

    public UserDemo(int id, String name, String union) {
        this.id = id;
        this.name = name;
        this.union = union;
    }
}
