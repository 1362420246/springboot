package com.qbk.lifecycleBean;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * Created by 13624 on 2018/8/2.
 * bean初始化
 */
@Component
public class MyInitializingBean implements InitializingBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("spring(bean)加载完毕");
    }
}
