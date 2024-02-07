/**
 * Created by CuteKe on 2017/7/10.
 */

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.List;
import com.itextpdf.layout.element.ListItem;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.property.ListNumberingType;

import java.io.IOException;

/**
 * 简单列表示例。
 *
 * @author zhixi
 */
public class ITextPdf2 {
    public static final String DEST = "results/chapter01/rick_astley.pdf";

    public static void main(String[] args) throws IOException {
        //生成文件
        new ITextPdf2().createPdf(DEST);
    }

    /**
     * 创建一个简单的列表
     *
     * @param dest 输出文件路径
     * @throws IOException IO异常
     */
    public void createPdf(String dest) throws IOException {
        PdfWriter writer = new PdfWriter(dest);
        PdfDocument pdf = new PdfDocument(writer);
        try (Document document = new Document(pdf);) {
            // 创建字体
            PdfFont font = PdfFontFactory.createFont(FontConstants.TIMES_ROMAN);
            // 添加一个段落
            document.add(new Paragraph("iText is:").setFont(font));
            // 创建一个列表
            List list = new List()
                    .setSymbolIndent(4) // 设置列表符号缩进
                    .setListSymbol(ListNumberingType.DECIMAL) // 设置列表符号
                    //.setListSymbol("•")
                    .setFont(font);// 设置字体
            list.add(new ListItem("Never gonna give you up"))
                    .add(new ListItem("Never gonna let you down"))
                    .add(new ListItem("Never gonna run around and desert you"))
                    .add(new ListItem("Never gonna make you cry"))
                    .add(new ListItem("Never gonna say goodbye"))
                    .add(new ListItem("Never gonna tell a lie and hurt you"));
            document.add(list);
        }
    }
}