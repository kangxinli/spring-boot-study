package com.sample.app.utils;

import java.util.HashMap;
import java.util.Map;

import org.springframework.cglib.beans.BeanMap;

/**
 * 对象与map的互换
 * @author user
 *
 */
public class BeanUtils {
	
	/**
     * 将对象转换为map
     *
     * @param bean
     * @return
     */
    public static <T> Map<String, Object> beanToMap(T bean) {
        Map<String, Object> map = new HashMap<>();
        if (bean != null) {
            BeanMap beanMap = BeanMap.create(bean);
            for (Object key : beanMap.keySet()) {
                map.put(key.toString(), beanMap.get(key));
            }
        }
        return map;
    }

    /**
     * 将map转换为javabean对象
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
    
    public static void main(String[] args) {
	}

}
