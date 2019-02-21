package com.syf.thread.thread.blockingqueue;

import java.util.Random;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author yfshen
 */
public class BlockingQueueTest {
    public static void main(String[] args) {
//        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(1);
        BlockingQueue<Integer> queue = new ArrayBlockingQueue<>(1);
        ExecutorService es = Executors.newFixedThreadPool(4);
        es.execute(new Product(queue));
        es.execute(new Product(queue));
        es.execute(new Consumer(queue));
        es.execute(new Consumer(queue));
    }
}

class Product implements Runnable {
    private BlockingQueue<Integer> queue;
    public Product(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(3000));
                if (queue.isEmpty()) {
                    queue.put(1);
                    System.out.println(Thread.currentThread().getName() + "放入数据");
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}

class Consumer implements Runnable {
    private BlockingQueue<Integer> queue;
    public Consumer(BlockingQueue<Integer> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(new Random().nextInt(3000));
                Integer i = queue.take();
                System.out.println(Thread.currentThread().getName() + "取走" + i);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
