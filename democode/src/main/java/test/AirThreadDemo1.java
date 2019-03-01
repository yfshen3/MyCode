package test;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class AirThreadDemo1 {
    public static void main(String[] args) {
        BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);
        System.out.println(System.currentTimeMillis()/1000);
        for (int i = 0; i < 4; i++) {
            new Thread(() -> {
                while (true) {
                    try {
                        String log = queue.take();
                        parseLog(log);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }

        for (int i = 0; i < 16; i++) {
            final String log = ""+(i+1);
//            parseLog(log);
            try {
                queue.put(log);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void parseLog(String log) {
        System.out.println(log + ":" + (System.currentTimeMillis()/1000));
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
