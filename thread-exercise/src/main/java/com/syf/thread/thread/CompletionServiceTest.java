package com.syf.thread.thread;

import java.util.Random;
import java.util.concurrent.*;

/**
 * CompletionService用于提交一组Callable任务，其take方法
 * 返回已完成的一个Callable任务对应的Future对象
 * @author yfshen
 */
public class CompletionServiceTest {
    public static void main(String[] args) throws Exception {
        ExecutorService es = Executors.newFixedThreadPool(10);
        CompletionService<String> completionService = new ExecutorCompletionService(es);
        for (int i = 0; i < 10; i++) {
            int seq = i;
            completionService.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    Thread.sleep(new Random().nextInt(5000));
                    return Thread.currentThread().getName();
                }
            });
        }

        for (int i = 0; i < 10; i++) {
            System.out.println(completionService.take().get());
        }
        es.shutdown();
    }
}
