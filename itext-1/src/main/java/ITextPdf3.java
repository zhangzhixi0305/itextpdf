
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * 简单的图像示例。
 */
public class ITextPdf3 {

    private static String RESOURCE_PATH;

    static {
        RESOURCE_PATH = Objects.requireNonNull(ITextPdf3.class.getClassLoader().getResource("")).getPath();
        // 去除路径中的前缀"/"，以适应Windows系统
        if (RESOURCE_PATH.startsWith("/")) {
            RESOURCE_PATH = RESOURCE_PATH.substring(1);
        }
    }

    public static final String DEST = "results/chapter01/quick_brown_fox.pdf";
    private static final String FOX = RESOURCE_PATH + "img/fox.jpg";
    private static final String DOG = RESOURCE_PATH + "img/dog.jpg";

    public static void main(String[] args) throws IOException {
        new ITextPdf3().createPdf(DEST);
    }

    public void createPdf(String dest) throws IOException {
        try (// 1、创建一个PdfWriter对象
             PdfWriter writer = new PdfWriter(dest);
             // 2、初始化一个PdfDocument对象
             PdfDocument pdf = new PdfDocument(writer);
             // 3、初始化一个Document对象
             Document document = new Document(pdf)) {
            byte[] foxBytes = Files.readAllBytes(Paths.get(FOX));
            byte[] dogBytes = Files.readAllBytes(Paths.get(DOG));
            Image fox = new Image(ImageDataFactory.create(foxBytes));
            Image dog = new Image(ImageDataFactory.create(dogBytes));
            // 新建一个段落
            Paragraph p = new Paragraph("The quick brown ")
                    .add(fox)
                    .add(" jumps over the lazy ")
                    .add(dog);
            // 4、向文档中添加段落
            document.add(p);
        }
    }
}