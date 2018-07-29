package com.qbk.config;

import org.aspectj.lang.annotation.Aspect;
import org.springframework.aop.Advisor;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import org.springframework.aop.framework.autoproxy.BeanNameAutoProxyCreator;
import org.springframework.aop.support.DefaultPointcutAdvisor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.interceptor.*;

import javax.annotation.Resource;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 事务的java配置方式
 */
@Configuration
//@AutoConfigureAfter(TransactionManagementConfig.class)
//@AutoConfigureAfter：在指定的配置类初始化后再加载
//@AutoConfigureBefore：在指定的配置类初始化前加载
//@AutoConfigureOrder：数越小越先初始化
public class TxAdviceInterceptor {

    //springboot会帮我们自动加载一个名为transactionManager的事务管理器
//    @Autowired
//    private DataSourceTransactionManager transactionManager;

    //注入自定义事务管理器
    @Resource(name = "txManager1")
    private PlatformTransactionManager txManager1;
    @Resource(name = "txManager2")
    private PlatformTransactionManager txManager2;

    // 创建事务拦截器1
    @Bean(name = "transactionInterceptor1")
    public TransactionInterceptor ransactionInterceptor() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
        TransactionInterceptor tsi = new TransactionInterceptor(txManager1,properties);
        return tsi;
    }

    // 创建事务代理1
    @Bean
    public BeanNameAutoProxyCreator txProxy1() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("transactionInterceptor1");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }

    // 创建事务拦截器2
    @Bean(name = "transactionInterceptor2")
    public TransactionInterceptor transactionInterceptor2() throws Exception {
        Properties properties = new Properties();
        properties.setProperty("get*", "PROPAGATION_REQUIRED,-Exception,readOnly");
        properties.setProperty("add*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("save*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("update*", "PROPAGATION_REQUIRED,-Exception");
        properties.setProperty("delete*", "PROPAGATION_REQUIRED,-Exception");
        TransactionInterceptor tsi = new TransactionInterceptor(txManager2,properties);
        return tsi;
    }

    // 创建事务代理2
    @Bean
    public BeanNameAutoProxyCreator txProxy2() {
        BeanNameAutoProxyCreator creator = new BeanNameAutoProxyCreator();
        creator.setInterceptorNames("transactionInterceptor2");
        creator.setBeanNames("*Service", "*ServiceImpl");
        creator.setProxyTargetClass(true);
        return creator;
    }
}