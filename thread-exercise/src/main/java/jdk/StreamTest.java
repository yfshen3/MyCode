package jdk;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * 什么是 Stream？
 * Stream（流）是一个来自数据源的元素队列并支持聚合操作
 *
 * 元素是特定类型的对象，形成一个队列。 Java中的Stream并不会存储元素，而是按需计算。
 * 数据源 流的来源。 可以是集合，数组，I/O channel， 产生器generator 等。
 * 聚合操作 类似SQL语句一样的操作， 比如filter, map, reduce, find, match, sorted等。
 * 和以前的Collection操作不同， Stream操作还有两个基础的特征：
 *
 * Pipelining: 中间操作都会返回流对象本身。 这样多个操作可以串联成一个管道，
 *  如同流式风格（fluent style）。 这样做可以对操作进行优化， 比如延迟执行(laziness)和短路( short-circuiting)。
 * 内部迭代： 以前对集合遍历都是通过Iterator或者For-Each的方式,
 *  显式的在集合外部进行迭代， 这叫做外部迭代。 Stream提供了内部迭代的方式， 通过访问者模式(Visitor)实现。
 */
public class StreamTest {
    /**
     * 在 Java 8 中, 集合接口有两个方法来生成流：
     * stream() − 为集合创建串行流。
     * parallelStream() − 为集合创建并行流。
     */

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("abc", "", "bc", "efg", "abcd", "", "jkl");
        List<String> filters = strings.stream().filter(string -> !string.isEmpty()).collect(Collectors.toList());


        /**
         * forEach test
         * Stream 提供了新的方法 'forEach' 来迭代流中的每个数据。
         */
        //        filters.forEach(s -> System.out.println(s)); // 与下面一行代码效果相同
        filters.forEach(System.out::println);

//        int[] arr = {1,2,5,10,-1};
//        int min = IntStream.of(arr).min().getAsInt();
//        System.out.println(min);

        /**
         * map test
         * map 方法用于映射每个元素到对应的结果
         */
        List<Integer> numbers = Arrays.asList(3, 2, 2, 3, 7, 3, 5);
        List<Integer> squaresList = numbers.stream().map(i -> i*i).distinct().collect(Collectors.toList());
        squaresList.stream().forEach(System.out::println);

        /**
         * filter test
         * filter 方法用于通过设置的条件过滤出元素
         * 同上 forEach
         */

        /**
         * limit test
         * limit 方法用于获取指定数量的流
         */
        new Random().ints().limit(10).forEach(System.out::println);

        /**
         * sorted test
         * sorted 方法用于对流进行排序
         */
        List<Integer> sortedList = numbers.stream().sorted().distinct().collect(Collectors.toList());
        sortedList.forEach(System.out::println);

        /**
         * 并行(parallel)程序
         * parallelStream 是流并行处理程序的代替方法
         */
        int count = (int)strings.parallelStream().filter(string -> string.isEmpty()).count();
        System.out.println(count);
    }
}
