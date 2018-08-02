package com.qbk.aware;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * Created by 13624 on 2018/8/2.
 * 获取ApplicationContext
 */
@Component
public class MyApplicationContextAware implements ApplicationContextAware {

    private static  ApplicationContext context ;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext ;
        System.out.println("获取applicationContext成功");
    }

    public static ApplicationContext getApplicationContext(){
       return context;
    }
}
