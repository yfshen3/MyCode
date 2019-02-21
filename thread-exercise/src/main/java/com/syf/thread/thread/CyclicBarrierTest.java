package com.syf.thread.thread;

import java.util.concurrent.BrokenBarrierException;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CyclicBarrierTest {
    public static void main(String[] args) {
        // 线程全部到达后执行的接口方法
        CyclicBarrier cyclicBarrier = new CyclicBarrier(3, () -> System.out.println("全部到达，去往下一个集合点"));
        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int i = 0; i < 3; i++) {
            int index = i;
            es.execute(() -> {
                try {
                    Thread.sleep(index * 500);
                    System.out.println("线程" + Thread.currentThread().getName() + "到达");
                    cyclicBarrier.await();

                    Thread.sleep(index * 500);
                    System.out.println("线程" + Thread.currentThread().getName() + "到达");
                    cyclicBarrier.await();
                } catch (InterruptedException | BrokenBarrierException e) {
                    e.printStackTrace();
                }
            });
        }
        es.shutdown();
    }

}