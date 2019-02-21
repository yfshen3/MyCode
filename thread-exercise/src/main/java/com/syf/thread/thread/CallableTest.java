package com.syf.thread.thread;

import java.util.concurrent.*;

/**
 * @author yfshen
 */
public class CallableTest {
    public static void main(String[] args) throws Exception {

        ExecutorService es = Executors.newSingleThreadExecutor();
        Future<String> future =es.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(2000);
                return "hello";
            }
        });
        System.out.println(future.get());
        es.shutdown();
    }
}
