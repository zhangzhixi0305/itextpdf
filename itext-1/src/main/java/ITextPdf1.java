/*
 * This example is part of the iText 7 tutorial.
 */

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * 简单的Hello World示例
 *
 * @author zhixi
 */
@Slf4j
public class ITextPdf1 {

    /**
     * 输出文件路径
     */
    public static final String DEST = "results/chapter01/hello_world.pdf";

    public static void main(String[] args) {
        //生成文件
        try {
            new ITextPdf1().createPdf(DEST);
        } catch (IOException e) {
            log.error("生成文件失败", e);
        }
    }

    public void createPdf(String dest) throws IOException {
        // 1、创建一个PdfWriter对象，将文档写入到文件中
        PdfWriter writer = new PdfWriter(dest);
        // 2、初始化一个PdfDocument对象
        PdfDocument pdf = new PdfDocument(writer);
        // 3、初始化一个Document对象
        Document document = new Document(pdf);
        // 4、向文档中添加内容
        document.add(new Paragraph("Hello World!"));
        // 5、关闭文档
        document.close();
    }
}