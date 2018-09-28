package com.qbk;

import com.qbk.poi.XWPRUNTest;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;

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

    /**
     * 流操作
     */
    @Test
    public void readTest(){
        try{
            byte[] bytes = new byte[4];
            InputStream is = IOUtils.toInputStream("hello world", Charset.defaultCharset());
            IOUtils.read(is, bytes);
            System.out.println(new String(bytes));

            bytes = new byte[10];
            is = IOUtils.toInputStream("hello world");
            IOUtils.read(is, bytes, 2, 4);
            System.out.println(new String(bytes));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 文件操作
     */
    @Test
    public void fileTest(){
        ArrayList<String> list = new ArrayList<>() ;
        String mes = "哈哈，下班了" ;
        String mes2 = "回家，回家" ;
        list.add( mes ) ;  //第一行数据
        list.add( mes2 ) ; //第二行数据
        //两个文件都不存在
        String filePath = "C:\\Users\\13624\\Desktop\\001.txt" ;
        String filePath2 = "C:\\Users\\13624\\Desktop\\002.txt" ;
        File file = new File( filePath ) ;
        File file2 = new File( filePath2 ) ;
        //写入内容
        try {
            FileUtils.writeLines( file , list );
        }catch (IOException e) {
            e.printStackTrace();
        }
        //复制文件
        try {
            FileUtils.copyFile(file,file2);
        } catch (IOException e) {
            e.printStackTrace();
        }
        //比较两个文件的内容
        try {
            final boolean b = FileUtils.contentEquals(file, file2);
            System.out.println("文件是否相同："+b);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }










}
