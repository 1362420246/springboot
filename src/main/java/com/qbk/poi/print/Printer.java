package com.qbk.poi.print;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.PrinterName;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author: quboka
 * @Date: 2018/9/29 14:42
 * @Description: 查找打印机
 * //http://www.cnblogs.com/gaopeng527/p/4357591.html
 */
public class Printer {

    /**
     *  根据名称查找打印机
     * @param name 打印机名称
     * @return
     */
    public static String getPrinter(String name){
        // 打印机 查找时约束 的属性集合
        AttributeSet hs = new HashAttributeSet();
        // 打印机名称
        // String printerName = "Brother MFC-7880DN Printer";
        // String printerName = "FX DocuPrint M268 dw";
        String printerName = name;

        // 添加打印机 查找时约束 属性
        //PrinterName是打印属性类，它是一个文本属性，用于指定打印机的名称
        //使用给定的 名称 和区域 设置构造一个新的打印机名称属性。
        hs.add(new PrinterName(printerName, null));

        /**
         * PrintServiceLookup  打印机查找服务
         * lookupPrintServices(DocFlavor flavor, AttributeSet attributes)找到能够打印指定的打印服务
         * @params attributes 打印服务必须支持的属性。如果不使用null这个约束。
         * @params flavor 打印风格（MIME类型）
         */
        //PrintService是DocPrintJob的工厂。PrintService 打印机服务
        PrintService[] pss = PrintServiceLookup.lookupPrintServices(null, hs);
        if (pss.length == 0) {
            System.out.println("无法找到打印机:" + printerName);
            return null;
        }
        return pss[0].getName();
    }

    /**
     * 查找默认打印机
     * @return
     */
    public static String getDefaultPrinter(){
        // 定位默认的打印服务(打印机)
        PrintService defaultService = PrintServiceLookup.lookupDefaultPrintService();
        return defaultService.getName();
    }

    /**
     * 查找所有打印机
     * @return
     */
    public static List<String > getAllPrinter(){
        List<String > list = new ArrayList<>();
        // 构建打印请求属性集
        HashPrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
        // 设置打印格式，因为未确定类型，所以选择autosense
        DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
        // 查找所有的可用的打印服务
        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(flavor, pras);
        for (int i = 0; i < printServices.length; i++) {
            list.add(printServices[i].getName());
        }
        return list ;
    }

    public static void main(String[] args ){
        System.out.println(getAllPrinter());
        System.out.println(getDefaultPrinter());
        System.out.println(getPrinter("Brother MFC-9140CDN "));
    }

}
