package com.zhixi.controller;

import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    @GetMapping("/download")
    public void downloadPdfDownload(HttpServletResponse response) throws IOException {
        String fileName = "example.pdf";
        String filePath = "results/chapter01/" + fileName;

        // 1、生成pdf
        generatePdf(filePath);
        // 2、发送响应（下载pdf）
        sendPdfResponseDownload(response, fileName, filePath);
        // 3、删除pdf
        Files.delete(Paths.get(filePath));
    }

    @GetMapping("/preview")
    public void downloadPdfPreview(HttpServletResponse response) throws IOException {
        String fileName = "example.pdf";
        String filePath = "results/chapter01/" + fileName;

        // 1、生成pdf
        generatePdf(filePath);
        // 2、发送响应（预览pdf）
        sendPdfResponsePreview(response, fileName, filePath);
        // 3、删除pdf
        Files.delete(Paths.get(filePath));
    }

    private void sendPdfResponseDownload(HttpServletResponse response, String fileName, String filePath) throws IOException {
        // 2.1、设置响应头
        File file = new File(filePath);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=" + fileName);
        response.setContentLength((int) file.length());

        // 2.2、将pdf文件写入响应输出流
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(Paths.get(filePath), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendPdfResponsePreview(HttpServletResponse response, String fileName, String filePath) throws IOException {
        // 2.1、设置响应头
        File file = new File(filePath);
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=" + fileName);
        response.setContentLength((int) file.length());

        // 2.2、将pdf文件写入响应输出流
        try (OutputStream out = response.getOutputStream()) {
            Files.copy(Paths.get(filePath), out);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成pdf（Service层）
     *
     * @param filePath pdf文件路径
     * @throws FileNotFoundException 文件未找到异常
     */
    public void generatePdf(String filePath) throws FileNotFoundException {
        PdfWriter writer = new PdfWriter(filePath);
        PdfDocument pdf = new PdfDocument(writer);
        Document document = new Document(pdf);
        document.add(new Paragraph("Hello, World!"));
        document.close();
    }
}
