package com.qbk;

import com.qbk.bean.UserBean;

import com.qbk.poi.XWPFTest;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.Charset;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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
    private XWPFTest xWPRUNTest;

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

    /**
     * 对象操作
     * import org.apache.commons.beanutils.BeanUtils;
     */
    @Test
    public void beanUtils(){
        Map<String,Object> map = new HashMap<String, Object>();
        map.put("userId", 233);
        map.put("loginName", "kaka");
        map.put("password", "123456");
        //把map中的数据封装到对象上。
        UserBean user = new UserBean();
        try {
            BeanUtils.populate(user, map);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user);
        //copy对象
        UserBean user2 = new UserBean();
        try {
            BeanUtils.copyProperties(user2,user);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        }
        System.out.println(user2);
        //把对象中的数据封装到map上。
        Map<String,String> map2 = new HashMap<String, String>();
        try {
            map2 = BeanUtils.describe(user2);
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        System.out.println(map2);
    }

    /**
     * 时间转换
     * import org.apache.commons.lang3.time.DateFormatUtils;
     * import org.apache.commons.lang3.time.DateUtils;
     */
    @Test
    public void dateTest() throws ParseException {
        //1.将String日期转化成Date：
         Date date = DateUtils.parseDate("2018-07-01", "yyyy-MM-dd");
         System.out.println(date);
         //2.将日期转换成各种format样式类型
         String format = DateFormatUtils.format(date, "MM-dd");
         System.out.println(format);
        //3.月份减1
        Date date1 = DateUtils.addMonths(date, -1);
        System.out.println(date1);
        //4.天数加1
        Date date2 = DateUtils.addDays(date, 1);
        System.out.println(date2);
        //5. 判断是否是同一天，DateUtils的isSameDay()方法
        boolean sameDay = DateUtils.isSameDay(date1, date2);
        System.out.println(sameDay);
    }





}
