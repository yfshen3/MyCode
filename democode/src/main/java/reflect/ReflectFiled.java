package reflect;

import java.lang.reflect.Field;

/**
 * 获取成员变量并调用：
 *
 * 1.批量的
 *      1).Field[] getFields():获取所有的"公有字段"
 *      2).Field[] getDeclaredFields():获取所有字段，包括：私有、受保护、默认、公有；
 * 2.获取单个的：
 *      1).public Field getField(String fieldName):获取某个"公有的"字段；
 *      2).public Field getDeclaredField(String fieldName):获取某个字段(可以是私有的)
 *
 *   设置字段的值：
 *      Field --> public void set(Object obj,Object value):
 *                  参数说明：
 *                  1.obj:要设置的字段所在的对象；
 *                  2.value:要为字段设置的值；
 *
 */
public class ReflectFiled {

    public static void main(String[] args) throws Exception {
        Class teacherClass = Class.forName("reflect.Teacher");

        // 获取字段
        System.out.println("************获取所有公有的字段********************");
        Field[] fields = teacherClass.getFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("************获取所有的字段(包括私有、受保护、默认的)********************");
        fields = teacherClass.getDeclaredFields();
        for (Field field : fields) {
            System.out.println(field);
        }

        System.out.println("*************获取公有字段**并调用***********************************");
        Field field = teacherClass.getField("name");
        System.out.println(field);

        //获取一个对象
        Object obj = teacherClass.getConstructor().newInstance();//产生Student对象--》Student stu = new Student();
        //为字段设置值
        field.set(obj, "刘德华");//为Teacher对象中的name属性赋值--》teacher.name = "刘德华"
        //验证
        Teacher teacher = (Teacher) obj;
        System.out.println("验证姓名:" + teacher.name);


        System.out.println("**************获取私有字段****并调用********************************");
        field = teacherClass.getDeclaredField("phoneNum");
        System.out.println(field);
        field.setAccessible(true);
        field.set(obj, "18571731303");
        System.out.println(teacher);
    }
}
