package com.qbk;

import com.qbk.aware.MyApplicationContextAware;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by 13624 on 2018/8/3.
 * FactoryBean 和 ApplicationContextAware  测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class FactoryBeanTests {

    @Test
    public void get(){
        //使用ApplicationContextAware 中获取得 ApplicationContext 容器
        ApplicationContext context= MyApplicationContextAware.getApplicationContext();
        //使用FactoryBean 获取其工厂生产得 bean
        Object o1=context.getBean("myFactoryBean");
        //获取 FactoryBean  获取工厂bean
        Object o2=context.getBean("&myFactoryBean");
        System.out.println(o1.getClass());//class com.qbk.bean.Test
        System.out.println(o2.getClass());//class com.qbk.aware.MyFactoryBean
        //判断是不是单利
        com.qbk.bean.Test t1 = (com.qbk.bean.Test) context.getBean("myFactoryBean");
        com.qbk.bean.Test t2 = ( com.qbk.bean.Test) context.getBean("myFactoryBean");
        System.out.println(t1==t2);

    }
}
