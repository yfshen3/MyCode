package org.lf.admin.api.baseapi.aspect;

import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.springframework.stereotype.Component;

/**
 * 验证api参数aop
 *
 * @author: sunwill
 * @date: 2018/6/2
 */
//@Aspect
@Component
@Slf4j
public class ValidateApiParamAspect {

//    @Pointcut("within(org.lf.zhxy.api.msgapi.service.api.openapi..*)")
    public void openApiPointcut() {
    }

//    @Before("openApiPointcut()")
    public void deBefore(JoinPoint joinPoint) throws Throwable {

    }

}
