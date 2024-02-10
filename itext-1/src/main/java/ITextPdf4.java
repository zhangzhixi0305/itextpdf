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
import com.itextpdf.layout.property.TextAlignment;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * 简单的表格示例。
 */
public class ITextPdf4 {

    public static final String DATA = "itext-1/src/main/resources/data/united_states.csv";
    public static final String DEST = "results/chapter01/united_states.pdf";

    public static void main(String[] args) throws IOException {
        //new ITextPdf4().createPdfToCsv(DEST);
        new ITextPdf4().createPdfToDb(DEST);
    }

    /**
     * 从数据库创建PDF
     *
     * @param dest 输出文件路径
     * @throws IOException IO异常
     */
    public void createPdfToDb(String dest) throws IOException {
        try (PdfWriter writer = new PdfWriter(dest);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf);
        ) {
            // 设置文档的页边距
            document.setMargins(20, 20, 20, 20);

            // 创建只有两列的表格，各占50%宽度
            Table table = new Table(new float[]{50f, 50f});
            // 设置表格宽度为页面宽度的100%
            table.setWidthPercent(100);
            // 设置表格文字居中
            table.setTextAlignment(TextAlignment.CENTER);

            // DB
            List<Student> students = new ArrayList<>();
            Student student = new Student();
            student.setName("zhangsan");
            student.setAge(18);
            Student student2 = new Student();
            student2.setName("李四");
            student2.setAge(20);
            students.add(student);
            students.add(student2);

            // 创建字体（中文）
            PdfFont fontGarbled = PdfFontFactory.createFont("STSongStd-Light", "UniGB-UCS2-H", false);

            // 添加表头
            table.addHeaderCell(new Cell().add(new Paragraph("姓名").setFont(fontGarbled)).setWidth(50f));
            table.addHeaderCell(new Cell().add(new Paragraph("年龄").setFont(fontGarbled)).setWidth(50f));

            // 添加学生信息
            for (Student s : students) {
                table.addCell(new Cell().add(new Paragraph(s.getName()).setFont(fontGarbled)).setWidth(50f));
                table.addCell(new Cell().add(new Paragraph(String.valueOf(s.getAge())).setFont(fontGarbled)).setWidth(50f));
            }
            document.add(table);
        }
    }

    /**
     * 从CSV文件创建PDF
     *
     * @param dest 输出文件路径
     * @throws IOException IO异常
     */
    public void createPdfToCsv(String dest) throws IOException {
        try (PdfWriter writer = new PdfWriter(dest);
             PdfDocument pdf = new PdfDocument(writer);
             Document document = new Document(pdf, PageSize.A4.rotate());
             BufferedReader br = new BufferedReader(new FileReader(DATA));
        ) {
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

    class Student {
        private String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }

        private int age;
    }
}
