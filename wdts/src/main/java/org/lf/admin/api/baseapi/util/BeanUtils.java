package org.lf.admin.api.baseapi.util;

import org.springframework.cglib.beans.BeanMap;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * javabean相关工具类
 *
 * @author: sunwill
 * @date: 2018/5/12
 */
public class BeanUtils {

    /**
     * 将对象装换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>(16);
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                Object val = beanMap.get(key);
                if (val != null) {
                    map.put(String.valueOf(key), val);
                }
            }
        }
        return map;
    }

    /**
     * 将map装换为javabean对象
     *
     * @param map
     * @param bean
     * @return
     */
    public static <T> T mapToBean(Map<String, Object> map, T bean) {
        BeanMap beanMap = BeanMap.create(bean);
        beanMap.putAll(map);
        return bean;
    }

    /**
     * List<T>转换为List<Map<String,Object>>
     *
     * @param beanList
     * @param <T>
     * @return
     */
    public static <T> List<Map<String, Object>> beanToMapBatch(List<T> beanList) {
        List<Map<String, Object>> beanMapList = new ArrayList<>();
        if (beanList != null && beanList.size() > 0) {
            for (T bean : beanList) {
                Map<String, Object> beanMap = beanToMap(bean);
                beanMapList.add(beanMap);
            }
        }
        return beanMapList;
    }

    /**
     * List<Map<String,Object>>转换为List<T>
     *
     * @param beanMap
     * @param beanClass
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> List<T> mapToBeanBatch(List<Map<String, Object>> beanMap, Class<T> beanClass)
            throws InstantiationException, IllegalAccessException {
        List<T> beanList = new ArrayList<>();
        if (beanMap != null && beanMap.size() > 0) {
            for (Map<String, Object> tempMap : beanMap) {
                T bean = mapToBean(tempMap, beanClass);
                beanList.add(bean);
            }
        }
        return beanList;
    }

    /**
     * map转bean
     *
     * @param beanMap
     * @param beanClass
     * @param <T>
     * @return
     * @throws InstantiationException
     * @throws IllegalAccessException
     */
    public static <T> T mapToBean(Map<String, Object> beanMap, Class<T> beanClass) throws InstantiationException, IllegalAccessException {
        T bean = beanClass.newInstance();
        BeanMap tempMap = BeanMap.create(bean);
        tempMap.putAll(beanMap);
        return bean;
    }
}
