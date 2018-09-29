package com.qbk.poi.print;

import com.jacob.activeX.ActiveXComponent;
import com.jacob.com.ComThread;
import com.jacob.com.Dispatch;
import com.jacob.com.Variant;

/**
 * @Author: quboka
 * @Date: 2018/9/29 10:44
 * @Description:
 */
public class JacobTest {

    /*
    JACOB是一个 JAVA到微软的COM接口的桥梁。使用JACOB允许任何JVM访问COM对象，从而使JAVA应用程序能够调用COM对象。如果你要对 MS Word、Excel 进行处理，JACOB 是一个好的选择。

    而关于打印的话，总结起来有这么几个步骤。
    1、使用Jacob创建 ActiveX部件对象：
    2、打开Word文档
    3、设置打印机
    4、设置打印参数并打印
    5、最后关闭Word文档
    到这里，利用jacob打印就实现了，并且只要将第一步中创建的ActiveX部件对象改成Excel.Application就可以实现Excel的打印了

    这里在说点题外话，之前说了jacob还可以操作word和excel，这里简单提一下
    可以看到在上面的第2步中，我们使用“open”打开了一个现有的word文档，如果改用“add”，那么我们就可以新建一个文档了
    Dispatch document = Dispatch.call(documents, "Add").toDispatch(); // 使用Add命令创建一个新文档
    Dispatch wordContent = Dispatch.get(document, "Content").toDispatch(); // 取得word文件的内容
    Dispatch.call(wordContent, "InsertAfter", "这里是一个段落的内容");//插入一个段

    另外需要注意的是，在使用jacob时，不仅需要添加jacob的jar包，在java安装目录下的bin目录中也要复制jacob的dll文件，否则会报错：
    Exception in thread "main" java.lang.UnsatisfiedLinkError: no jacob-1.14.3-x86 in java.library.path
     */
    static {
        //设置dll路径 否则会默认读取系统路径
        System.setProperty("jacob.dll.path", Thread.currentThread().getClass().getResource("/").getPath() + "jacob-1.14.3-x64.dll");
        //初始化sta接口
        ComThread.InitSTA();
    }
    public static void main(String[] args){
        String path = Thread.currentThread().getClass().getResource("/").getPath().substring(1);
        String filePath = path + "001.docx";
        System.out.println(filePath);

        // 1、使用Jacob创建 ActiveX部件对象：
        ActiveXComponent wd = new ActiveXComponent("Word.Application");

        //2、打开Word文档  这里Visible是控制文档打开后是可见还是不可见，若是静默打印，那么第三个参数就设为false就好了
        Dispatch.put(wd, "Visible", new Variant(false));
        Dispatch document = wd.getProperty("Documents").toDispatch();
        Dispatch doc = Dispatch.call(document, "Open", filePath).toDispatch();

        /*
        //这里在说点题外话，之前说了jacob还可以操作word和excel，这里简单提一下
        //可以看到在上面的第2步中，我们使用“open”打开了一个现有的word文档，如果改用“add”，那么我们就可以新建一个文档了
        Dispatch document = Dispatch.call(documents, "Add").toDispatch(); // 使用Add命令创建一个新文档
        Dispatch wordContent = Dispatch.get(document, "Content").toDispatch(); // 取得word文件的内容
        Dispatch.call(wordContent, "InsertAfter", "这里是一个段落的内容");//插入一个段
        */

        //3、设置打印机   第二个参数即为打印机的name
        wd.setProperty("ActivePrinter", new Variant("Brother MFC-9140CDN Printer"));

       // 4、设置打印参数并打印
        Dispatch.callN(doc, "PrintOut", new Object[]{});

        //5、最后关闭Word文档
        wd.invoke("Quit", new Variant[] {});
        //到这里，利用jacob打印就实现了，并且只要将第一步中创建的ActiveX部件对象改成Excel.Application就可以实现Excel的打印了

    }

}
