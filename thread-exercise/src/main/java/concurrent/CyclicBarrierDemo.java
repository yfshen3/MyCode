package concurrent;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 循环栅栏：也就是说这个计数器可以反复使用。
 * 比如：假设我们将计数器设置为10，那么一批10个线程之后，
 * 计数器就会归零，然后接着凑齐下一批10个线程。
 * @author yfshen
 *
 * public CyclicBarrier(int parties, Runnable barrierActoin)
 * barrierAction就是当计数器一次计数完成后，系统就会执行的动作
 */
public class CyclicBarrierDemo {
    public static void main(String[] args) {
        final int N = 10;
        ExecutorService es = Executors.newFixedThreadPool(N);
        boolean flag = false;
        CyclicBarrier cyclic = new CyclicBarrier(N, new BarrierRun(flag, N));
        // 设置屏障点，主要是为了执行这个方法
        System.out.println("集合队伍");
        for (int i = 0; i < N; i++) {
            System.out.println("士兵" + i + "报道");
            es.execute(new Soldier(cyclic, "士兵" + i));
        }
        es.shutdown();
    }
}

class Soldier implements Runnable {
    private String soldier;
    private final CyclicBarrier cyclic;
    Soldier(CyclicBarrier cyclic, String soldier) {
        this.cyclic = cyclic;
        this.soldier = soldier;
    }

    @Override
    public void run() {
        try {
            // 等待所有士兵到齐
            cyclic.await();
            doWork();
            // 等待所有士兵完成工作
            cyclic.await();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void doWork() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(soldier + ":任务完成");
    }
}

class BarrierRun implements Runnable {
    boolean flag;
    int N;
    public BarrierRun(boolean flag, int N) {
        this.flag = flag;
        this.N = N;
    }
    @Override
    public void run() {
        if (flag) {
            System.out.println("司令：[士兵" + N + "个，任务完成]");
        } else {
            System.out.println("司令：[士兵" + N + "个，集合完毕]");
            flag = true;
        }
    }
}
