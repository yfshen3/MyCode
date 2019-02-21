package com.syf.thread.thread;

import java.util.concurrent.Exchanger;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 用于实现两个线程之间的数据交换，每个人在完成一定事务后想与
 * 对方交换数据，第一个先拿到数据的人将一直等待第二个人拿着数
 * 据到来时，才能彼此交换数据。
 * @author yfshen
 */
public class ExchangerTest {
    private static final Exchanger exchange = new Exchanger();
    public static void main(String[] args) {
        ExecutorService es = Executors.newCachedThreadPool();

        es.execute(() -> {
            try {
                String out = "money";
                System.out.println("线程" + Thread.currentThread().getName()
                        + "正在给钱");
                Thread.sleep(2000);
                String in = (String) exchange.exchange(out);
                System.out.println("线程" + Thread.currentThread().getName()
                        + "买到了" + in);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        es.execute(() -> {
            try {
                String out = "food";
                System.out.println("线程" + Thread.currentThread().getName()
                        + "正在装东西");
                Thread.sleep(1000);
                String in = (String) exchange.exchange(out);
                System.out.println("线程" + Thread.currentThread().getName()
                        + "收到了" + in);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });

        es.shutdown();
    }
}
