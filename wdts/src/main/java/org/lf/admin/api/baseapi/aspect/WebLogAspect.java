package org.lf.admin.api.baseapi.aspect;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

@Aspect
@Component
public class WebLogAspect {
    /**
     * 要处理的方法，包名+类名+方法名
     */
    @Pointcut("execution(public * org.lf.admin.api.baseapi.controller.*.*(..))")
    public void cut(){
    }

    /**
     * 无论Controller中调用方法以何种方式结束，都会执行
     */
    @After("cut()")
    public void doAfter(JoinPoint joinPoint) {
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpSession session = request.getSession();

        String deviceNumber = request.getParameter("deviceNumber");
        if (deviceNumber != null) {
//            System.out.println(deviceNumber);
        }

//        System.out.println("URL :" + request.getRequestURL().toString());
//        // 可以通过url 判断采取的是什么操作
//        System.out.println("METHOD :" +request.getMethod());
//        System.out.println("IP :" + request.getRemoteAddr());
//        System.out.println("CLASS_METHOD :" + joinPoint.getSignature().getDeclaringTypeName()
//                + "." + joinPoint.getSignature().getName());
//        System.out.println("ARGS :" + Arrays.toString(joinPoint.getArgs()));

        /**
         * 插入日志表中的具体操作
         * TODO
         */
    }

}
