package reflect;

/**
 * 获取Class对象的三种方式
 * 1 Object ——> getClass();
 * 2 任何数据类型（包括基本数据类型）都有一个“静态”的class属性
 * 3 通过Class类的静态方法：forName（String  className）(常用)
 *
 *
 *  三种方式常用第三种，第一种对象都有了还要反射干什么。
 *  第二种需要导入类的包，依赖太强，不导包就抛编译错误。
 *  一般都第三种，一个字符串可以传入也可写在配置文件中等多种方法。
 */
public class Reflect1 {

    public static void main(String[] args) {
        //这一new 产生一个Student对象，一个Class对象。
        Student stu = new Student();
        // 1
        Class stuClass1 = stu.getClass();
        System.out.println(stuClass1.getName());

        // 2
        Class stuClass2 = Student.class;
        System.out.println(stuClass1 == stuClass2);

        // 3
        try {
            Class stuClass3 = Class.forName("reflect.Student");
            System.out.println(stuClass2 == stuClass3);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
