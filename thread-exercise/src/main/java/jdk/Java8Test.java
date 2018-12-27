package jdk;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Java8Test {

    public static void main(String[] args) {

        List<String> names1 = new ArrayList<>();
        names1.add("Google");
        names1.add("Runoob");
        names1.add("Taobao");
        names1.add("Baidu");
        names1.add("Sina");

        List<String> names2 = new ArrayList<String>();
        names2.add("Google");
        names2.add("Runoob");
        names2.add("Taobao");
        names2.add("Baidu");
        names2.add("Sina");

        Java8Test test = new Java8Test();
        System.out.println("使用java7 语法: ");
        test.sortUsingJava7(names1);
        System.out.println(names1);

        System.out.println("使用java8 语法: ");
        test.sortUsingJava8(names2);
        System.out.println(names2);
    }

    /**
     * java7排序
     * @param names
     */
    private void sortUsingJava7(List<String> names) {
        Collections.sort(names, new Comparator<String>() {
            @Override
            public int compare(String o1, String o2) {
                return o1.compareTo(o2);
            }
        });
    }

    /**
     * java8 排序
     * @param names
     */
    private void sortUsingJava8(List<String> names) {
        Collections.sort(names, (s1, s2) -> s1.compareTo(s2));
    }
}
