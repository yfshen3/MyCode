package jdk;

import java.util.HashMap;

public class MapJdk8Test {

    public static void main(String[] args) {
        HashMap<Integer, String> map = new HashMap<>();
        map.put(1, "one");
        map.put(2, "two");
        map.put(3, "three");

        /**
         * forEach
         * 该方法签名为void forEach(BiConsumer<? super K, ? super V> action)，
         * 作用是对Map中每个映射执行action指定的操作，
         * 其中BiConsumer是一个函数接口，里面有一个待实现方法void accept(T t, U u)。
         * BinConsumer接口名字和accept()方法名字都不重要，不须记住
          */
        map.forEach((k, v) -> System.out.println(k + " = " +v.toUpperCase()));


        /**
         * getOrDefault()
         * 该方法跟Lambda表达式没关系，但是很有用。方法签名为V getOrDefault(Object key, V defaultValue)，
         * 作用是按照给定的key查询Map中对应的value，如果没有找到则返回defaultValue。
         * 使用该方法程序员可以省去查询指定键值是否存在的麻烦。
         */
        // 查询Map中指定的值，不存在时使用默认值
        System.out.println(map.getOrDefault(3, "NoValue")); //three
        System.out.println(map.getOrDefault(4, "NoValue")); //NoValue

        /**
         * putIfAbsent()
         * 该方法跟Lambda表达式没关系，但是很有用。方法签名为V putIfAbsent(K key, V value)，
         * 作用是只有在不存在key值的映射或映射值为null时，才将value指定的值放入到Map中，否则不对Map做更改。
         * 该方法将条件判断和赋值合二为一，使用起来更加方便。
         */

        /**
         * remove()
         * 我们都知道Map中有一个remove(Object key)方法，来根据指定key值删除Map中的映射关系；
         * Java8新增了remove(Object key, Object value)方法，
         * 只有在当前Map中key正好映射到value时才删除该映射，否则什么也不做。
         */

        /**
         * replace()
         * 在Java7及以前，要想替换Map中的映射关系可通过put(K key, V value)方法来实现，
         * 该方法总是会用新值替换原来的值，为了更精确的控制替换行为，Java8在Map中加入了两个replace()方法，
         * 分别如下：
         * replace(K key, V value)，只有在当前Map中key的映射存在时才用value去替换原来的值，否则什么也不做。
         * replace(K key, V oldValue, V newValue)，只有在当前Map中key的映射存在且等于oldValue时才用newValue去替换原来的值，否则什么也不做。
         */

        /**
         * replaceAll()
         * 该方法签名为replaceAll(BiFunction<? super K, ? super V, ? extends V> function)，
         * 作用是对Map中的每个映射执行function指定的操作，并用function的执行结果替换原来的value，
         * 其中BiFunction是一个函数接口，里面有一个待实现方法R apply(T t, U u)，不要被如此多的函数接口吓到，
         * 因为使用的时候根本不需要知道他们的名字。
         */

    }

}
