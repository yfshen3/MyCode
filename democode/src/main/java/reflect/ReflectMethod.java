package reflect;

import java.lang.reflect.Method;

/**
 * 获取成员方法并调用：
 *
 * 1.批量的：
 *      public Method[] getMethods():获取所有"公有方法"；（包含了父类的方法也包含Object类）
 *      public Method[] getDeclaredMethods():获取所有的成员方法，包括私有的(不包括继承的)
 * 2.获取单个的：
 *      public Method getMethod(String name,Class<?>... parameterTypes):
 *                  参数：
 *                      name : 方法名；
 *                      Class ... : 形参的Class类型对象
 *      public Method getDeclaredMethod(String name,Class<?>... parameterTypes)
 *
 *   调用方法：
 *      Method --> public Object invoke(Object obj,Object... args):
 *                  参数说明：
 *                  obj : 要调用方法的对象；
 *                  args:调用方式时所传递的实参；
 */
public class ReflectMethod {

    public static void main(String[] args) throws Exception {
        Class personClass = Class.forName("reflect.Person");

        // 获取所有的公有方法
        System.out.println("***************获取所有的”公有“方法*******************");
        Method[] methods = personClass.getMethods();
        for (Method method : methods) {
            System.out.println(method);
        }


        System.out.println("***************获取所有的方法，包括私有的*******************");
        methods = personClass.getDeclaredMethods();
        for (Method method : methods) {
            System.out.println(method);
        }


        System.out.println("***************获取公有的show1()方法*******************");
        Method method = personClass.getMethod("show1", String.class);
        System.out.println(method);
        // 实例化一个Person对象
        Object object = personClass.getConstructor().newInstance();
        method.invoke(object, "刘德华");

        System.out.println("***************获取私有的show4()方法******************");
        method = personClass.getDeclaredMethod("show4", int.class);
        System.out.println(method);
        //解除私有限定
        method.setAccessible(true);
        //需要两个参数，一个是要调用的对象（获取有反射），一个是实参
        Object result = method.invoke(object, 20);
        System.out.println("返回值:" + result);
    }

}
