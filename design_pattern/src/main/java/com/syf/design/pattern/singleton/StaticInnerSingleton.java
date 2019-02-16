package com.syf.design.pattern.singleton;

/**
 * 单例-静态内部类
 * 基于类初始化的延迟加载解决方案
 */
public class StaticInnerSingleton {
    private static class InnerClass {
        private static StaticInnerSingleton staticInnerSingleton = new StaticInnerSingleton();
    }

    public static StaticInnerSingleton getInstance() {
        return InnerClass.staticInnerSingleton;
    }
}
