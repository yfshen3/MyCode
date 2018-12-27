package lambda;

public class LambdaDemo {

    public static void main(String[] args) {
        Interface1 i1 = (i) -> i * 2;

        // 最常见的写法
        Interface1 i2 = i -> i * 2;
        System.out.println(i2.doubleNum(2));
        Interface1 i3 = (int i) -> i * 2;

        Interface1 i4 = (int i) -> {
            System.out.println("----");
            return i*2;
        };
    }
}

/**
 *  接口里面只有一个要实现的方法才行
 */
// 定义是一个函数接口
@FunctionalInterface
interface Interface1 {
    int doubleNum(int i);

    default int add(int x, int y) {
        return x + y;
    }
}
