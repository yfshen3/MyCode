package jdk.lambda;

/**
 * lambda 表达式只能引用标记了 final 的外层局部变量，
 * 这就是说不能在 lambda 内部修改定义在域外的局部变量，否则会编译错误。
 */
public class Demo1 {
    public static void main(String[] args) {
        final String SALUATION = "hello! ";
        GreetingService greetingService =
                message -> SALUATION + message;
        System.out.println(greetingService.sayMessage("shenyifan"));
    }
    interface GreetingService {

        String sayMessage(String message);
    }
}
