package jdk;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamDetailDemo {
    private static List<PersonModel> list = null;

    static {
        PersonModel wu = new PersonModel("wu qi", 18, "男");
        PersonModel zhang = new PersonModel("zhang san", 19, "男");
        PersonModel wang = new PersonModel("wang si", 20, "女");
        PersonModel zhao = new PersonModel("zhao wu", 20, "男");
        PersonModel chen = new PersonModel("chen liu", 21, "男");
        list = Arrays.asList(wu, zhang, wang, zhao, chen);
    }

    public static List<PersonModel> getData() {
        return list;
    }

    public static void main(String[] args) {
//                flatMapString();
//                List<String> names = new ArrayList<>();
//                list.stream().forEach(i -> names.add(i.getName()));
//        System.out.println(names.size());
//        names.stream().forEach(System.out::println);

        Thread t1 = new Thread(() -> {
            System.out.println(Thread.currentThread().getName());
        });
        t1.start();
    }

    /**
     * 过滤掉所有的男性
     */
    public static void filterSex() {
        List<PersonModel> data = StreamDetailDemo.getData();

        // old
        List<PersonModel> temp=new ArrayList<>();
        for (PersonModel person:data) {
            if ("男".equals(person.getSex())){
                temp.add(person);
            }
        }
        System.out.println(temp);

        // new
        List<PersonModel> collect = data.
                                    stream().
                                    filter(person -> "男".equals(person.getSex())).
                                    collect(Collectors.toList());
        collect.forEach(System.out::println);

    }

    /**
     * 过滤所有的男性 并且小于20岁
     */
    public static void fiterSexAndAge(){
        List<PersonModel> data = StreamDetailDemo.getData();

        //old
        List<PersonModel> temp=new ArrayList<>();
        for (PersonModel person:data) {
            if ("男".equals(person.getSex())&&person.getAge()<20){
                temp.add(person);
            }
        }

        //new 1
        List<PersonModel> collect = data
                .stream()
                .filter(person -> {
                    if ("男".equals(person.getSex())&&person.getAge()<20){
                        return true;
                    }
                    return false;
                })
                .collect(Collectors.toList());
        //new 2
        List<PersonModel> collect1 = data
                .stream()
                .filter(person -> ("男".equals(person.getSex())&&person.getAge()<20))
                .collect(Collectors.toList());

    }

    /**
     * 取出所有的用户名字
     */
    public static void getUserNameList() {
        List<PersonModel> data = StreamDetailDemo.getData();

        // old
        List<String> list = new ArrayList<>();
        for (PersonModel person : data) {
            list.add(person.getName());
        }
        System.out.println(list);

        // new 1
        List<String> collect = data.stream().map(person -> person.getName()).collect(Collectors.toList());
        System.out.println(collect);

        // new 2
        List<String> collect1 = data.stream().map(PersonModel::getName).collect(Collectors.toList());
        System.out.println(collect1);

        // new 3
        List<String> collect2 = data.stream().map(person -> {
            System.out.println(person.getName());
            return person.getName();
        }).collect(Collectors.toList());
    }

    /**
     * map和flatMap的区别：我个人认为，flatMap的可以处理更深层次的数据，
     * 入参为多个list，结果可以返回为一个list，而map是一对一的，入参是多个list，
     * 结果返回必须是多个list。通俗的说，如果入参都是对象，那么flatMap可以操作对象里面的对象，
     * 而map只能操作第一层。
     */
    public static void flatMapString() {
        List<PersonModel> data = StreamDetailDemo.getData();

        //返回类型不一样
        List<String> collect = data.stream()
                .flatMap(person -> Arrays.stream(person.getName().split(" "))).collect(Collectors.toList());
        System.out.println(collect);

        List<Stream<String>> collect1 = data.stream()
                .map(person -> Arrays.stream(person.getName().split(" "))).collect(Collectors.toList());
        System.out.println(collect1);

        //用map实现
        List<String> collect2 = data.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(Arrays::stream).collect(Collectors.toList());
        System.out.println(collect2);

        //另一种方式
        List<String> collect3 = data.stream()
                .map(person -> person.getName().split(" "))
                .flatMap(str -> Arrays.asList(str).stream()).collect(Collectors.toList());
        System.out.println(collect3);

    }

}

class PersonModel {
    private String name;
    private int age;
    private String sex;

    public PersonModel(String name, int age, String sex) {
        this.name = name;
        this.age = age;
        this.sex = sex;
    }

    public PersonModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
