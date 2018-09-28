package com.qbk.poi;

import org.apache.commons.io.IOUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.ImageUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @Author: quboka
 * @Date: 2018/9/28 15:14
 * @Description:
 */
public class WorkbookTest {

    /*
    POIFS (较差混淆技术实现文件系统) : 此组件是所有其他POI元件的基本因素。它被用来明确地读取不同的文件。
    HSSF (可怕的电子表格格式) : 它被用来读取和写入MS-Excel文件的xls格式。
    XSSF (XML格式) : 它是用于MS-Excel中XLSX文件格式。
    HPSF (可怕的属性设置格式) : 它用来提取MS-Office文件属性设置。
    HWPF (可怕的字处理器格式) : 它是用来读取和写入MS-Word的文档扩展名的文件。
    XWPF (XML字处理器格式) : 它是用来读取和写入MS-Word的docx扩展名的文件。
    HSLF (可怕的幻灯片版式格式) : 它是用于读取，创建和编辑PowerPoint演示文稿。
    HDGF (可怕的图表格式) : 它包含类和方法为MS-Visio的二进制文件。
    HPBF (可怕的出版商格式) : 它被用来读取和写入MS-Publisher文件。
     */
    /*
        常用组件：
        Workbook :excel的文档对象
        Sheet:excel的表单
        Row :excel的行
        Cell :excel的格子单元
        Font : excel字体
        DataFormat:日期格式
        Header:sheet头
        Footer:sheet尾（只有打印的时候才能看到效果）
        样式：
        CellStyle :cell样式
        辅助操作包括：
        DateUtil:日期
        PrintSetup;打印
        ErrorConstants :错误信息表
     */

    public static void main(String[] args) throws IOException, InvalidFormatException {
        String path = "C:\\Users\\13624\\Desktop\\远程申办业务.xls";
        //从给定文件创建适当的HSSFWorkbook / XSSFWorkbook，该文件必须存在且可读。
        Workbook workbook = WorkbookFactory.create(new File(path));
        // 获取给定索引处的Sheet对象。
        Sheet sheet = workbook.getSheetAt(0);
        // 返回基于0的逻辑行（非物理行）。
        Row row = sheet.getRow(0);
        // 获取表示给定列（逻辑单元）的单元格。
        Cell cell = row.getCell(0);
        //获取单元格的值作为字符串
        String stringCellValue = cell.getStringCellValue();
        //第一页第一行第一格内容
        System.out.println(stringCellValue);
        //恢复细胞的风格。
         CellStyle cellStyle = cell.getCellStyle();
         //获取格式字符串
         String dataFormatString = cellStyle.getDataFormatString();
        System.out.println(dataFormatString);
        //  返回此单元格的列索引
        int columnIndex = cell.getColumnIndex();
        System.out.println(columnIndex);

        //图片的字节
        byte[] bytes = IOUtils.toByteArray(new FileInputStream(new File("C:\\Users\\13624\\Desktop\\sssjpg.jpg")));
        //将图片添加到工作簿
        int pictureIdx = workbook.addPicture(bytes, Workbook.PICTURE_TYPE_JPEG);
        CreationHelper helper = workbook.getCreationHelper();
        //客户端锚点附加到Excel工作表
        ClientAnchor anchor = helper.createClientAnchor();
        // 图片插入坐标
        anchor.setCol1(6); //列
        anchor.setRow1(0); //行
        //创建界面绘图
        Drawing drawing = sheet.createDrawingPatriarch();
        // 创建一张图片
        Picture pict = drawing.createPicture(anchor, pictureIdx);
        //以像素为单位获取宽度  列坐标
        float columnWidth = sheet.getColumnWidthInPixels(6);
        //行高
        float height = sheet.getRow(0).getHeightInPoints();
        System.out.println("width:{"+columnWidth+"}height:{"+height+"}");
        /*调整图像大小。
        参数：
            scaleX - 图像宽度相对于原始宽度相乘的量。
            scaleY - 图像高度相对于原始高度相乘的量。
         */
        pict.resize(115/columnWidth,50/height);
        //将此工作簿写到Outputstream中。
        workbook.write(new FileOutputStream(new File("C:\\Users\\13624\\Desktop\\sssjpg.xls")));

        //关闭
        workbook.close();

    }


}
