package com.syf.thread.thread.locks;

import java.util.Random;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁：氛围读锁和写锁，多个读锁不互斥，读锁与写锁互斥，写锁与写锁互斥，
 * 这是有jvm自己控制的，只要上好对应的锁就行了。
 * @author yfshen
 */
public class WriteReadLockTest {
    public static void main(String[] args) {
        final ShareData data = new ShareData();
        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    data.put(new Random().nextInt(100));
                }
            }).start();
        }

        for (int i = 0; i < 3; i++) {
            new Thread(() -> {
                while (true) {
                    data.get();
                }
            }).start();
        }
    }
}

class ShareData {
    private Object data = null;
    private ReadWriteLock readWriteLock = new ReentrantReadWriteLock();

    public void get() {
        readWriteLock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备读数据");
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "已经读完:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.readLock().unlock();
        }
    }

    public void put(Object data) {
        readWriteLock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + "准备写数据");
            this.data = data;
            Thread.sleep(500);
            System.out.println(Thread.currentThread().getName() + "已经写完:" + data);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            readWriteLock.writeLock().unlock();
        }
    }

}
