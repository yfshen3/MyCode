package com.syf.design.pattern.proxy;

/**
 * 具体实现业务
 * 自己拓展类
 */
public class Person implements BaseService{
    @Override
    public void eat() {
        System.out.println("吃饭");
    }

    @Override
    public void bash() {
        System.out.println("上厕所");
    }
}
