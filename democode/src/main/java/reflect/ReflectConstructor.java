package reflect;

import java.lang.reflect.Constructor;

/**
 * 通过Class对象可以获取某个类中的：构造方法、成员变量、成员方法；并访问成员；
 *
 * 1.获取构造方法：
 *      1).批量的方法：
 *          public Constructor[] getConstructors()：所有”公有的”构造方法
            public Constructor[] getDeclaredConstructors()：获取所有的构造方法(包括私有、受保护、默认、公有)

 *      2).获取单个的方法，并调用：
 *          public Constructor getConstructor(Class… parameterTypes):获取单个的”公有的”构造方法：
 *          public Constructor getDeclaredConstructor(Class… parameterTypes):获取”某个构造方法”可以是私有的，或受保护、默认、公有；
 *
 *          调用构造方法：
 *          Constructor–>newInstance(Object… initargs)
 */
public class ReflectConstructor {
    public static void main(String[] args) throws Exception {
        Class clazz = Class.forName("reflect.Student");

        // 获取所有的公有构造方法
        System.out.println("***********所有公有构造方法*************");
        Constructor[] conArray = clazz.getConstructors();
        for (Constructor constructor : conArray) {
            System.out.println(constructor);
        }


        System.out.println("***********所有的构造方法(包括：私有、受保护、默认、公有)*************");
        conArray = clazz.getDeclaredConstructors();
        for (Constructor constructor : conArray) {
            System.out.println(constructor);
        }

        // 获取所有公有无参构造方法
        System.out.println("***********所有无参构造方法***********");
        Constructor constructor = clazz.getConstructor(null);
        //1>、因为是无参的构造方法所以类型是一个null,不写也可以：这里需要的是一个参数的类型，切记是类型
        //2>、返回的是描述这个无参构造函数的类对象。

        System.out.println("constructor = " + constructor);
        //调用构造方法
        Object obj = constructor.newInstance();
//        System.out.println("obj = " + obj);
//        Student stu = (Student)obj;

        System.out.println("******************获取私有构造方法，并调用*******************************");
        Constructor con = clazz.getDeclaredConstructor(char.class);
        System.out.println(con);
        //调用构造方法
        con.setAccessible(true);//暴力访问(忽略掉访问修饰符)
        obj = con.newInstance('男');
    }

}
