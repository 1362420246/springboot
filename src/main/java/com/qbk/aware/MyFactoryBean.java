package com.qbk.aware;

import com.qbk.bean.Test;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * Created by 13624 on 2018/8/3.
 * FactoryBean 工厂bean
 */
@Component
public class MyFactoryBean implements FactoryBean<Test> {

    //这里可以进行负责的对象创建逻辑
    @Override
    public Test getObject() throws Exception {
        Test test = new Test();
        return test;
    }

    @Override
    public Class<?> getObjectType() {
        return Test.class;
    }

    //是否是单列
    @Override
    public boolean isSingleton() {
        return true;
    }
}
