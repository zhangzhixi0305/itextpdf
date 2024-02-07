/**
 * Created by CuteKe on 2017/7/10.
 */

import com.itextpdf.io.font.FontConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.StringTokenizer;

/**
 * 简单的表格示例。
 */
public class ITextPdf4 {

    public static final String DATA = "itext-1/src/main/resources/data/united_states.csv";
    public static final String DEST = "results/chapter01/united_states.pdf";

    public static void main(String[] args) throws IOException {
        new ITextPdf4().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        try (PdfWriter writer = new PdfWriter(dest);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4.rotate());
             BufferedReader br = new BufferedReader(new FileReader(DATA));) {

            // 设置文档的页边距
            document.setMargins(20, 20, 20, 20);
            // 创建字体：普通字体和加粗字体
            PdfFont font = PdfFontFactory.createFont(FontConstants.HELVETICA);
            PdfFont bold = PdfFontFactory.createFont(FontConstants.HELVETICA_BOLD);
            // 创建表格：设置列宽（百分比）
            Table table = new Table(new float[]{4, 1, 3, 4, 3, 3, 3, 3, 1});
            // 设置表格宽度
            table.setWidthPercent(100);

            // 读取数据并添加到表格中
            String line = br.readLine();
            process(table, line, bold, true);
            while ((line = br.readLine()) != null) {
                process(table, line, font, false);
            }
            document.add(table);
        }

    }

    /**
     * 处理表格数据
     *
     * @param table    表格
     * @param line     行数据
     * @param font     字体
     * @param isHeader 是否是表头
     */
    public void process(Table table, String line, PdfFont font, boolean isHeader) {
        StringTokenizer tokenizer = new StringTokenizer(line, ";");
        while (tokenizer.hasMoreTokens()) {
            if (isHeader) {
                table.addHeaderCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            } else {
                table.addCell(new Cell().add(new Paragraph(tokenizer.nextToken()).setFont(font)));
            }
        }
    }
}