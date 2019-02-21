package com.syf.thread.thread.locks;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存demo
 * @author yfshen
 */
public class CacheDemo {

    private final static ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    private static Map<String, Object> map = new HashMap<>();

    public static Object get(String key) {
        readWriteLock.readLock().lock();
        Object value = null;
        try {
            value = map.get(key);
            if (value == null) {
                readWriteLock.readLock().unlock();
                readWriteLock.writeLock().lock();
                try {
                    if (value == null) {
                        value = "查数据库获取value";
                        map.put(key, value);
                    }
                } finally {
                    readWriteLock.writeLock().unlock();
                }
                readWriteLock.readLock().lock();
            }
        } finally {
            readWriteLock.readLock().unlock();
        }
        return value;
    }
}
