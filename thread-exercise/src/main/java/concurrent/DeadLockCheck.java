package concurrent;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

/**
 * 死锁检查
 * @author yfshen
 */
public class DeadLockCheck {
    private final static ThreadMXBean mbean = ManagementFactory.getThreadMXBean();
    final static Runnable deadLockCheck = new Runnable() {
        @Override
        public void run() {
            while (true) {
                long[] deadLockThreadIds = mbean.findDeadlockedThreads();
                if (deadLockThreadIds != null) {
                    ThreadInfo[] threadInfos = mbean.getThreadInfo(deadLockThreadIds);
                    for (Thread t : Thread.getAllStackTraces().keySet()) {
                        for (ThreadInfo info : threadInfos) {
                            if (t.getId() == info.getThreadId()) {
                                t.interrupt();
                            }
                        }
                    }
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                }
            }
        }
    };

    public static void check() {
        Thread t = new Thread(deadLockCheck);
        t.setDaemon(true);
        t.start();
    }
}
