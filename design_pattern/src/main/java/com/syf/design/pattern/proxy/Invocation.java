package com.syf.design.pattern.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

/**
 * 监控对象
 */
public class Invocation implements InvocationHandler {
    BaseService obj;

    public Invocation(BaseService obj) {
        this.obj = obj;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        Object value;

        String methodName = method.getName();
        if ("eat".equals(methodName)) {
            wash();
            value = method.invoke(this.obj, args);
        } else {
            value = method.invoke(this.obj, args);
            wash();
        }
        return value;
    }

    /**
     * 次要业务
     */
    void wash() {
        System.out.println("洗手");
    }
}
