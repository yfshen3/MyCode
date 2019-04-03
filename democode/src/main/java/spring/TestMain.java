package spring;

import spring.beans.Teacher;
import spring.ioc.BeanDefined;
import spring.ioc.BeanFactory;

import java.util.ArrayList;
import java.util.List;

public class TestMain {

    public static void main(String[] args) throws Exception {
        BeanDefined beanObj = new BeanDefined();
        beanObj.setBeanId("teacher");
        beanObj.setClassPath("spring.beans.Teacher");
//        beanObj.setScope("prototype");

        List beanList = new ArrayList();
        beanList.add(beanObj);

        BeanFactory factory = new BeanFactory(beanList);

        Teacher t = (Teacher) factory.getBean("teacher");
        System.out.println("t=" + t);
        Teacher t2 = (Teacher) factory.getBean("teacher");
        System.out.println("t=" + t2);
    }
}
