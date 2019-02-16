package com.syf.design.pattern.singleton;

import java.io.Serializable;

/**
 * 饿汉式
 */
public class HungrySingleton implements Serializable {
    private final static HungrySingleton singleton;
    static {
        singleton = new HungrySingleton();
    }
    private HungrySingleton () {
        if (singleton != null) {
            throw new RuntimeException("单例构造器禁止反射调用");
        }
    }

    public static HungrySingleton getInstance() {
        return singleton;
    }

    private Object readResolve() {
        return singleton;
    }
}
