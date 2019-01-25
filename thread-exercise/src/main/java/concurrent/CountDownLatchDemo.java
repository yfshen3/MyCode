package concurrent;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 栅栏：计数器
 * @author yfshen
 */
public class CountDownLatchDemo {
    static final CountDownLatch END = new CountDownLatch(10);

    public static void main(String[] args) {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for (int i = 0; i < 10; i++) {
            exec.submit(() -> {
                try {
                    // 模拟检查任务
                    Thread.sleep(new Random().nextInt(10) * 1000);
                    System.out.println("check complete");
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    END.countDown();
                }
            });
        }
        // 等待检查
        try {
            END.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("fire");
        exec.shutdown();
    }
}
