package com.syf.design.pattern.proxy;

public class TestMain {
    public static void main(String[] args) throws Exception {
        BaseService obj = ProxyFactory.builder(Person.class);
        obj.eat();
    }
}
