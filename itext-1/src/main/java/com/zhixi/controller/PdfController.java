package com.zhixi.controller;

import com.zhixi.service.PdfGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

@Controller
@RequestMapping("/pdf")
public class PdfController {

    private PdfGenerationService pdfGenerationService;

    @Autowired
    public void setPdfGenerationService(PdfGenerationService pdfGenerationService) {
        this.pdfGenerationService = pdfGenerationService;
    }


    @GetMapping("/download")
    public void downloadPdf(HttpServletResponse response) throws IOException {
        String fileName = "example.pdf";
        String filePath = "results/chapter01/" + fileName;

        // 1、生成pdf
        pdfGenerationService.generatePdf(filePath);
        // 2、发送响应（下载pdf）
        sendPdfResponse(response, fileName, filePath);
    }

    private void sendPdfResponse(HttpServletResponse response, String fileName, String filePath) throws IOException {
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
}
