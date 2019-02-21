package com.syf.thread.thread.locks;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * 多路Condition
 * @author yfshen
 */
public class ConditionDemo {
    final Lock lock = new ReentrantLock();
    final Condition notEmpty = lock.newCondition();
    final Condition notFull = lock.newCondition();

    final Object[] items = new Object[100];
    int putptr, takeptr, count;

    public void put(Object value) throws InterruptedException {
        lock.lock();
        try {
            // 如果队列已经满了，就不能放
            while (count == items.length) {
                notFull.await();
            }
            // 将值放到第putptr位置
            items[putptr] = value;
            // 如果放到队列最后一个位置，将偏移量置为0
            if (++putptr == items.length) putptr = 0;
            ++count;
            notEmpty.signal();
        } finally {
            lock.unlock();
        }
    }

    public Object take() throws InterruptedException {
        lock.lock();
        Object value = null;
        try {
            while (count == 0) {
                notEmpty.await();
            }
            value = items[takeptr];
            if (++takeptr == items.length) takeptr = 0;
            --count;
            notFull.signal();
            return value;
        } finally {
            lock.unlock();
        }
    }

}
