package com.qbk;

import com.qbk.poi.XWPRUNTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

/**
 * @Author: quboka
 * @Date: 2018/9/27 10:25
 * @Description: poi测试类
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PoiTest
{

    /**
     * 模板文件地址
     */
    private static String inputUrl = "C:\\Users\\13624\\Desktop\\001.docx";

    /**
     * 新生产的模板文件
     */
    private static String outputUrl = "C:\\Users\\13624\\Desktop\\002.docx";

    @Resource
    private XWPRUNTest xWPRUNTest;

    @Test
    public void test(){

        xWPRUNTest.runTest();
        xWPRUNTest.tableTest();
        xWPRUNTest.changeWord(inputUrl,outputUrl);

    }
}
