package com.ypc.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

public class SpringContextsUtil implements ApplicationContextAware {
 
    private static ApplicationContext applicationContext;
    @Override
    public void setApplicationContext(ApplicationContext applicationContext)
            throws BeansException {
        SpringContextsUtil.applicationContext = applicationContext;
    }
 
    public static Object getBean(String beanName) {
        return applicationContext.getBean(beanName);
    }
    
    public static <T> T getBean(Class<T> clazs) {
        return applicationContext.getBean(clazs);
    }
 
    public static <T> T getBean(String beanName, Class<T> clazs) {
        return clazs.cast(getBean(beanName));
    }
    
    public static <T> Map<String, T> getBeans(Class<T> clazs) {
        return applicationContext.getBeansOfType(clazs);
    }
}
