package com.qbk.utils;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;
import com.qbk.poi.print.Printer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @Author: quboka
 * @Date: 2018/9/29 14:42
 * @Description: 打印 word、excel
 */
public class YPrintUtils {
    private static Logger logger = LoggerFactory.getLogger(YPrintUtils.class);

    static {
        //设置dll路径 否则会默认读取系统路径
        System.setProperty("jacob.dll.path", YPrintUtils.class.getResource("/").getPath() + "jacob-1.14.3-x64.dll");
        //初始化sta接口
        ComThread.InitSTA();
    }
    public static void main(String[] args) {
        String path = Thread.currentThread().getClass().getResource("/").getPath().substring(1);
        String filePath = path + "001.docx";
        wordPrint(filePath, Printer.getDefaultPrinter());
        excelPrint("C:\\Users\\13624\\Desktop\\远程申办业务.xls", Printer.getDefaultPrinter());
    }
    public static Boolean wordPrint(String outputUrl, String deviceName) {
        Dispatch doc = null;
        ActiveXComponent word = null;
        try {
            //设置打印文件为word
            word = new ActiveXComponent("Word.Application");
            //设置默认打印机
            word.setProperty("ActivePrinter", new Variant(deviceName));
            // 设置以不可见的形式打开word
            Dispatch.put(word, "Visible", new Variant(false));
            Dispatch docs = word.getProperty("Documents").toDispatch();
            //打开word文档
            doc = Dispatch.call(docs, "Open", outputUrl).toDispatch();
            //打印
            Dispatch.call(doc, "PrintOut");
        } catch (Exception e) {
            logger.error("打印失败", e);
            return false;
        } finally {
            try {
                if (doc != null) {
                    //关闭打开word
                    Dispatch.call(doc, "Close", new Variant(0));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            word.invoke("Quit", new Variant[0]);
            //释放资源
            ComThread.Release();
        }
        return true;
    }

    public static Boolean excelPrint(String outputUrl, String deviceName) {
        //新建Excel对象
        ActiveXComponent xl = null;
        Dispatch excel = null;
        try {
            ActiveXComponent word = new ActiveXComponent("Word.Application");
            word.setProperty("ActivePrinter", new Variant(deviceName));
            word.invoke("Quit", new Variant[0]);
            xl = new ActiveXComponent("Excel.Application");
//            xl.setProperty("ActivePrinter", new Variant(deviceName));
//            xl.invoke("Quit", new Variant[0]);
            //设置默认打印机
//            Variant activePrinter = xl.getProperty("ActivePrinter");
            //设置是否显示打开Excel
            Dispatch.put(xl, "Visible", new Variant(false));
            //打开具体的工作簿
            Dispatch workbooks = xl.getProperty("Workbooks").toDispatch();
            excel = Dispatch.call(workbooks, "Open", outputUrl).toDispatch();

            //设置打印属性并打印
            Dispatch.callN(excel, "PrintOut", new Object[]{Variant.VT_MISSING, Variant.VT_MISSING, new Integer(1),
                    new Boolean(false), Variant.VT_MISSING, new Boolean(true), Variant.VT_MISSING, ""});

        } catch (Exception e) {
            logger.error("打印失败", e);
            return false;
        } finally {
            try {
                if (excel != null) {
                    //关闭打开word
                    Dispatch.call(excel, "Close", new Variant(0));
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
            xl.invoke("Quit", new Variant[0]);
            //始终释放资源
            ComThread.Release();
        }
        return true;
    }
}
