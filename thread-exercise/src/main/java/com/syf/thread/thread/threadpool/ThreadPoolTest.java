package com.syf.thread.thread.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * shutdown只是将线程池的状态设置为SHUTWDOWN状态，正在执行的任务会继续执行下去，
 * 没有被执行的则中断。而shutdownNow则是将线程池的状态设置为STOP，正在执行的任务
 * 则被停止，没被执行任务的则返回。
 * @author yfshen
 */
public class ThreadPoolTest {
    public static void main(String[] args) throws InterruptedException {
        IntegerData data = new IntegerData();

        ExecutorService es = Executors.newFixedThreadPool(3);
        for (int j = 0; j < 10; j++) {
            es.execute(() -> data.add());
        }

        Thread.sleep(3000);

        for (int j = 0; j < 3; j++) {
            es.execute(() ->
                    System.out.println(Thread.currentThread().getName() + ":" + data.get()));
        }

        es.shutdown();
    }
}

class IntegerData {
    ThreadLocal<Integer> threadLocal = new ThreadLocal(){
        @Override
        protected Object initialValue() {
            return 0;
        }
    };

    public void add() {
        threadLocal.set(threadLocal.get() + 1);
    }

    public int get() {
        return threadLocal.get();
    }
}
