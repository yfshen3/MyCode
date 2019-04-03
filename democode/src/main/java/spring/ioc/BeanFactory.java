package spring.ioc;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BeanFactory {
    private List<BeanDefined> beanDefinedList;

    private Map<String, Object> springIoc;

    public BeanFactory (List<BeanDefined> beanDefinedList) throws Exception {
        this.beanDefinedList = beanDefinedList;
        springIoc = new HashMap<>();
        for (BeanDefined beanDefined : beanDefinedList) {
            if ("singleton".equals(beanDefined.getScope())) {
                Class clazz = Class.forName(beanDefined.getClassPath());
                Object instance = clazz.newInstance();
                springIoc.put(beanDefined.getBeanId(), instance);
            }
        }
    }

    public void setBeanDefinedList(List<BeanDefined> beanDefinedList) {
        this.beanDefinedList = beanDefinedList;
    }

    public Object getBean(String beanId) throws Exception {
        Object instance = null;
        for (BeanDefined beanDefined : beanDefinedList) {
            if (beanId.equals(beanDefined.getBeanId())) {
                if ("prototype".equals(beanDefined.getScope())) {
                    Class clazz = Class.forName(beanDefined.getClassPath());
                    instance = clazz.newInstance();
                } else { // singleton
                    instance = springIoc.get(beanId);
                }
                return instance;
            }
        }
        return  null;
    }
}
