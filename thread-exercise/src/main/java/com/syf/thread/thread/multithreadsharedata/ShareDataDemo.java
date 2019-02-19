package com.syf.thread.thread.multithreadsharedata;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ShareDataDemo {
    public static void main(String[] args) {
        Data data = new Data();
        ExecutorService es = Executors.newFixedThreadPool(4);
        for (int i = 0; i < 4; i++) {
            if (i%2 == 0) {
                es.execute(() -> {
                    while (true) {
                        data.inc();
                    }
                });
            } else {
                es.execute(() -> {
                    while (true) {
                        data.dec();
                    }
                });
            }
        }
    }
}

class Data {
    public int count = 10;

    public synchronized void inc() {
        count ++;
        System.out.println(Thread.currentThread().getName() + "增加1：" + count);
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public synchronized void dec() {
        if (count > 0) {
            count --;
            System.out.println(Thread.currentThread().getName() + "减少1：" + count);
        }
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
