package com.qbk.lifecycleBean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.stereotype.Component;

/**
 * Created by 13624 on 2018/8/2.
 * bean销毁
 */
@Component
public class MyDisposableBean implements DisposableBean {

    @Override
    public void destroy() throws Exception {
        System.out.println("spring(bean)释放完毕");
    }
}
