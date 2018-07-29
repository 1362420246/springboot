package com.qbk.service.impl;

import com.qbk.bean.Test;
import com.qbk.dao.two.TestDao;
import com.qbk.service.TestService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

/**
 * 使用事务管理器2，走数据源2
 */
@Service
//@Transactional( value = "txManager2",rollbackFor=Exception.class)
public class TestServiceImpl implements TestService {

    @Resource
    private TestDao testDao ;

    @Override
    public Test getTest()throws  Exception {
        Test t =new Test();
        t.setName("kaka");
        testDao.add(t);
        //测试事务
       // int i=10/0;
        return testDao.getTest() ;
    }
}
