package com.syf.thread.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yfshen
 */
public class ThreadDataShareTest {

    private static Map<Thread, Integer> threadMap = new HashMap<>();

    public static void main(String[] args) throws InterruptedException {
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                int data = new Random().nextInt(100);
                threadMap.put(Thread.currentThread(), data);
                System.out.println(Thread.currentThread() + "  has put data:" + data);
            });
        }

        Thread.sleep(3000);

        for (int i = 0; i < 4; i++) {
            es.execute(() -> {
                new A().get();
                new B().get();
            });
        }

        es.shutdown();
    }

    static class A {
        public void get() {
            System.out.println("A from" + Thread.currentThread().getName()
                    + " get " + threadMap.get(Thread.currentThread()));
        }
    }

    static class B {
        public void get() {
            System.out.println("B from " + Thread.currentThread().getName()
                    + " get " + threadMap.get(Thread.currentThread()));
        }
    }
}
