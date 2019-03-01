package test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

public class AirThreadDemo3 extends Thread {
    private TestDo testDo;
    private String key;
    private String value;

    public AirThreadDemo3(String key, String key2, String value) {
        this.testDo = TestDo.getInstance();
        /*
        常量"1"和"1"是用一个对象，下面这行代码就是要用"1"+""的方式产生新的对象，
        以实现内容没有改变，仍然相等（都还为"1"），但对象却不再是同一个效果。
         */
        this.key = key + key2;
        this.value = value;
    }

    public static void main(String[] args) {
        AirThreadDemo3 a = new AirThreadDemo3("1", "", "1");
        AirThreadDemo3 b = new AirThreadDemo3("1", "", "2");
        AirThreadDemo3 c = new AirThreadDemo3("3", "", "3");
        AirThreadDemo3 d = new AirThreadDemo3("4", "", "4");
        AirThreadDemo3 e = new AirThreadDemo3("4", "", "5");
        System.out.println(System.currentTimeMillis()/1000);
        a.start();
        b.start();
        c.start();
        d.start();
        e.start();
    }

    @Override
    public void run() {
        testDo.doSome(key, value);
    }
}

class TestDo {
    private TestDo() {}
    private static TestDo instance = new TestDo();
    public static TestDo getInstance() {
        return instance;
    }

    private List<Object> keys = new CopyOnWriteArrayList<>();

    public void doSome(Object key, String value) {
        String lock = (String) key;
        final String l = lock;
        if (!keys.contains(key)) {
            keys.add(key);
        } else {
            // 遍历过滤得到原来的锁
            lock = (String) keys.stream()
                    .filter(k -> k.equals(l))
                    .collect(Collectors.toList())
                    .get(0);
        }
        synchronized (lock) {
            try {
                Thread.sleep(1000);
                System.out.println(key+":"+value+":"+(System.currentTimeMillis()/1000));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}