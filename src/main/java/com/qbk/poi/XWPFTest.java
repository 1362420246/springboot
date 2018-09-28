package com.qbk.poi;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.xwpf.usermodel.*;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * @Author: quboka
 * @Date: 2018/9/27 10:08
 * @Description: XWPFDocument对象操控word 测试
 */
@Component("xWPRUNTest")
public class XWPFTest {

    /*
    https://blog.csdn.net/harry_zh_wang/article/details/61938911

    XWPFDocument对象可以解析docx文件，在XWPFDocument对象通过输入流解析docx的时候，会获取到docx文档中的各种对象，例如表格，段落，图片等，通过操作XWPFDocument对象就可以修改模板内容

           //解析docx模板并获取document对象
             XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取整个文本对象
            List<XWPFParagraph> allParagraph = document.getParagraphs();
            //获取整个表格对象
            List<XWPFTable> allTable = document.getTables();
            //获取图片对象
            XWPFPictureData pic = document.getPictureDataByID("PICId");
     */

    /**
     * 模板文件地址
     */
    private static String inputUrl = "C:\\Users\\13624\\Desktop\\001.docx";

    /**
     * 新生产的模板文件
     */
    private static String outputUrl = "C:\\Users\\13624\\Desktop\\002.docx";

    /**
     *  文本打印测试
     */
    public void runTest(){
        try {
            //解析docx模板并获取document对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取整个文本对象
            List<XWPFParagraph> allParagraph = document.getParagraphs();
            //获取XWPFRun对象输出整个文本内容
            StringBuffer tempText = new StringBuffer();
            for (XWPFParagraph xwpfParagraph : allParagraph) {
                List<XWPFRun> runList = xwpfParagraph.getRuns();
                for (XWPFRun xwpfRun : runList) {
                    tempText.append(xwpfRun.toString());
                }
            }
            System.out.println(tempText.toString());
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     *  表格打印测试
     */
    public void tableTest(){
        try {
            StringBuffer tableText = new StringBuffer();
            //解析docx模板并获取document对象
            XWPFDocument document = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取全部表格对象
            List<XWPFTable> allTable = document.getTables();
            for (XWPFTable xwpfTable : allTable) {
                //获取表格行数据
                List<XWPFTableRow> rows = xwpfTable.getRows();
                for (XWPFTableRow xwpfTableRow : rows) {
                    //获取表格单元格数据
                    List<XWPFTableCell> cells = xwpfTableRow.getTableCells();
                    for (XWPFTableCell xwpfTableCell : cells) {
                        //获取文本对象
                        List<XWPFParagraph> paragraphs = xwpfTableCell.getParagraphs();
                        for (XWPFParagraph xwpfParagraph : paragraphs) {
                            List<XWPFRun> runs = xwpfParagraph.getRuns();
                            for(int i = 0; i < runs.size();i++){
                                XWPFRun run = runs.get(i);
                                tableText.append(run.toString());
                            }
                        }
                    }
                }
            }

            System.out.println(tableText.toString());
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    /**
     * 修改word并生成新word
     * @param inputUrl 模板路径
     * @param outputUrl 模板保存路径
     */
    public static void changeWord(String inputUrl, String outputUrl ){
        try {
            //获取word文档解析对象
            XWPFDocument doucument = new XWPFDocument(POIXMLDocument.openPackage(inputUrl));
            //获取段落文本对象
            List<XWPFParagraph> paragraph = doucument.getParagraphs();
            //获取首行run对象
            XWPFRun run = paragraph.get(0).getRuns().get(0);
            //设置文本内容
            run.setText("修改了的word");
            //生成新的word
            File file = new File(outputUrl);
            FileOutputStream stream = new FileOutputStream(file);
            doucument.write(stream);
            stream.close();
            doucument.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }





}
