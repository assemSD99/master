package com.saadaoui.master.controller;

import com.google.zxing.WriterException;
import com.itextpdf.text.DocumentException;
import com.saadaoui.master.service.PDFGeneratorService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@RestController
@RequestMapping("/api/pdf")
public class PDFController {

    private final PDFGeneratorService pdfGeneratorService;

    public PDFController(PDFGeneratorService pdfGeneratorService) {
        this.pdfGeneratorService = pdfGeneratorService;
    }

    @GetMapping("/generate/{userId}")
    public ResponseEntity<byte[]> generatePDF(@PathVariable Long userId) {
        String outputPath = "C:\\Users\\assem\\Desktop\\master\\master-backend\\pdfs\\user_" + userId + ".pdf";
        try {
            pdfGeneratorService.generatePDF(userId, outputPath);

            File pdfFile = new File(outputPath);
            byte[] contents = Files.readAllBytes(pdfFile.toPath());

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_PDF);
            headers.setContentDispositionFormData("attachment", "Candidat_" + userId + ".pdf");

            return ResponseEntity.ok().headers(headers).body(contents);

        } catch (DocumentException | IOException | WriterException e) {
            return ResponseEntity.status(500).body(null);
        }
    }
}
