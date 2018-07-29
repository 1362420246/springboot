package com.qbk.dao.two;

import com.qbk.bean.Test;
import org.springframework.stereotype.Repository;

/**
 * Created by 13624 on 2018/6/20.
 */
@Repository
public interface TestDao {

    public Test getTest()throws  Exception;

    void add(Test t);
}
