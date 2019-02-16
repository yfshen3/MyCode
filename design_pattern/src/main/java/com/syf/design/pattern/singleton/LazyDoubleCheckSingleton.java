package com.syf.design.pattern.singleton;

/**
 * 懒汉优化双重加锁
 */
public class LazyDoubleCheckSingleton {
    //volatile:防止指令重排序
    private volatile static LazyDoubleCheckSingleton singleton = null;
    private LazyDoubleCheckSingleton() {}

    public static LazyDoubleCheckSingleton getInstance() {
        if (singleton == null) {
            synchronized (LazyDoubleCheckSingleton.class) {
                if (singleton == null) {
                    singleton = new LazyDoubleCheckSingleton();
                }
            }
        }
        return singleton;
    }
}
