package com.qbk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;
import java.util.Map;

/**
 * 事务管理器配置， 可自定义多个事务管理器
 * springboot也会帮我们自动加载一个名为transactionManager的事务管理器
 *EnableTransactionManagement 启事务管理，等同于xml配置方式的 <tx:annotation-driven />
 */
@Configuration
@EnableTransactionManagement()
public class TransactionManagementConfig {//implements TransactionManagementConfigurer

//    @Resource(name = "txManager1")
//    private PlatformTransactionManager txManager1;

    // 创建事务管理器1
    @Bean(name = "txManager1")
    public PlatformTransactionManager txManager1(
            @Qualifier("dataSourceOne") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    // 创建事务管理器2
    @Bean(name = "txManager2")
    public PlatformTransactionManager txManager2(
            @Qualifier("dataSourceTwo") DataSource dataSource) {
        return new DataSourceTransactionManager(dataSource);
    }

    //  实现接口 TransactionManagementConfigurer 方法，其返回值代表在拥有多个事务管理器的情况下默认使用的事务管理器
//    @Override
//    public PlatformTransactionManager annotationDrivenTransactionManager() {
//        return txManager1;
//    }

}
