package com.syf.thread.thread;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * Semaphore可以维护当前访问自身的线程个数，并提供了同步机制。
 * 使用Semaphore可以控制同事访问资源的线程个数。
 * 例如：实现一个文件允许的并发访问数。
 * @author yfshen
 * 单个信号量的Semaphore对象可以实现互斥锁的功能，并且可以是
 * 由一个线程获得了锁，再由另一个线程释放锁，这可应用于死锁恢
 * 复的一些场合。
 */
public class SemaphoreTest {
    private static final Semaphore semaphore = new Semaphore(3);
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();
        for (int i = 0; i < 10; i++) {
            es.submit(() -> {
                try {
                    semaphore.acquire();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() +
                        "进入，当前已有" + (3 - semaphore.availablePermits()) + "个并发");
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("线程" + Thread.currentThread().getName() + "即将离开");
                semaphore.release();
                System.out.println("线程" + Thread.currentThread().getName() + "已经离开;当前有"
                                + (3 - semaphore.availablePermits()) + "个并发");
            });
        }
        es.shutdown();
    }
}
