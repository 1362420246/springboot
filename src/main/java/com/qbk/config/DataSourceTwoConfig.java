package com.qbk.config;

import com.alibaba.druid.spring.boot.autoconfigure.DruidDataSourceBuilder;
import org.apache.ibatis.mapping.DatabaseIdProvider;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.mybatis.spring.annotation.MapperScan;
import org.mybatis.spring.boot.autoconfigure.ConfigurationCustomizer;
import org.mybatis.spring.boot.autoconfigure.MybatisAutoConfiguration;
import org.mybatis.spring.boot.autoconfigure.MybatisProperties;
import org.mybatis.spring.boot.autoconfigure.SpringBootVFS;
import org.mybatis.spring.mapper.MapperScannerConfigurer;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.io.ResourceLoader;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.sql.DataSource;
import java.util.List;

/**
 * 多数据源配置 2
 */
@Configuration
//扫描dao接口的包用于识别mybatis
@MapperScan(basePackages="com.qbk.dao.two",sqlSessionTemplateRef="twoSqlSessionTemplate")
public class DataSourceTwoConfig  {

    /**
     * 配置数据源
     */
    @Bean("dataSourceTwo")
    @Qualifier("dataSourceTwo")
    @ConfigurationProperties(prefix = "spring.datasource.druid.two")
    public DataSource dataSourceTwo(){
        return DruidDataSourceBuilder.create().build();
    }

    /**
     * 配置session工厂
     * @param dataSource
     */
    @Bean(name = "twoSqlSessionFactory")
    public SqlSessionFactory twoSqlSessionFactory(@Qualifier("dataSourceTwo") DataSource dataSource) throws Exception {
        SqlSessionFactoryBean bean = new SqlSessionFactoryBean();
        bean.setDataSource(dataSource);
        bean.setTypeAliasesPackage("com.qbk.bean");//pojo别名
        bean.setVfs(SpringBootVFS.class);
        //扫描mapper文件
        bean.setMapperLocations(new PathMatchingResourcePatternResolver().getResources("classpath:com/qbk/dao/two/*.xml"));
        return bean.getObject();
    }

    /**
     * 模版
     * @param sqlSessionFactory
     */
    @Bean(name = "twoSqlSessionTemplate")
    public SqlSessionTemplate oneSqlSessionTemplate(@Qualifier("twoSqlSessionFactory") SqlSessionFactory sqlSessionFactory) throws Exception {
        return new SqlSessionTemplate(sqlSessionFactory);
    }


}
