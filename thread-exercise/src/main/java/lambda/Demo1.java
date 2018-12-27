package lambda;

import java.util.stream.IntStream;

/**
 * 找到最小值
 * @author yfshen
 */
public class Demo1 {
    public static void main(String[] args) {
        int[] nums = {33,55,-55,90,-666,90};

        // jdk8
        int min = IntStream.of(nums).parallel().min().getAsInt();
        System.out.println(min);
    }
}
