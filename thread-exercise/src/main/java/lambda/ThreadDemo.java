package lambda;

public class ThreadDemo {

    public static void main(String[] args) {
        new Thread(new Runnable() {
            @Override
            public void run() {

            }
        }).start();

        // jdk8 lambda
        new Thread(() -> System.out.println("ok")).start();

        Runnable target = () -> System.out.println("ok");
    }
}
